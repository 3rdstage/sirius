package thirdstage.dv.widget.meta;


import org.testng.Assert;
import org.testng.annotations.Test;

public class WidgetMetaLoaderTest {

	@Test
	public void testBuild() throws Exception {
		
		WidgetMetaLoader loader = new WidgetMetaLoader();
		
		String path = "classpath:thirdstage/dv/widget/meta/pie-chart.meta.json";
		WidgetMeta meta = loader.load(path);
		
		System.out.println(meta.toString());
		
		Assert.assertNotNull(meta);
	}
}
