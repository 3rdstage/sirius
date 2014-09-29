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
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;
import thirdstage.sirius.support.xml.CollectiveSaxErrorHandler;
import thirdstage.sirius.support.xml.ErrorCollectingSax2Handler;
import thirdstage.sirius.support.xml.XmlErrorBundle;

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

   @Test
   public void testDomParserWithIllformedXml1() throws Exception{
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      dbf.setValidating(false);

      CollectiveSaxErrorHandler errHandler = new CollectiveSaxErrorHandler();
      DocumentBuilder db = null;
      Document doc = null;
      String loc = "thirdstage/sirius/support/oozie/samples/workflow-sample-illformed-1.xml";
      try{
         db = dbf.newDocumentBuilder();
         db.setErrorHandler(errHandler);
         doc = db.parse(ClassLoader.getSystemResourceAsStream(loc));
      }catch(SAXParseException ex){
         XmlErrorBundle errors = errHandler.getErrorBundle();
         errors.print(System.out);
         
         //Actually the file contains more errors than 2
         //DOM parser seems to stop at the first error.
         Assert.assertEquals(errors.getItems().size(), 1);
      }catch(Exception ex){
         throw ex;
      }
   }
   
   @Test
   public void testSaxParserWithIllformedXml1() throws Exception{
      SAXParserFactory spf = SAXParserFactory.newInstance();
      spf.setNamespaceAware(true);
      spf.setValidating(false);
      spf.setFeature("http://xml.org/sax/features/validation", false);
      
      ErrorCollectingSax2Handler handler = new ErrorCollectingSax2Handler();
      SAXParser sp = null;
      String loc = "thirdstage/sirius/support/oozie/samples/workflow-sample-illformed-1.xml";
      try{
         sp = spf.newSAXParser();
         sp.parse(ClassLoader.getSystemResourceAsStream(loc), handler);
      }catch(SAXParseException ex){
         XmlErrorBundle errors = handler.getErrorHandler().getErrorBundle();
         errors.print(System.out);
         
         //Actually the file contains more errors than 2
         //SAX parser seems to stop at the first error.
         Assert.assertEquals(errors.getItems().size(), 1);
      }catch(Exception ex){
         throw ex;
      }
   }
   
   
   @Test
   public void testStreamReaderWithIllformedXml1() throws Exception{
      XMLInputFactory xif = XMLInputFactory.newFactory();
      xif.setProperty(XMLInputFactory.IS_VALIDATING, false);
      xif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
      xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);

      XMLStreamReader reader = null;
      XmlErrorBundle errors = new XmlErrorBundle();
      String loc = "thirdstage/sirius/support/oozie/samples/workflow-sample-illformed-1.xml";
      try{
         reader = xif.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(loc));
         
         for(;;){
            try{
               if(reader.hasNext()){ reader.next(); }
               else{ break; }
            }catch(XMLStreamException ex){
               errors.addItem(new XmlErrorBundle.Item().setLine(ex.getLocation().getLineNumber())
                     .setColumn(ex.getLocation().getColumnNumber())
                     .setTitle(ex.getMessage()).setDesc(ex.getMessage()));
               if(errors.getItems().size() > 1000) break;
            }catch(Exception ex){
               break;
            }
         }
         
      }catch(XMLStreamException ex){
         errors.addItem(new XmlErrorBundle.Item().setLine(ex.getLocation().getLineNumber())
               .setColumn(ex.getLocation().getColumnNumber())
               .setTitle(ex.getMessage()).setDesc(ex.getMessage()));
      }catch(Exception ex){
         throw ex;
      }
      
      errors.print(System.out);
      
   }
   
}