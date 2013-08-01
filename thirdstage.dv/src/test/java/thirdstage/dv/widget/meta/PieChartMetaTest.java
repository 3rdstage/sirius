package thirdstage.dv.widget.meta;

import java.net.URL;
import java.util.Iterator;

import junit.framework.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.report.ProcessingMessage;
import com.github.fge.jsonschema.report.ProcessingReport;

public class PieChartMetaTest {

	private JsonSchemaFactory schemaFactory;
	private URL metaSchemaUrl;
	private URL schemaUrl1;
	private URL docUrl1;
	private ObjectMapper jsonMapper;
	
	@BeforeClass
	public void beforeClass() throws Exception{
		this.schemaFactory = JsonSchemaFactory.byDefault();
		this.metaSchemaUrl = new URL("http://json-schema.org/schema");
		this.schemaUrl1 = ClassLoader.getSystemResource("thirdstage/dv/widget/meta/pie-chart.meta.json");
		this.docUrl1 = ClassLoader.getSystemResource("thirdstage/dv/widget/meta/pie-chart.json");
		
		this.jsonMapper = new ObjectMapper();
	}	
	
	@Test
	public void testIsValidMeta() throws Exception{
		JsonNode metaSchmNd = this.jsonMapper.readTree(this.metaSchemaUrl);
		JsonNode schmNd = this.jsonMapper.readTree(schemaUrl1);
		JsonSchema schm = this.schemaFactory.getJsonSchema(metaSchmNd);
		
		ProcessingReport rpt = schm.validate(schmNd);
		Iterator<ProcessingMessage> msgs = rpt.iterator();

		ProcessingMessage msg = null;
		while(msgs.hasNext()){
			msg = msgs.next();
			System.out.println(msg.getMessage());
		}
		
		Assert.assertTrue(rpt.isSuccess());
	}

}
