package thirdstage.dv.util;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;

/**
 * @author 3rdstage
 * @version 0.8, Sangmoon Oh, 2013-08-02
 * @since 2013-09-02
 */
public final class JsonUtils {
	
	protected static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	private final static ObjectMapper jsonMapper = new ObjectMapper();
	
	private final static JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();

	private static JsonSchema metaSchema;
	
	static {
		try{
			metaSchema = schemaFactory.getJsonSchema("http://json-schema.org/schema");
		}
		catch(Exception ex){
			logger.error("The class is not initialized properly.", ex);
		}
	}
	
	public static boolean validateJson(File schema, File doc, PrintStream out) throws Exception{
		if(schema == null) throw new IllegalArgumentException("...");
		if(doc == null) throw new IllegalArgumentException("");
		if(out == null) throw new IllegalArgumentException("");
		
		JsonNode schmNd = jsonMapper.readTree(schema);
		JsonNode docNd = jsonMapper.readTree(doc);
		
		JsonSchema schm = schemaFactory.getJsonSchema(schmNd);

		ProcessingReport rpt = schm.validate(docNd);
		Iterator<ProcessingMessage> msgs = rpt.iterator();

		ProcessingMessage msg = null;
		while(msgs.hasNext()){
			msg = msgs.next();
			out.println(msg.getMessage());
		}
		
		return rpt.isSuccess();
	}
	
	public static boolean validateJson(URL schema, URL doc, PrintStream out) throws Exception{
		if(schema == null) throw new IllegalArgumentException("");
		if(doc == null) throw new IllegalArgumentException("");
		
		return validateJson(new File(schema.toURI()), new File(doc.toURI()), out);
	}
	
	
	public static boolean validateJson(File doc, PrintStream out) throws Exception{
		if(doc == null) throw new IllegalArgumentException("");
		if(out == null) throw new IllegalArgumentException("");
		if(metaSchema == null) throw new IllegalStateException("This class is not initialized properly.");
		
		JsonNode nd = jsonMapper.readTree(doc);

		ProcessingReport rpt = metaSchema.validate(nd);
		Iterator<ProcessingMessage> msgs = rpt.iterator();

		ProcessingMessage msg = null;
		while(msgs.hasNext()){
			msg = msgs.next();
			out.println(msg.getMessage());
		}
		
		return rpt.isSuccess();	
	}

	public static boolean validateJson(URL doc, PrintStream out) throws Exception{
		if(doc == null) throw new IllegalArgumentException("");
		
		return validateJson(new File(doc.toURI()), out);
	}
}
