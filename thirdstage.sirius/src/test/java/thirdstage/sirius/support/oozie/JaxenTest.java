package thirdstage.sirius.support.oozie;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;

import org.jaxen.Navigator;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.dom.DOMXPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import thirdstage.sirius.support.xml.UniversalNamespaceContext;
import thirdstage.sirius.support.xml.XPathUtils;

public class JaxenTest {
	
	
	@Test
	public void testXPathEvaluation1() throws Exception{
		
		String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setValidating(false);

		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(ClassLoader.getSystemResourceAsStream(xmlPath)));

		org.jaxen.XPath xpath = new DOMXPath("//action");
		xpath.setNamespaceContext(new SimpleNamespaceContext());
		Navigator nav = xpath.getNavigator();
		List selected = xpath.selectNodes(doc);
		
	}

}
