package thirdstage.sirius.support.oozie;

import java.util.Set;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OozieDefinitionValidatorTest {


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


}
