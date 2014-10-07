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
import com.helger.commons.xml.serialize.DOMReader;
import com.helger.schematron.SchematronHelper;
import com.helger.schematron.pure.SchematronResourcePure;
import com.helger.schematron.pure.binding.IPSQueryBinding;
import com.helger.schematron.pure.binding.PSQueryBindingRegistry;
import com.helger.schematron.pure.bound.IPSBoundSchema;
import com.helger.schematron.pure.errorhandler.CollectingPSErrorHandler;
import com.helger.schematron.pure.errorhandler.IPSErrorHandler;
import com.helger.schematron.pure.exchange.PSReader;
import com.helger.schematron.pure.model.PSSchema;
import com.helger.schematron.pure.preprocess.PSPreprocessor;
import com.helger.schematron.pure.validation.xpath.PSXPathValidationHandlerSVRL;
import com.helger.schematron.svrl.SVRLFailedAssert;
import com.helger.schematron.svrl.SVRLUtils;
import com.helger.schematron.xslt.SchematronResourceSCH;
import com.helger.schematron.xslt.SchematronResourceXSLT;

public class PhSchematronTest {

   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   
   private static final String schematronLocBase = "thirdstage/sirius/support/oozie/schematrons/";
   private static final String sampleLocBase = "thirdstage/sirius/support/oozie/samples/";
   
   private static final String schematron0Loc = schematronLocBase + "workflow.sch";
   private static final String schematron1Loc = schematronLocBase + "workflow-0.1.sch";
   
   private static final String sample1Loc = sampleLocBase + "workflow-sample-linkbroken-1.xml";

   @Test(expectedExceptions={java.lang.IllegalStateException.class})
   public void testErrorHandler1() throws Exception{

      String schPath = schematron1Loc;
      String xmlPath = sample1Loc;

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
      String schPath = schematron1Loc; 
      String xmlPath = sample1Loc;

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
      String schPath = schematron1Loc; 
      String xmlPath = sample1Loc;

      SchematronResourcePure workflowSchematron = SchematronResourcePure.fromClassPath(schPath);
      if(!workflowSchematron.isValidSchematron()){
         RuntimeException ex = new IllegalStateException("Fail to initialize OozieDefinitionValidator class. - Invalid Scehmatron at " + schPath); 
         logger.error(ex.getMessage(), ex);
         throw ex;
      }

      Source src = new StreamSource(new File(ClassLoader.getSystemResource(xmlPath).toURI()));
      SchematronOutputType output = null;

      output = workflowSchematron.applySchematronValidationToSVRL(src);
      List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output);

      for(SVRLFailedAssert fail : fails){
         logger.info("{}, {}", fail.getLocation(), fail.getText());
      }

      Assert.assertTrue(output.getActivePatternAndFiredRuleAndFailedAssert().size() > 0);
   }

   @Test
   public void testValidationUsingSchematronResourcePureAndDom() throws Exception{
      //using SchematronResourcePure and DOM
      String schPath = schematron1Loc; 
      String xmlPath = sample1Loc;

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
      String path = new File(ClassLoader.getSystemResource(xmlPath).toURI()).getCanonicalPath();
      //path = "D:\\home\\git\\repos\\sirius\\thirdstage.sirius\\target\\test-classes\\thirdstage\\sirius\\support\\oozie\\samples\\workflow-sample-1.xml";

      IResourceErrorGroup errGroups = SchematronHelper.convertToResourceErrorGroup(output, path);

      List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output);
      for(SVRLFailedAssert fail : fails){
         logger.info("{}, {}", fail.getLocation(), fail.getText());
      }

      Assert.assertTrue(fails.size() > 0);
   }	

   @Test
   public void testValidationUsingSchematronResourcePureAndDom2() throws Exception{
      //using SchematronResourcePure and DOM
      //seems to fire single assertion just one time and catch all the failures
      
      String schPath = schematron0Loc; 
      String xmlPath = sample1Loc;

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
      List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output);
      for(SVRLFailedAssert fail : fails){
         logger.info("{}, {}", fail.getLocation(), fail.getText());
      }
      
      Assert.assertTrue(output.getActivePatternAndFiredRuleAndFailedAssert().size() > 0);
   }  

   @Test
   public void testValidationUsingSchematronResourcePureAndDom3() throws Exception{
      String schPath = schematron0Loc; 
      String xmlPath = sample1Loc;

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
      logger.info(output.getChildNodes().toString());

      Assert.assertTrue(output.getChildNodes().getLength() > 0);
   }  


   @Test(expectedExceptions={java.lang.IllegalStateException.class})
   public void testValidationUsingSchematronResourceSchAndReadableResource() throws Exception{
    //using SchematronResourceSCH and IReadableResource
    //seems to fire single assertion multiple times that could be inefficient.
      
      String schPath = schematron0Loc; 
      String xmlPath = sample1Loc;

      SchematronResourceSCH workflowSchematron = SchematronResourceSCH.fromClassPath(schPath);
      if(!workflowSchematron.isValidSchematron()){
         RuntimeException ex = new IllegalStateException("Fail to initialize OozieDefinitionValidator class. - Invalid Scehmatron at " + schPath); 
         logger.error(ex.getMessage(), ex);
         throw ex;
      }

      SchematronOutputType output = null;

      IReadableResource rr = new ClassPathResource(xmlPath);
      output = workflowSchematron.applySchematronValidationToSVRL(rr);
      String path = new File(ClassLoader.getSystemResource(xmlPath).toURI()).getCanonicalPath();

      IResourceErrorGroup errGroups = SchematronHelper.convertToResourceErrorGroup(output, path);

      List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output);

      Assert.assertTrue(fails.size() > 0);      

   }  
   
   @Test(expectedExceptions={java.lang.IllegalStateException.class})
   public void testValidationUsingSchematronResourceSchAndDomSource() throws Exception{

      //using SchematronResourceSCH and DOMSource
      //seems to fire single assertion multiple times that could be inefficient.
      String schPath = schematron0Loc; 
      String xmlPath = sample1Loc;

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

      output = workflowSchematron.applySchematronValidationToSVRL(new DOMSource(doc));
      String path = "D:/home/git/repos/sirius/thirdstage.sirius/target/test-classes/thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";

      IResourceErrorGroup errGroups = SchematronHelper.convertToResourceErrorGroup(output, path);
      List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output);

      for(SVRLFailedAssert fail : fails){
         logger.info("{}, {}", fail.getLocation(), fail.getText());
      }
      
      Assert.assertTrue(output.getActivePatternAndFiredRuleAndFailedAssertCount() > 0);
      Assert.assertTrue(fails.size() > 0);
   }     

   @Test(expectedExceptions={java.lang.IllegalStateException.class})
   public void testValidationUsingSchematronResourceXsltAndReadableResource() throws Exception{
      //using SchematronResourceXSLT and IReadableResource
      //thows exception
      
      String schPath = schematron0Loc; 
      String xmlPath = sample1Loc;

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
 
      IResourceErrorGroup errGroups = SchematronHelper.convertToResourceErrorGroup(output, path);

      List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output);

      Assert.assertTrue(fails.size() > 0);      
   }  
   
   
   @Test
   public void testValidationUsingInternalApi() throws Exception{

      //using SchematronResourceSCH and IReadableResource
      //seems to go on over 1st failure
      
      //IPSErrorHandler
      //XPathVariableResolver
      //XPathFunctionResolver
      //IPSValidationHandler
      
      String schPath = schematron0Loc; 
      String xmlPath = sample1Loc;
      
      final PSSchema sch = new PSReader(new ClassPathResource(schPath)).readSchema();
      if(!sch.isValid()){
         RuntimeException ex = new IllegalStateException("Fail to initialize OozieDefinitionValidator class. - Invalid Scehmatron at " + schPath); 
         logger.error(ex.getMessage(), ex);
         throw ex;
      }
      
      final IPSQueryBinding qryBinding 
         = PSQueryBindingRegistry.getQueryBindingOfNameOrThrow(sch.getQueryBinding());
      final PSPreprocessor preProcessor = new PSPreprocessor (qryBinding);
      preProcessor.setKeepDiagnostics(true).setKeepReports(true).setKeepTitles(true);
      
      final PSSchema processedSch = preProcessor.getAsPreprocessedSchema(sch);
      IPSErrorHandler errHandler = new CollectingPSErrorHandler();
      final IPSBoundSchema boundSch = qryBinding.bind(processedSch, null, errHandler);

      final Document doc = DOMReader.readXMLDOM(new File(ClassLoader.getSystemResource(xmlPath).toURI()));
      PSXPathValidationHandlerSVRL valHandler = new PSXPathValidationHandlerSVRL(errHandler);
      boundSch.validate(doc, valHandler); //validate completely over the errors found.
      SchematronOutputType output = valHandler.getSVRL();
      
      List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output);

      for(SVRLFailedAssert fail : fails){
         logger.info("{}, {}", fail.getLocation(), fail.getText());
      }
      
      Assert.assertTrue(output.getActivePatternAndFiredRuleAndFailedAssertCount() > 0);
      Assert.assertTrue(fails.size() > 0);
   }  

}
