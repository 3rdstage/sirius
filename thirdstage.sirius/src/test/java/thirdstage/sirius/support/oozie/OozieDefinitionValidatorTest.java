package thirdstage.sirius.support.oozie;

import java.io.File;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import thirdstage.sirius.support.xml.XmlErrorBundle;

public class OozieDefinitionValidatorTest {

   private OozieDefinitionValidator validator = new OozieDefinitionValidator();

   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   private final static String sampleLocBase ="thirdstage/sirius/support/oozie/samples/";
   private final static String validSample1Loc = sampleLocBase + "workflow-sample-1.xml";
   private final static String illformedSample1Loc = sampleLocBase + "workflow-sample-illformed-1.xml";
   private final static String invalidSample1Loc = sampleLocBase + "workflow-sample-invalid-1.xml";
   private static final String linkBrokenSample1Loc = sampleLocBase + "workflow-sample-linkbroken-1.xml";

   @Test
   public void testStaticMemebers(){
      Set<String> wfNamespaces = OozieDefinitionValidator.WORKFLOW_NAMESPACES;
      Set<String> slaNamespaces = OozieDefinitionValidator.SLA_NAMESPACES;
      Set<String> emailNamespaces = OozieDefinitionValidator.EMAIL_ACTION_NAMESPACES;
      Set<String> distcpNamespaces = OozieDefinitionValidator.DISTCP_ACTION_NAMESPACES;
      Set<String> hiveNamespaces = OozieDefinitionValidator.HIVE_ACTION_NAMESPACES;
      Set<String> shellNamespaces = OozieDefinitionValidator.SHELL_ACTION_NAMESPACES;
      Set<String> sqoopNamespaces = OozieDefinitionValidator.SQOOP_ACTION_NAMESPACES;
      Set<String> sshNamespaces = OozieDefinitionValidator.SSH_ACTION_NAMESPACES;

      Assert.assertEquals(6, wfNamespaces.size());
      Assert.assertEquals(2, slaNamespaces.size());
      Assert.assertEquals(1, emailNamespaces.size());
      Assert.assertEquals(2, distcpNamespaces.size());
      Assert.assertEquals(4, hiveNamespaces.size());
      Assert.assertEquals(3, shellNamespaces.size());
      Assert.assertEquals(3, sqoopNamespaces.size());
      Assert.assertEquals(2, sshNamespaces.size());

      Assert.assertTrue(wfNamespaces.contains("uri:oozie:workflow:0.2.5"));
      Assert.assertFalse(wfNamespaces.contains("uri:oozie:workflow:0.3.5"));
      Assert.assertTrue(slaNamespaces.contains("uri:oozie:sla:0.1"));
      Assert.assertFalse(slaNamespaces.contains("uri:oozie:sla:0.3"));
      Assert.assertTrue(hiveNamespaces.contains("uri:oozie:hive-action:0.5"));
      Assert.assertFalse(hiveNamespaces.contains("uri:oozie:hive-action:0.1"));
      Assert.assertTrue(sqoopNamespaces.contains("uri:oozie:sqoop-action:0.4"));
      Assert.assertFalse(sqoopNamespaces.contains("uri:oozie:sqoop-action:0.1"));

      Assert.assertNotNull(OozieDefinitionValidator.workflowSchema);
   }

   @Test
   public void testValidateWfDefOnClasspathWithValidDef1(){

      XmlErrorBundle errors = validator.validateWorkflowDefinitionOnClasspath(validSample1Loc);
      errors.print(System.out);
      Assert.assertEquals(errors.getItems().size(), 0);
   }


   @Test
   public void testValidateWfDefOnClasspathWithIllformedDef1(){

      XmlErrorBundle errors = validator.validateWorkflowDefinitionOnClasspath(illformedSample1Loc);
      errors.print(System.out);
      Assert.assertTrue(errors.getItems().size() > 0);
   }

   @Test
   public void testValidateWfDefOnClasspathWithInvalidDef1(){

      XmlErrorBundle errors = validator.validateWorkflowDefinitionOnClasspath(invalidSample1Loc);
      errors.print(System.out);
      Assert.assertTrue(errors.getItems().size() > 0);
   }   
   
   @Test
   public void testValidateWfDefOnClasspathWithLinkBrokenDef1(){

      XmlErrorBundle errors = validator.validateWorkflowDefinitionOnClasspath(linkBrokenSample1Loc);

      for(XmlErrorBundle.Item item : errors.getItems()){
         logger.error("L{}C{}, {}, {}", new Object[]{item.getLine(), item.getColumn(), item.getLocationHint(), item.getMessage()});
      }
      Assert.assertTrue(errors.getItems().size() > 0);
   }

   @Test
   public void testValidateWfDefStringWithValidDef1() throws Exception{

      File f = new File(ClassLoader.getSystemResource(validSample1Loc).toURI());
      String str = FileUtils.readFileToString(f, "utf-8");

      XmlErrorBundle errors = validator.validateWorkflowDefinitionString(str);

      for(XmlErrorBundle.Item item : errors.getItems()){
         logger.error("{}, L{}C{}, {}, {}", new Object[]{item.getType().toString(), item.getLine(), item.getColumn(), item.getLocationHint(), item.getMessage()});
      }
      Assert.assertEquals(errors.getItems().size(), 0);
   }

   @Test
   public void testValidateWfDefStringWithIllformedDef1() throws Exception{

      File f = new File(ClassLoader.getSystemResource(illformedSample1Loc).toURI());
      String str = FileUtils.readFileToString(f, "utf-8");

      XmlErrorBundle errors = validator.validateWorkflowDefinitionString(str);

      for(XmlErrorBundle.Item item : errors.getItems()){
         logger.error("{}, L{}C{}, {}, {}", new Object[]{item.getType().toString(), item.getLine(), item.getColumn(), item.getLocationHint(), item.getMessage()});
      }
      Assert.assertTrue(errors.getItems().size() > 0);
   }

   @Test
   public void testValidateWfDefStringWithInvalidDef1() throws Exception{

      File f = new File(ClassLoader.getSystemResource(invalidSample1Loc).toURI());
      String str = FileUtils.readFileToString(f, "utf-8");

      XmlErrorBundle errors = validator.validateWorkflowDefinitionString(str);

      for(XmlErrorBundle.Item item : errors.getItems()){
         logger.error("{}, L{}C{}, {}, {}", new Object[]{item.getType().toString(), item.getLine(), item.getColumn(), item.getLocationHint(), item.getMessage()});
      }
      Assert.assertTrue(errors.getItems().size() > 0);
   }   

   @Test
   public void testValidateWfDefStringWithLinkBrokenDef1() throws Exception{

      File f = new File(ClassLoader.getSystemResource(linkBrokenSample1Loc).toURI());
      String str = FileUtils.readFileToString(f, "utf-8");

      XmlErrorBundle errors = validator.validateWorkflowDefinitionString(str);

      for(XmlErrorBundle.Item item : errors.getItems()){
         logger.error("{}, L{}C{}, {}, {}", new Object[]{item.getType().toString(), item.getLine(), item.getColumn(), item.getLocationHint(), item.getMessage()});
      }

      Assert.assertTrue(errors.getItems().size() > 0);
   }

}
