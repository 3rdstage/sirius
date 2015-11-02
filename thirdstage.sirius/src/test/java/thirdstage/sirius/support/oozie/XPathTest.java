package thirdstage.sirius.support.oozie;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import thirdstage.sirius.support.xml.UniversalNamespaceContext;
import thirdstage.sirius.support.xml.XPathUtils;

public class XPathTest {
   
   private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testXPathAndNamespaceUsingDom1() throws Exception{

		String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setValidating(false);

		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(ClassLoader.getSystemResourceAsStream(xmlPath));

		XPath xpath = XPathUtils.createNewXPathInstance();
		xpath.setNamespaceContext(new OozieXmlNamespaceContext());
		
		NodeList nodes = (NodeList) xpath.evaluate("//action[0]", doc, XPathConstants.NODESET);
		
		logger.debug("[{}] The number of selected nodes : {}", "testXPathAndNamespaceUsingDom1", nodes.getLength());
		Assert.assertEquals(nodes.getLength(), 0);
		

	}
	
   @Test(expectedExceptions={javax.xml.xpath.XPathExpressionException.class})
   public void testXPathAndNamespaceUsingDom2() throws Exception{

      String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
      
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      dbf.setValidating(false);

      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(ClassLoader.getSystemResourceAsStream(xmlPath));

      XPath xpath = XPathUtils.createNewXPathInstance();
      xpath.setNamespaceContext(new OozieXmlNamespaceContext());
      
      NodeList nodes = (NodeList) xpath.evaluate("//uri:oozie:workflow:0.2:action[0]", doc, XPathConstants.NODESET);

   }
	
	
	@Test(expectedExceptions={java.lang.ClassCastException.class})
	public void testXPathAndNamespaceUsingSax() throws Exception{

		String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
		
		InputSource is = new InputSource(ClassLoader.getSystemResourceAsStream(xmlPath));
		XPath xpath = XPathUtils.createNewXPathInstance();
		//xpath.setNamespaceContext(new UniversalNamespaceContext(doc));
		
		//Next line would throw ClassCastException in some case. But can't be understood and seems to
		//violate the API
		NodeList nd1 = (NodeList)xpath.evaluate("//action[0]", is, XPathConstants.NODESET);
		
	}

}

class OozieXmlNamespaceContext implements NamespaceContext{
   
   @Override
   public String getNamespaceURI(String prefix){
      
      if(XMLConstants.NULL_NS_URI.equals(prefix)){
         return "uri:oozie:workflow:0.2";
      }else{
         return "";
      }
      
   }
   
   @Override
   public String getPrefix(String namespaceUri){
      
      if("uri:oozie:workflow:0.2".equals(namespaceUri)){
         return XMLConstants.NULL_NS_URI;
      }else{
         return "";
      }
      
   }
   
   @Override
   public Iterator getPrefixes(String namespaceUri){
      return null;
   }
   
}
