package thirdstage.sirius.support.spring;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration("/thirdstage/sirius/support/spring/spring.xml")
public class DefaultSpringApplicationContextMXBeanTest extends AbstractTestNGSpringContextTests {


	@Resource(name="springApplicationContextMXBean")
	private SpringApplicationContextMXBean mxBean;
	

	@Test
	public void testBasic() {
		
		Assert.assertNotNull(this.mxBean);

		ApplicationContext context = ((DefaultSpringApplicationContextMXBean)(this.mxBean)).getApplicationContext();
		Assert.assertNotNull(context);
		
		System.out.printf("String representation of the context object : %1$s%n", context);
		System.out.printf("Class of the context object : %1$s%n", context.getClass().getName());
		
		String[] names = context.getBeanDefinitionNames();
		System.out.println("Names of beans defined in the context : ");
		for(String name: names){
			System.out.printf("     %1$s%n", name);
		}
		
		Assert.assertEquals(context.getBeanDefinitionCount(), names.length);
		
	}
	
	
	
}
