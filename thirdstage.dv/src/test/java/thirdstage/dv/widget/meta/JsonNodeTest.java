package thirdstage.dv.widget.meta;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.expand.SchemaExpander;
import com.github.fge.jsonschema.load.Dereferencing;
import com.github.fge.jsonschema.load.RefResolver;
import com.github.fge.jsonschema.load.SchemaLoader;
import com.github.fge.jsonschema.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.ref.JsonRef;
import com.github.fge.jsonschema.report.ConsoleProcessingReport;
import com.github.fge.jsonschema.tree.BaseSchemaTree;
import com.github.fge.jsonschema.tree.CanonicalSchemaTree;
import com.github.fge.jsonschema.tree.SchemaTree;
import com.github.fge.jsonschema.util.ValueHolder;
import com.github.fge.jsonschema.walk.ResolvingSchemaWalker;
import com.github.fge.jsonschema.walk.SchemaWalker;
import com.github.fge.jsonschema.walk.SchemaWalkingConfiguration;

public class JsonNodeTest {

	private static ObjectMapper jsonMapper;
	private static String path1 = "thirdstage/dv/widget/meta/pie-chart-meta-sample.json";
	private static JsonNode node1;


	@BeforeTest
	public void beforeTest() throws Exception{
		jsonMapper = new ObjectMapper();
		URL url = ClassLoader.getSystemResource(path1);
		node1 = jsonMapper.readTree(new File(url.toURI()));
	}


	@Test
	public void testFieldNames(){

		JsonNode nd = node1.get("properties");
		Iterator<String> itr = nd.fieldNames();
		List<String> names = new ArrayList<String>();

		while(itr.hasNext()){
			names.add(itr.next());
		}

		Assert.assertTrue(names.size() > 0);
	}

	@Test
	public void testReferencedMember(){

		JsonNode nd = node1.get("properties").get("title")
				.get("properties").get("position");

		Iterator<String> names = nd.fieldNames();

		String name = null;
		Map<String, String> members = new HashMap<String, String>();
		while(names.hasNext()){
			name = names.next();
			members.put(name, nd.get(name).asText());
		}

		Assert.assertEquals(members.size(), 2);
		Assert.assertTrue(members.containsKey("$ref"));
		Assert.assertTrue(members.containsKey("default"));
	}

	@Test
	public void testCanonicalSchemaTree(){
		
		JsonNode nd = node1.get("properties").get("title")
				.get("properties").get("position");

		SchemaTree st = new CanonicalSchemaTree(nd);
		nd = st.getNode();

		Iterator<String> names = nd.fieldNames();

		String name = null;
		Map<String, String> members = new HashMap<String, String>();
		while(names.hasNext()){
			name = names.next();
			members.put(name, nd.get(name).asText());
			
			System.out.printf("key : %1$s, value : %2$s \n", name, members.get(name));
		}

		Assert.assertEquals(members.size(), 2);
		Assert.assertTrue(members.containsKey("$ref"));
		Assert.assertTrue(members.containsKey("default"));
	}
	
	@Test
	public void testDereferencing() throws Exception{
		
		LoadingConfiguration conf = LoadingConfiguration.byDefault()
				.thaw().dereferencing(Dereferencing.CANONICAL)
				.freeze();
		
		SchemaLoader loader = new SchemaLoader(conf); 
		RefResolver rslv = new RefResolver(loader);
		
		SchemaTree st = loader.load(node1);
		SchemaTree st2 = rslv.process(new ConsoleProcessingReport(), ValueHolder.hold(st)).getValue();
	
		JsonNode nd = st2.getNode().get("properties").get("title")
				.get("properties").get("position");
		
		Iterator<String> names = nd.fieldNames();

		String name = null;
		Map<String, String> members = new HashMap<String, String>();
		while(names.hasNext()){
			name = names.next();
			members.put(name, nd.get(name).asText());
			
			System.out.printf("key : %1$s, value : %2$s \n", name, members.get(name));
		}

		Assert.assertEquals(members.size(), 2);
		Assert.assertTrue(members.containsKey("$ref"));
		Assert.assertTrue(members.containsKey("default"));
	}
	
	
	@Test
	public void testSchemaWalker() throws Exception{
		
		LoadingConfiguration cfg = LoadingConfiguration.byDefault()
				.thaw().dereferencing(Dereferencing.CANONICAL)
				.freeze();
				
		SchemaTree st = new CanonicalSchemaTree(node1);
		
		SchemaWalkingConfiguration swCfg = SchemaWalkingConfiguration.byDefault()
				.thaw().setLoadingConfiguration(cfg).freeze();
		SchemaWalker sw = new ResolvingSchemaWalker(st, swCfg);
		sw.walk(new SchemaExpander(st), new ConsoleProcessingReport());
		
		JsonNode nd = st.getNode().get("properties").get("title")
				.get("properties").get("position");
		
		Iterator<String> names = nd.fieldNames();

		String name = null;
		Map<String, String> members = new HashMap<String, String>();
		while(names.hasNext()){
			name = names.next();
			members.put(name, nd.get(name).asText());
			
			System.out.printf("key : %1$s, value : %2$s \n", name, members.get(name));
		}

		Assert.assertEquals(members.size(), 2);
		Assert.assertTrue(members.containsKey("$ref"));
		Assert.assertTrue(members.containsKey("default"));
	}
}
