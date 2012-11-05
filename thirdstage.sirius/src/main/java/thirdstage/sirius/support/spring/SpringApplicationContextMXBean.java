package thirdstage.sirius.support.spring;

import javax.management.MXBean;

import org.springframework.beans.factory.config.BeanDefinition;

/**
 * Exposes application context of Spring framework using JMX.
 * <p>The attributes or operations of this MXBean is design to   
 * correspond to the methods of <code>org.springframework.context.ConfigurableApplicationContext</code>
 * directly as possible.
 * </p> 
 * 
 * @see org.springframework.context.ConfigurableApplicationContext
 * @author Sangmoon Oh
 * @version 0.3 2011-06-20 Sangmoon Oh
 * @since 2011-06-20
 *
 */
@MXBean(true)
public interface SpringApplicationContextMXBean {
	
	
	/**
	 * @return
	 * @see org.springframework.context.Lifecycle#isRunning()
	 * @since 0.3
	 */
	boolean isRunning();
	
	
	/**
	 * @return 
	 * @see org.springframework.context.Lifecycle#isRunning()
	 */
	void start();

	/**
	 * @return
	 * @see org.springframework.beans.factory.ListableBeanFactory#getBeanDefinitionCount
	 * @since 0.3
	 */
	int getBeanDefinitionCount();
	
	/**
	 * @return
	 * @see org.springframework.beans.factory.ListableBeanFactory#getBeanDefinitionNames
	 * @since 0.3
	 */
	String[] getBeanDefinitionNames();
	
	
	/**
	 * This attribute doesn't have corresponding one in <code>ConfigurableApplicationContext</code>
	 * @return
	 */
	BeanDefinition[] getBeanDefinitions();
	
	
	

}
