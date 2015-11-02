package thirdstage.sirius.support.oozie;

import java.io.File;
import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import thirdstage.sirius.support.xml.CollectiveSaxErrorHandler;
import thirdstage.sirius.support.xml.XmlErrorBundle;

public class XmlValidationTest{


	/**
	 * Shows that the JDK built-in JAXP schema validator can't use the embedded schematron.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testXmlValidationUsingSchematronEmbeddedSchema() throws Exception{

		String schPath = "thirdstage/sirius/support/oozie/schematron-schemas/oozie-workflow-0.2.sch.xsd";
		String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-2.xml";

		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		Schema sch = sf.newSchema(ClassLoader.getSystemResource(schPath));

		Validator validator = sch.newValidator();
		CollectiveSaxErrorHandler errHandler = new CollectiveSaxErrorHandler();
		validator.setErrorHandler(errHandler);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(ClassLoader.getSystemResource(xmlPath).toURI()));

		validator.validate(new DOMSource(doc));

		XmlErrorBundle errors = errHandler.getErrorBundle();
		errors.print(System.err);


	}

}
