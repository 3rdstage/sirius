package thirdstage.sirius.support.oozie;

import java.io.File;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.oclc.purl.dsdl.svrl.SchematronOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import com.helger.commons.error.IResourceErrorGroup;
import com.helger.commons.io.IReadableResource;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.schematron.SchematronHelper;
import com.helger.schematron.pure.SchematronResourcePure;
import com.helger.schematron.pure.errorhandler.CollectingPSErrorHandler;
import com.helger.schematron.svrl.SVRLFailedAssert;
import com.helger.schematron.svrl.SVRLUtils;
import com.helger.schematron.xslt.SchematronResourceSCH;
import com.helger.schematron.xslt.SchematronResourceXSLT;

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
	public void testOutputTypeUsingDom1() throws Exception{

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
		Document doc = db.parse(new File(ClassLoader.getSystemResource(xmlPath).toURI()));
		SchematronOutputType output = null;
		
		output = workflowSchematron.applySchematronValidation(doc);
		String path = "D:/home/git/repos/sirius/thirdstage.sirius/target/test-classes/thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
		path = "D:\\home\\git\\repos\\sirius\\thirdstage.sirius\\target\\test-classes\\thirdstage\\sirius\\support\\oozie\\samples\\workflow-sample-1.xml";
		
		IResourceErrorGroup errGroups = SchematronHelper.convertToResourceErrorGroup(output, path);
		
		List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output);
		
		Assert.assertTrue(fails.size() > 0);
	}	
	
   @Test
   public void testOutputTypeUsingDom2() throws Exception{

      String schPath = "thirdstage/sirius/support/oozie/schematron/workflow.sch"; 
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
      Document doc = db.parse(new File(ClassLoader.getSystemResource(xmlPath).toURI()));
      SchematronOutputType output = null;
      
      output = workflowSchematron.applySchematronValidation(doc);
      
      Assert.assertTrue(output.getActivePatternAndFiredRuleAndFailedAssert().size() > 0);
   }  
   	
   @Test
   public void testOutputTypeUsingDom3() throws Exception{

      String schPath = "thirdstage/sirius/support/oozie/schematron/workflow.sch"; 
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
      Document doc = db.parse(new File(ClassLoader.getSystemResource(xmlPath).toURI()));
      Document output = null;
      
      output = workflowSchematron.applySchematronValidation(new DOMSource(doc));
      
      Assert.assertTrue(output.getChildNodes().getLength() > 0);
   }  
   
   
   @Test
   public void testOutputTypeUsingDom4() throws Exception{

      String schPath = "thirdstage/sirius/support/oozie/schematron/workflow.sch"; 
      String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
      
      SchematronResourceSCH workflowSchematron = SchematronResourceSCH.fromClassPath(schPath);
      if(!workflowSchematron.isValidSchematron()){
         RuntimeException ex = new IllegalStateException("Fail to initialize OozieDefinitionValidator class. - Invalid Scehmatron at " + schPath); 
         logger.error(ex.getMessage(), ex);
         throw ex;
      }
      
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      dbf.setValidating(false);

      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(ClassLoader.getSystemResource(xmlPath).toURI()));
      SchematronOutputType output = null;
      
      IReadableResource rr = new ClassPathResource(xmlPath);
      output = workflowSchematron.applySchematronValidationToSVRL(rr);
      String path = "D:/home/git/repos/sirius/thirdstage.sirius/target/test-classes/thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
      path = "D:\\home\\git\\repos\\sirius\\thirdstage.sirius\\target\\test-classes\\thirdstage\\sirius\\support\\oozie\\samples\\workflow-sample-1.xml";
      
      IResourceErrorGroup errGroups = SchematronHelper.convertToResourceErrorGroup(output, path);
      
      List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output);
      
      Assert.assertTrue(fails.size() > 0);      
   
   }  
   
   @Test
   public void testOutputTypeUsingDom5() throws Exception{

      String schPath = "thirdstage/sirius/support/oozie/schematron/workflow.sch"; 
      String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
      
      SchematronResourceXSLT workflowSchematron = SchematronResourceXSLT.fromClassPath(schPath);
      if(!workflowSchematron.isValidSchematron()){
         RuntimeException ex = new IllegalStateException("Fail to initialize OozieDefinitionValidator class. - Invalid Scehmatron at " + schPath); 
         logger.error(ex.getMessage(), ex);
         throw ex;
      }
      
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      dbf.setValidating(false);

      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(ClassLoader.getSystemResource(xmlPath).toURI()));
      SchematronOutputType output = null;
      
      IReadableResource rr = new ClassPathResource(xmlPath);
      output = workflowSchematron.applySchematronValidationToSVRL(rr);
      String path = "D:/home/git/repos/sirius/thirdstage.sirius/target/test-classes/thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
      path = "D:\\home\\git\\repos\\sirius\\thirdstage.sirius\\target\\test-classes\\thirdstage\\sirius\\support\\oozie\\samples\\workflow-sample-1.xml";
      
      IResourceErrorGroup errGroups = SchematronHelper.convertToResourceErrorGroup(output, path);
      
      List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output);
      
      Assert.assertTrue(fails.size() > 0);      
   
   }  
   
}
