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
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import thirdstage.sirius.support.xml.UniversalNamespaceContext;
import thirdstage.sirius.support.xml.XPathUtils;

public class JaxenTest {

   private final Logger logger = LoggerFactory.getLogger(this.getClass());


   @Test
   public void testNamespaceResolution1() throws Exception{
      String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(false); //@caution : ignored namespace
      dbf.setValidating(false);

      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new InputSource(ClassLoader.getSystemResourceAsStream(xmlPath)));

      org.jaxen.XPath xpath = new DOMXPath("//action");
      Navigator nav = xpath.getNavigator();
      List selected = xpath.selectNodes(doc);

      logger.debug("[{}] The number of selected nodes : {}", "testNamespaceResolution1", selected.size());
      Assert.assertTrue(selected.size() > 0);
   }

   @Test
   public void testNamespaceResolution2() throws Exception{

      String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      dbf.setValidating(false);

      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new InputSource(ClassLoader.getSystemResourceAsStream(xmlPath)));

      org.jaxen.XPath xpath = new DOMXPath("//wf:action");
      xpath.addNamespace("wf", "uri:oozie:workflow:0.2");
      Navigator nav = xpath.getNavigator();
      List selected = xpath.selectNodes(doc);

      logger.debug("[{}] The number of selected nodes : {}", "testNamespaceResolution2", selected.size());
      Assert.assertTrue(selected.size() > 0);

   }   
   
   
   @Test
   public void testNamespaceResolution3() throws Exception{

      String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      dbf.setValidating(false);

      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new InputSource(ClassLoader.getSystemResourceAsStream(xmlPath)));

      org.jaxen.XPath xpath = new DOMXPath("//action");
      xpath.addNamespace(null, "uri:oozie:workflow:0.2");
      Navigator nav = xpath.getNavigator();
      List selected = xpath.selectNodes(doc);

      logger.debug("[{}] The number of selected nodes : {}", "testNamespaceResolution3", selected.size());
      Assert.assertEquals(selected.size(), 0);

   }

   
   @Test
   public void testNamespaceResolution4() throws Exception{

      String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      dbf.setValidating(false);

      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new InputSource(ClassLoader.getSystemResourceAsStream(xmlPath)));
      
      Map map = new HashMap();
      map.put("", "uri:oozie:workflow:0.2");  // would NOT work as expected.
      
      org.jaxen.XPath xpath = new DOMXPath("//action");
      xpath.setNamespaceContext(new SimpleNamespaceContext(map));
      Navigator nav = xpath.getNavigator();
      List selected = xpath.selectNodes(doc);

      logger.debug("[{}] The number of selected nodes : {}", "testNamespaceResolution4", selected.size());
      Assert.assertEquals(selected.size(), 0);

   }
   
}
