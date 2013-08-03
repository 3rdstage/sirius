package thirdstage.dv.widget.meta;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

}
