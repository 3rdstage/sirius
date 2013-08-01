package thirdstage.dv.util;

import java.io.File;
import java.io.PrintStream;

import com.github.fge.jsonschema.main.JsonSchemaFactory;

public class JsonUtils {
	
	
	public boolean validateJson(File schema, File doc, PrintStream out){
		if(schema == null) throw new IllegalArgumentException("...");
		if(doc == null) throw new IllegalArgumentException("");
		if(out == null) throw new IllegalArgumentException("");
		
		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		
		return false;
	}
	
	
	public boolean validateJson(File doc, PrintStream out){
		return false;
	}
	
	

}
