package thirdstage.sirius.support.spring;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

public class DefaultSpringApplicationContextMXBean implements
		SpringApplicationContextMXBean {
	
	
	/* just ApplicationContext is not enough, cause it dosen't provide methods
	 * defined in ConfigurableApplicationContext interface.
	 */
	private AbstractApplicationContext context;
	
	
	protected AbstractApplicationContext getApplicationContext(){
		return this.context;
	}
	
	@Autowired
	public void setApplicationContext(AbstractApplicationContext context){
		this.context = context;
	}

	
	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getBeanDefinitionCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getBeanDefinitionNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BeanDefinition[] getBeanDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
