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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlParsingTest {

   @Test
   public void testParseNamespaceUsingDom() throws Exception{

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      dbf.setValidating(false);

      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(ClassLoader.getSystemResourceAsStream("thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml"));

      NodeList nodes = doc.getElementsByTagNameNS("*", "shell");
      Assert.assertEquals(2, nodes.getLength());
      for(int i = 0, n = nodes.getLength(); i < n; i++){
         System.out.println("shell xmlns=\"" + nodes.item(i).getNamespaceURI() + "\"");
         Assert.assertEquals("uri:oozie:shell-action:0.1", nodes.item(i).getNamespaceURI());
      }

      nodes = doc.getElementsByTagNameNS("*", "sqoop");
      Assert.assertEquals(3, nodes.getLength());
      for(int i = 0, n = nodes.getLength(); i < n; i++){
         System.out.println("sqoop xmlns=\"" + nodes.item(i).getNamespaceURI() + "\"");
         Assert.assertEquals("uri:oozie:sqoop-action:0.2", nodes.item(i).getNamespaceURI());
      }

      nodes = doc.getElementsByTagNameNS("*", "hive");
      Assert.assertEquals(2, nodes.getLength());
      for(int i = 0, n = nodes.getLength(); i < n; i++){
         System.out.println("hive xmlns=\"" + nodes.item(i).getNamespaceURI() + "\"");
         Assert.assertEquals("uri:oozie:hive-action:0.2", nodes.item(i).getNamespaceURI());
      }
   }

   @Test
   public void testParseNamespaceWithPrefixUsingDom() throws Exception{

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      dbf.setValidating(false);

      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(ClassLoader.getSystemResourceAsStream("thirdstage/sirius/support/oozie/samples/workflow-sample-1-2.xml"));

      NodeList nodes = doc.getElementsByTagNameNS("*", "shell");
      Assert.assertEquals(2, nodes.getLength());
      for(int i = 0, n = nodes.getLength(); i < n; i++){
         System.out.println("shell xmlns=\"" + nodes.item(i).getNamespaceURI() + "\"");
         Assert.assertEquals("uri:oozie:shell-action:0.1", nodes.item(i).getNamespaceURI());
      }

      nodes = doc.getElementsByTagNameNS("*", "sqoop");
      Assert.assertEquals(3, nodes.getLength());
      for(int i = 0, n = nodes.getLength(); i < n; i++){
         System.out.println("sqoop xmlns=\"" + nodes.item(i).getNamespaceURI() + "\"");
         Assert.assertEquals("uri:oozie:sqoop-action:0.2", nodes.item(i).getNamespaceURI());
      }

      nodes = doc.getElementsByTagNameNS("*", "hive");
      Assert.assertEquals(2, nodes.getLength());
      for(int i = 0, n = nodes.getLength(); i < n; i++){
         System.out.println("hive xmlns=\"" + nodes.item(i).getNamespaceURI() + "\"");
         Assert.assertEquals("uri:oozie:hive-action:0.2", nodes.item(i).getNamespaceURI());
      }

   }
}
