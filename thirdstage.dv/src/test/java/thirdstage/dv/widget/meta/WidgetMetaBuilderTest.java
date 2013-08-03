package thirdstage.dv.widget.meta;


import org.testng.Assert;
import org.testng.annotations.Test;

public class WidgetMetaBuilderTest {

	@Test
	public void testBuild() throws Exception {
		
		WidgetMetaBuilder builder = new WidgetMetaBuilder();
		
		String path = "classpath:thirdstage/dv/widget/meta/pie-chart.meta.json";
		WidgetMeta meta = builder.build(path);
		
		System.out.println(meta.toString());
		
		Assert.assertNotNull(meta);
	}
}
