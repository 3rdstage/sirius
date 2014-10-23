package thirdstage.sirius.support.oozie;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.oclc.purl.dsdl.svrl.FailedAssert;
import org.oclc.purl.dsdl.svrl.SchematronOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import com.helger.commons.io.resource.inmemory.ReadableResourceInputStream;
import com.helger.schematron.ISchematronResource;
import com.helger.schematron.pure.SchematronResourcePure;
import com.helger.schematron.svrl.SVRLFailedAssert;
import com.helger.schematron.svrl.SVRLUtils;

import thirdstage.sirius.support.xml.CollectiveSaxErrorHandler;
import thirdstage.sirius.support.xml.InputSourceProvider;
import thirdstage.sirius.support.xml.XmlErrorBundle;
import thirdstage.sirius.support.xml.XmlErrorBundle.Item;
import thirdstage.sirius.support.xml.XmlErrorBundle.ItemType;


/**
 * 
 * @version 0.8 2014-10-03, initial
 * @author Sangmoon Oh
 * @since
 * @see <a href="https://github.com/apache/oozie/tree/branch-4.0/client/src/main/resources">XML Schemas for Oozie 4.0</a>
 * @see <a href="https://github.com/apache/oozie/blob/master/core/src/main/java/org/apache/oozie/service/SchemaService.java">org.apache.oozie.service.SchemaService.java</a>
 */
@ThreadSafe
public class OozieDefinitionValidator{
   
   private Logger logger = LoggerFactory.getLogger(this.getClass());

   //@todo Make the schemaLocBase and schematronLoc could be overwritten by system variable at VM start-up time.

   protected static String schemaLocBase = "thirdstage/sirius/support/oozie/schemas";

   //classpath relative path of the schematron rule for workflow definition
   protected static String schematronLoc = "thirdstage/sirius/support/oozie/schematrons/workflow.sch";

   protected static Schema workflowSchema; //thread-safe 

   protected static ISchematronResource workflowSchematron; //should use thread-safe impplementation

   public final static String WORKFLOW_NID = "oozie:workflow"; //Namespace Identifier

   public final static Set<String> WORKFLOW_NAMESPACES;

   public final static String SLA_NID = "oozie:sla";

   public final static Set<String> SLA_NAMESPACES;

   public final static String EMAIL_ACTION_NID = "oozie:email-action";

   public final static Set<String> EMAIL_ACTION_NAMESPACES;

   public final static String DISTCP_ACTION_NID = "oozie:distcp-action";

   public final static Set<String> DISTCP_ACTION_NAMESPACES;

   public final static String HIVE_ACTION_NID = "oozie:hive-action";

   public final static Set<String> HIVE_ACTION_NAMESPACES;

   public final static String SHELL_ACTION_NID = "oozie:shell-action";

   public final static Set<String> SHELL_ACTION_NAMESPACES;

   public final static String SQOOP_ACTION_NID = "oozie:sqoop-action";

   public final static Set<String> SQOOP_ACTION_NAMESPACES;

   public final static String SSH_ACTION_NID = "oozie:ssh-action";

   public final static Set<String> SSH_ACTION_NAMESPACES;

   static{
      //init namespace constants		
      WORKFLOW_NAMESPACES = Collections.unmodifiableSet(
            getNamespaces(WORKFLOW_NID, new String[]{"0.1", "0.2", "0.2.5", "0.3", "0.4", "0.5"}));

      SLA_NAMESPACES = Collections.unmodifiableSet(
            getNamespaces(SLA_NID, new String[]{"0.1", "0.2"}));

      EMAIL_ACTION_NAMESPACES = Collections.unmodifiableSet(
            getNamespaces(EMAIL_ACTION_NID, new String[]{"0.1"}));

      DISTCP_ACTION_NAMESPACES = Collections.unmodifiableSet(
            getNamespaces(DISTCP_ACTION_NID, new String[]{"0.1", "0.2"}));

      HIVE_ACTION_NAMESPACES = Collections.unmodifiableSet(
            getNamespaces(HIVE_ACTION_NID, new String[]{"0.2", "0.3", "0.4", "0.5"}));

      SHELL_ACTION_NAMESPACES = Collections.unmodifiableSet(
            getNamespaces(SHELL_ACTION_NID, new String[]{"0.1", "0.2", "0.3"}));

      SQOOP_ACTION_NAMESPACES = Collections.unmodifiableSet(
            getNamespaces(SQOOP_ACTION_NID, new String[]{"0.2", "0.3", "0.4"}));

      SSH_ACTION_NAMESPACES = Collections.unmodifiableSet(
            getNamespaces(SSH_ACTION_NID, new String[]{"0.1", "0.2"}));

      try{

         SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

         Set<String> wfSchLocs = new HashSet<String>();
         wfSchLocs.addAll(getSchemaLocations(WORKFLOW_NAMESPACES));
         wfSchLocs.addAll(getSchemaLocations(SLA_NAMESPACES));
         wfSchLocs.addAll(getSchemaLocations(EMAIL_ACTION_NAMESPACES));
         wfSchLocs.addAll(getSchemaLocations(DISTCP_ACTION_NAMESPACES));
         wfSchLocs.addAll(getSchemaLocations(HIVE_ACTION_NAMESPACES));
         wfSchLocs.addAll(getSchemaLocations(SHELL_ACTION_NAMESPACES));
         wfSchLocs.addAll(getSchemaLocations(SQOOP_ACTION_NAMESPACES));
         wfSchLocs.addAll(getSchemaLocations(SSH_ACTION_NAMESPACES));

         //@todo Review over using StreamSource which can be constructed using File instance
         //instead of InputStream instance.
         Set<SAXSource> wfSchSrcs = new HashSet<SAXSource>();
         for(String loc : wfSchLocs){
            wfSchSrcs.add(new SAXSource(new InputSource(ClassLoader.getSystemResourceAsStream(loc))));
         }

         workflowSchema = sf.newSchema(wfSchSrcs.toArray(new SAXSource[wfSchSrcs.size()]));

      }catch(Exception ex){
         Logger logger = LoggerFactory.getLogger(OozieDefinitionValidator.class);
         logger.error("Fail to initialize OozieDefinitionValidator class.", ex);
         throw new IllegalStateException(ex);
      }

      workflowSchematron = SchematronResourcePure.fromClassPath(schematronLoc);
      if(!workflowSchematron.isValidSchematron()){
         RuntimeException ex = new IllegalStateException("Fail to initialize OozieDefinitionValidator class. - Invalid Scehmatron at " + schematronLoc); 
         Logger logger = LoggerFactory.getLogger(OozieDefinitionValidator.class);
         logger.error(ex.getMessage(), ex);
         throw ex;
      }

   }

   @Nonnull
   private static Set<String> getNamespaces(@Nonnull String nid, @Nonnull String[] vers){
      Set<String> namespaces = new HashSet<String>();
      for(String ver : vers){
         namespaces.add(new StringBuilder(30).append("uri:")
               .append(nid).append(":").append(ver).toString());
      }
      return namespaces;
   }

   private static Set<String> getSchemaLocations(@Nonnull Set<String> namespaces){
      Set<String> locs = new HashSet<String>();
      String loc;
      for(String namespace : namespaces){
         loc = namespace.replace(":", "-");
         if(loc.startsWith("uri-oozie-workflow") || loc.startsWith("uri-oozie-coordinator") 
               || loc.startsWith("uri-oozie-bundle") || loc.startsWith("uri-oozie-sla")){
            loc = loc.substring(4);
         }else{
            loc = loc.substring(10);
         }
         locs.add(new StringBuilder(80).append(schemaLocBase).append("/")
               .append(loc).append(".xsd").toString());
      }
      return locs;
   }

   public OozieDefinitionValidator(){}



   @Nonnull
   private XmlErrorBundle validateWorkflowDefinition(@Nonnull InputSource src){
      if(src == null) throw new IllegalArgumentException("The input should not be null.");

      CollectiveSaxErrorHandler errHandler = new CollectiveSaxErrorHandler();

      //@todo Consider just using Xerces2 implementation.
      //@todo Try pull parsers.  http://www.xmlpull.org/impls.shtml
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true); //Definitely should be TRUE for XML Schema validation.
      dbf.setValidating(false);
      DocumentBuilder db = null;
      Document doc = null;

      //Parse the definition with DOM Parser.
      //This process include checking well-formedness of the definition.
      try{
         db = dbf.newDocumentBuilder();
         db.setErrorHandler(errHandler);
         doc = db.parse(src);
      }catch(RuntimeException ex){
         throw ex;
      }catch(SAXParseException ex){
         if(doc == null) return errHandler.getErrorBundle();
      }catch(Exception ex){
         throw new RuntimeException("Fail to parse the XML document", ex);
      }

      //Check the validity of the definition using XML Schema
      Validator validator = workflowSchema.newValidator();
      validator.setErrorHandler(errHandler);
      DOMSource ds = new DOMSource(doc);
      try{
         validator.validate(ds);

         //return errHandler.getErrorBundle();
      }catch(RuntimeException ex){
         if(errHandler.getErrorBundle() != null){ 
            //return errHandler.getErrorBundle(); 
         }
         else{ throw ex; }
      }catch(Exception ex){
         if(errHandler.getErrorBundle() != null){ 
            //return errHandler.getErrorBundle(); 
         }
         else{ throw new RuntimeException("Fail to validate the XML document.", ex); }
      }


      //Check the additional validity of the definition using Schematron.
      SchematronOutputType output = null;
      XmlErrorBundle bundle = errHandler.getErrorBundle();
      try{
         output = ((SchematronResourcePure)workflowSchematron).applySchematronValidation(doc);
      }catch(Exception ex){
         throw new RuntimeException("Fail to validate the XML documentat using Schematron at " + schematronLoc, ex);
      }

      List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output); //output is guaranteed non-null from the API 
      for(SVRLFailedAssert fail : fails){
         bundle.addItem(new Item().setLocationHint(fail.getLocation())
               .setType(ItemType.ERROR).setMessage(StringUtils.trim(fail.getText())));

      }

      return bundle;
   }	
   
   @Nonnull
   private XmlErrorBundle validateWorkflowDefinition(@Nonnull Object base, @Nonnull InputSourceProvider provider){
      if(base == null || provider == null) throw new IllegalArgumentException("The input should not be null.");

      CollectiveSaxErrorHandler errHandler = new CollectiveSaxErrorHandler();

      //@todo Consider just using Xerces2 implementation.
      //@todo Try pull parsers.  http://www.xmlpull.org/impls.shtml
      SAXParserFactory spf = SAXParserFactory.newInstance();
      spf.setNamespaceAware(true);
      spf.setValidating(false);
      
      SAXParser sp = null; //JAXP SAX parser interface
      XMLReader parser = null;  //standard SAX2 parser interface
      
      //Parse the definition with DOM Parser.
      //This process include checking well-formedness of the definition.
      try{
         spf.setFeature("http://xml.org/sax/features/validation", false);
         sp = spf.newSAXParser();
         parser = sp.getXMLReader(); //standard SAX2 parser
         parser.setErrorHandler(errHandler);
         parser.parse(provider.getInputSourceFrom(base));
      }catch(RuntimeException ex){
         throw ex;
      }catch(SAXParseException ex){
         //this.logger.error("Fail to parser the XML document", ex);
         return errHandler.getErrorBundle();
      }catch(Exception ex){
         throw new RuntimeException("Fail to parse the XML document", ex);
      }

      //Check the validity of the definition using XML Schema
      //reuse of SAXParser is enabled through reset() 
      Validator validator = workflowSchema.newValidator();
      validator.setErrorHandler(errHandler);
      //reuse of InputSource is guaranteed by SAX standard. 
      //Refer http://docs.oracle.com/javase/6/docs/api/index.html?org/xml/sax/InputSource.html 
      Source input = new SAXSource(parser, provider.getInputSourceFrom(base));
      try{
         validator.validate(input);
      }catch(RuntimeException ex){
         if(errHandler.getErrorBundle() != null){ 
            //this.logger.error("Fail to parser the XML document", ex);
            return errHandler.getErrorBundle(); 
         }
         else{ throw ex; }
      }catch(Exception ex){
         if(errHandler.getErrorBundle() != null){ 
            //this.logger.error("Fail to parser the XML document", ex);
            return errHandler.getErrorBundle(); 
         }
         else{ throw new RuntimeException("Fail to validate the XML document.", ex); }
      }

      //Check the additional validity of the definition using Schematron.
      SchematronOutputType output = null;
      XmlErrorBundle bundle = errHandler.getErrorBundle();
      InputStream is = null;
      ReadableResourceInputStream rris = null;
      try{
         
         /* SAXSource is not supported
         output = ((SchematronResourcePure)workflowSchematron).applySchematronValidationToSVRL(
                     new SAXSource(provider.getInputSourceFrom(base))); */
         
         /*
         is = provider.getInputSourceFrom(base).getByteStream(); //InputSource is reused again.
         rris = new ReadableResourceInputStream(is);
         output = ((SchematronResourcePure)workflowSchematron).applySchematronValidationToSVRL(rris);
         */

         output = ((SchematronResourcePure)workflowSchematron).applySchematronValidationToSVRL(
                     new StreamSource(provider.getInputSourceFrom(base).getByteStream()));
      }catch(Exception ex){
         throw new RuntimeException("Fail to validate the XML documentat using Schematron at " + schematronLoc, ex);
      }

      List<SVRLFailedAssert> fails = SVRLUtils.getAllFailedAssertions(output); //output is guaranteed non-null from the API 
      for(SVRLFailedAssert fail : fails){
         bundle.addItem(new Item().setLocationHint(fail.getLocation())
               .setType(ItemType.ERROR).setMessage(StringUtils.trim(fail.getText())));

      }

      return bundle;
   }     

   /**
    * The return value is NOT {@code null} but empty, when the specified XML is valid.
    * 
    * 
    * @param resourceLoc
    * @return XmlErrorBundle object that contains illformedness or non-validness.
    */
   @Nonnull
   public XmlErrorBundle validateWorkflowDefinitionOnClasspath(@Nonnull String defLoc){
      if(defLoc == null) throw new IllegalArgumentException("The input should not be null.");

      //InputSource or SAX Parser is to close the stream at end-of-parse cleanup.
      //Refer the API documentation of org.xml.sax.InputSource
      //return this.validateWorkflowDefinition(new InputSource(ClassLoader.getSystemResourceAsStream(defLoc)));
      return this.validateWorkflowDefinition(defLoc, 
            new InputSourceProvider(){
               
               @Override public InputSource getInputSourceFrom(Object base){
                  String loc = (String)base;
                  return new InputSource(ClassLoader.getSystemResourceAsStream(loc));
               }
      });
   }

   /**
    * The return value is NOT {@code null} but empty, when the specified XML is valid.
    * 
    * 
    * @param resourceLoc
    * @return XmlErrorBundle object that contains illformedness or non-validness.
    */
   @Nonnull
   public XmlErrorBundle validateWorkflowDefinitionString(@Nonnull String def){
      if(def == null) throw new IllegalArgumentException("The input should not be null.");

      //InputSource or SAX Parser is to close the stream at end-of-parse cleanup.
      //Refer the API documentation of org.xml.sax.InputSource
      //return this.validateWorkflowDefinition(new InputSource(new StringReader(def)));
      
      return this.validateWorkflowDefinition(def, 
            new InputSourceProvider(){
               
               @Override public InputSource getInputSourceFrom(Object base){
                  return new InputSource(new StringReader((String)base));
               }
      });
   }

   

}



