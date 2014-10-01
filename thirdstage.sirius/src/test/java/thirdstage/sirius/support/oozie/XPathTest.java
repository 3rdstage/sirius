package thirdstage.sirius.support.oozie;

import java.io.File;
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
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import thirdstage.sirius.support.xml.UniversalNamespaceContext;
import thirdstage.sirius.support.xml.XPathUtils;

public class XPathTest {

	@Test
	public void testXPathAndNamespaceUsingDom() throws Exception{

		String xmlPath = "thirdstage/sirius/support/oozie/samples/workflow-sample-1.xml";
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setValidating(false);

		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(ClassLoader.getSystemResourceAsStream(xmlPath));

		XPath xpath = XPathUtils.createNewXPathInstance();
		xpath.setNamespaceContext(new UniversalNamespaceContext(doc));
		
		Node nd1 = (Node) xpath.evaluate("//action[0]", doc, XPathConstants.NODE);
		
		Node nd2 = (Node) xpath.evaluate("//uri:oozie:workflow:0.2:action[0]", doc, XPathConstants.NODE);

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
