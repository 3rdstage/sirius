package thirdstage.sirius.support.oozie;

import java.io.InputStream;
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
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import thirdstage.sirius.support.xml.CollectiveSaxErrorHandler;
import thirdstage.sirius.support.xml.XmlErrorBundle;
import thirdstage.sirius.support.xml.XmlErrorBundle.ItemType;


/**
 * @author 3rdstage
 * @see <a href="https://github.com/apache/oozie/tree/branch-4.0/client/src/main/resources">XML Schemas for Oozie 4.0</a>
 * @see <a href="https://github.com/apache/oozie/blob/master/core/src/main/java/org/apache/oozie/service/SchemaService.java">org.apache.oozie.service.SchemaService.java</a>
 */
@ThreadSafe
public class OozieDefinitionValidator{

	protected static String schemaLocBase = "thirdstage/sirius/support/oozie/schemas";

	protected static Schema workflowSchema; //thread-safe 

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

			Set<SAXSource> wfSchSrcs = new HashSet<SAXSource>();
			for(String loc : wfSchLocs){
				wfSchSrcs.add(new SAXSource(new InputSource(ClassLoader.getSystemResourceAsStream(loc))));
			}

			workflowSchema = sf.newSchema(wfSchSrcs.toArray(new SAXSource[wfSchSrcs.size()]));
			
		}catch(Exception ex){
			Logger logger = LoggerFactory.getLogger(OozieDefinitionValidator.class);
			logger.error("Fail to initialize OozieDefinitionValidator class.", ex);
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
	
	/**
	 * The return value is NOT {@code null} but empty, when the specified XML is valid.
	 * 
	 * 
	 * @param resourceLoc
	 * @return XmlErrorBundle object that contains illformedness or non-validness.
	 */
	@Nonnull
	public XmlErrorBundle validateWorkflowDefinition(@Nonnull String resourceLoc){
	   CollectiveSaxErrorHandler errHandler = new CollectiveSaxErrorHandler();
	   
	   //@todo Consider just using Xerces2 implementation.
	   //@todo Try pull parsers.  http://www.xmlpull.org/impls.shtml
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setValidating(false);
		DocumentBuilder db = null;
		InputStream is = null;
		Document doc = null;

		try{
			db = dbf.newDocumentBuilder();
			db.setErrorHandler(errHandler);
			is = ClassLoader.getSystemResourceAsStream(resourceLoc);
			doc = db.parse(is);
		}catch(RuntimeException ex){
			throw ex;
		}catch(SAXParseException ex){
		   if(doc == null) return errHandler.getErrorBundle();
		}catch(Exception ex){
			throw new RuntimeException("Fail to parse the XML document at " + resourceLoc, ex);
		}finally{
			if(is != null){
				try{ is.close(); }catch(Exception ex){}
			}
		}
		
		Validator validator = workflowSchema.newValidator();
		validator.setErrorHandler(errHandler);
		try{
			validator.validate(new DOMSource(doc));
			
			return errHandler.getErrorBundle();
		}catch(RuntimeException ex){
		   if(errHandler.getErrorBundle() != null){ return errHandler.getErrorBundle(); }
		   else{ throw ex; }
		}catch(Exception ex){
		   if(errHandler.getErrorBundle() != null){ return errHandler.getErrorBundle(); }
		   else{ throw new RuntimeException("Fail to validate the XML document at " + resourceLoc, ex); }
		}
		
	}


}
