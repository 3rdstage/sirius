package thirdstage.dv.widget.meta;

import java.io.File;
import java.util.Iterator;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import checkers.javari.quals.ReadOnly;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class WidgetMetaBuilder {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ObjectMapper jsonMapper = new ObjectMapper();

	/**
	 * @param path path for meta file in Spring's resource path format.
	 * @return
	 */
	public WidgetMeta build(@Nonnull String path) throws Exception{

		// TODO(Sangmoon Oh, 2013-08-03) : Validation of JSON for meta is necessary first.
		File f = ResourceUtils.getFile(path);
		JsonNode rootNd = jsonMapper.readTree(f);

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
		while(names.hasNext()){
			name = names.next();
			nd = propsNd.get(name);

			if(!nd.has("type")){
				// TODO error or dereferencing
			}else{

				type = nd.get("type").asText();

				if("object".equals(type)){
					pm = buildPart(name, nd);
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
	 * @return
	 */
	private PartMeta buildPart(@Nonnull String nodeName, @Nonnull @ReadOnly JsonNode node){
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
		AttributeMeta am = null;
		String type = null;
		while(names.hasNext()){
			name = names.next();
			nd = node.get("properties").get(name);

			if(!JsonNodeType.OBJECT.equals(nd.getNodeType())){
				logger.error("The member of 'properties' should be object");
				throw new IllegalArgumentException("The member of 'properties' should be object");
			}

			am = new AttributeMeta();
			am.setName(name);
			type = (nd.get("type") != null) ? nd.get("type").asText() : "";
			if(!type.isEmpty()){
				am.setType(AttributeType.valueFromJsonType(type));
			}
			pm.addAttributeMeta(am);
		}

		return pm;
	}

}
