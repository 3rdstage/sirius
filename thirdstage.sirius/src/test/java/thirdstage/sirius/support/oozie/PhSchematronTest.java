package thirdstage.sirius.support.oozie;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.oclc.purl.dsdl.svrl.FailedAssert;
import org.oclc.purl.dsdl.svrl.SchematronOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import thirdstage.sirius.support.xml.XmlErrorBundle;

import com.helger.commons.io.resource.ClassPathResource;
import com.helger.schematron.ISchematronResource;
import com.helger.schematron.pure.SchematronResourcePure;
import com.helger.schematron.pure.errorhandler.CollectingPSErrorHandler;

public class PhSchematronTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test(expectedExceptions={java.lang.IllegalStateException.class})
	public void testErrorHandler1() throws Exception{

		String schPath = "thirdstage/sirius/support/oozie/schematron/workflow-0.1.sch"; 
		String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
		
		SchematronResourcePure workflowSchematron = SchematronResourcePure.fromClassPath(schPath);
		if(!workflowSchematron.isValidSchematron()){
			RuntimeException ex = new IllegalStateException("Fail to initialize OozieDefinitionValidator class. - Invalid Scehmatron at " + schPath); 
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		
		CollectingPSErrorHandler errHandler = new CollectingPSErrorHandler();
		workflowSchematron.setErrorHandler(errHandler); //will raise exception!!

		Source src = new StreamSource(new File(ClassLoader.getSystemResource(xmlPath).toURI()));
		SchematronOutputType output = null;
		
		
		output = workflowSchematron.applySchematronValidationToSVRL(src);
	
	}
	
	@Test
	public void testErrorHandler2() throws Exception{

		String schPath = "thirdstage/sirius/support/oozie/schematron/workflow-0.1.sch"; 
		String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
		
		
		ClassPathResource schRes = new ClassPathResource(schPath);
		CollectingPSErrorHandler errHandler = new CollectingPSErrorHandler();
		SchematronResourcePure workflowSchematron = new SchematronResourcePure(schRes, null, errHandler);
		if(!workflowSchematron.isValidSchematron()){
			RuntimeException ex = new IllegalStateException("Fail to initialize OozieDefinitionValidator class. - Invalid Scehmatron at " + schPath); 
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		
		Source src = new StreamSource(new File(ClassLoader.getSystemResource(xmlPath).toURI()));
		SchematronOutputType output = null;
		
		output = workflowSchematron.applySchematronValidationToSVRL(src);
	
	}	
	
	@Test
	public void testOutputTypeUsingStreamSource() throws Exception{

		String schPath = "thirdstage/sirius/support/oozie/schematron/workflow-0.1.sch"; 
		String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
		
		SchematronResourcePure workflowSchematron = SchematronResourcePure.fromClassPath(schPath);
		if(!workflowSchematron.isValidSchematron()){
			RuntimeException ex = new IllegalStateException("Fail to initialize OozieDefinitionValidator class. - Invalid Scehmatron at " + schPath); 
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		
		Source src = new StreamSource(new File(ClassLoader.getSystemResource(xmlPath).toURI()));
		SchematronOutputType output = null;
		
		
		output = workflowSchematron.applySchematronValidationToSVRL(src);
		
		Assert.assertTrue(output.getActivePatternAndFiredRuleAndFailedAssert().size() > 0);
	}

	@Test
	public void testOutputTypeUsingDom() throws Exception{

		String schPath = "thirdstage/sirius/support/oozie/schematron/workflow-0.1.sch"; 
		String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
		
		SchematronResourcePure workflowSchematron = SchematronResourcePure.fromClassPath(schPath);
		if(!workflowSchematron.isValidSchematron()){
			RuntimeException ex = new IllegalStateException("Fail to initialize OozieDefinitionValidator class. - Invalid Scehmatron at " + schPath); 
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setValidating(false);

		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(ClassLoader.getSystemResourceAsStream(xmlPath));
		SchematronOutputType output = null;
		
		output = workflowSchematron.applySchematronValidation(doc);
		
		Assert.assertTrue(output.getActivePatternAndFiredRuleAndFailedAssert().size() > 0);
	}	
	
	
	
}
