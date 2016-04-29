package thirdstage.dv.widget.meta;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import checkers.javari.quals.ReadOnly;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonschema.load.Dereferencing;
import com.github.fge.jsonschema.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.tree.CanonicalSchemaTree;
import com.github.fge.jsonschema.tree.SchemaTree;

public class WidgetMetaLoader {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ObjectMapper jsonMapper = new ObjectMapper();
	
	private final LoadingConfiguration loadingConfig = LoadingConfiguration.byDefault()
			.thaw().dereferencing(Dereferencing.CANONICAL)
			.freeze();

	/**
	 * @param path path for meta file in Spring's resource path format.
	 * @return
	 */
	public WidgetMeta load(@Nonnull String path) throws Exception{

		// TODO(Sangmoon Oh, 2013-08-03) : Validation of JSON for meta is necessary first.
		File f = ResourceUtils.getFile(path);
		JsonNode rootNd = jsonMapper.readTree(f);
		SchemaTree schmTr = new CanonicalSchemaTree(rootNd);

		WidgetMeta wm = new WidgetMeta();

		String str = rootNd.get("name").asText(); //name is mandatory
		wm.setName(str);

		str = (rootNd.get("title") != null) ? rootNd.get("title").asText() : "";
		wm.setTitle(str);

		JsonNode propsNd = rootNd.get("properties"); //mandatory	
		Iterator<String> names = propsNd.fieldNames();

		String name = null;
		JsonNode nd = null;
		PartMeta pm = null;
		String type = null;
		while(names.hasNext()){ //for each part of array of part
			name = names.next();
			nd = propsNd.get(name);

			if(nd.has("$ref")){
				//when the whole definition of part can be a reference on a member in definitions
				
			}else if(!nd.has("type")){
					// TODO error or dereferencing
			}else{
		
				type = nd.get("type").asText();

				if("object".equals(type)){
					pm = loadPart(name, nd, schmTr);
					wm.addPartMeta(pm);
				}
			}
		}

		return wm;
	}

	/**
	 * The followings are expected.
	 *  
	 * <pre>
	 *    . The node can has "title" member which is string type.
	 *    . The node should have "properties" member which is object type.
	 *    . The members of "properties" object should be object type.
	 *    . The members of "properties" object can have "type", "maximum"
	 *      "minimum", ... 
	 * </pre>
	 * 
	 * @param nodeName
	 * @param node
	 * @param tree
	 * @return
	 */
	private PartMeta loadPart(@Nonnull String nodeName, @Nonnull @ReadOnly JsonNode node, 
			@Nonnull @ReadOnly SchemaTree tree) throws JsonPointerException{
		PartMeta pm = new PartMeta();
		pm.setName(nodeName);
		pm.setTitle((node.get("title") != null) ? node.get("title").asText() : "");

		if(node.get("properties") == null){
			logger.error("The node of {} doesn't have 'properties' member", nodeName);
			throw new IllegalArgumentException("The node doesn't have 'properties' member");
		}

		Iterator<String> names = node.get("properties").fieldNames();

		String name = null;
		JsonNode nd = null;
		AttributeMeta<?> am = null;
		Boolean required = null;
		String title = null;
		String desc = null;
		String type = null;
		AttributeType at = null;
		Double min = null;
		Boolean minIncludes = null;
		Double max = null;
		Boolean maxIncludes = null;
		Integer minLen = null;
		Integer maxLen = null;
		Object[] enums = null;
		String format = null; 
		String pattern = null;
		Object defaultValue = null;
		// TODO(Sangmoon Oh, 2013-08-8) : enum, default
		while(names.hasNext()){
			name = names.next();
			nd = node.get("properties").get(name);

			if(!JsonNodeType.OBJECT.equals(nd.getNodeType())){
				logger.error("The member of 'properties' should be object");
				throw new IllegalArgumentException("The member of 'properties' should be object");
			}

			//when the property definition has an reference.
			if(nd.get("$ref") != null){
				String path = nd.get("$ref").asText();
				path = path.substring(path.indexOf("#") + 1);
				
				// TODO(Sangmoon Oh) : Access the referenced location using JsonPointer
				JsonNode nd2 = tree.setPointer(new JsonPointer(path)).getNode();
				
				if(!JsonNodeType.OBJECT.equals(nd2.getNodeType())){
					logger.error("The member of definitions should be object");
					throw new IllegalArgumentException("The member of definitions should be object");
				}

				nd = ((ObjectNode)nd2).setAll((ObjectNode)nd);
			}
			
			title = (nd.get("title") != null) ? nd.get("title").asText() : "";

			type = (nd.get("type") != null) ? nd.get("type").asText() : "";
			if(!type.isEmpty()){ at = AttributeType.valueFromJsonType(type); }
			else{ at = AttributeType.UNKNOWN; }
			
			desc = (nd.get("description") != null) ? nd.get("title").asText() : "";
			min = (nd.get("minimum") != null) ? nd.get("minimum").asDouble() : null;
			minIncludes = (nd.get("exclusiveMinimum") != null) ? !nd.get("exclusiveMinimum").asBoolean() : true;
			max = (nd.get("maximum") != null) ? nd.get("maximum").asDouble() : null;
			maxIncludes = (nd.get("exclusiveMaximum") != null) ? !nd.get("exclusiveMaximum").asBoolean() : true;
			minLen = (nd.get("minLength") != null) ? nd.get("minLength").asInt() : null;
			maxLen = (nd.get("maxLength") != null) ? nd.get("maxLength").asInt() : null;
			format = (nd.get("format") != null) ? nd.get("format").asText() : null;
			pattern = (nd.get("pattern") != null) ? nd.get("pattern").asText() : null;
			
			JsonNode nd2 = null;
			switch(at){
			case BOOLEAN :
				defaultValue = (nd.get("default") != null) ? nd.get("default").asBoolean() : null;
				//BOOLEAN type doens't have emums
				if(nd.get("enum") != null){
					this.logger.warn("There's a BOOLEAN type attribute that " +
						"has enum property which is not allowed.");
				}
				break;
			case INTEGER :
				defaultValue = (nd.get("default") != null) ? nd.get("default").asLong() : null;
				if(nd.get("enum") != null){
					Iterator<JsonNode> elements = nd.get("enum").elements();
					List<Long> values = new ArrayList<Long>();
					while(elements.hasNext()){
						values.add(elements.next().asLong());
					}
					if(values.size() > 0){
						enums = values.toArray(new Long[values.size()]);
					}
				}
				break;
			case NUMBER :
				defaultValue = (nd.get("default") != null) ? nd.get("default").asDouble() : null;
				if(nd.get("enum") != null){
					Iterator<JsonNode> elements = nd.get("enum").elements();
					List<Double> values = new ArrayList<Double>();
					while(elements.hasNext()){
						values.add(elements.next().asDouble());
					}
					if(values.size() > 0){
						enums = values.toArray(new Double[values.size()]);
					}
				}
				break;
			case STRING : 
				defaultValue = (nd.get("default") != null) ? nd.get("default").asText() : null;
				if(nd.get("enum") != null){
					Iterator<JsonNode> elements = nd.get("enum").elements();
					List<String> values = new ArrayList<String>();
					while(elements.hasNext()){
						values.add(elements.next().asText());
					}
					if(values.size() > 0){
						enums = values.toArray(new String[values.size()]);
					}
				}
				break;
			default : 
				this.logger.warn("There's an attribute that has not an allowed type.");
			}
			
			//TODO(Sangmoon Oh) : process required 
			
			am = AttributeMetaFactory.getInstance().createAttributeMeta(name, at, 
				null, title, desc, min, minIncludes, max, maxIncludes, minLen, 
				maxLen, enums, format, pattern, defaultValue);
			
			pm.addAttributeMeta(am);
		}

		return pm;
	}
	
	

}

