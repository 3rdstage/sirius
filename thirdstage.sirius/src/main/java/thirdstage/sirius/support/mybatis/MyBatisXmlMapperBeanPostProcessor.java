package thirdstage.sirius.support.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 *
 * @author 3rdstage
 *
 */
public class MyBatisXmlMapperBeanPostProcessor implements BeanPostProcessor {

	/**
	 * Currently do nothing
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		// TODO Auto-generated method stub
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		
		if(bean == null) return bean;
		
		if(bean.getClass().isAnnotationPresent(MyBatisXmlMapper.class)){
			
			MyBatisXmlMapper mapperAnn = bean.getClass().getAnnotation(MyBatisXmlMapper.class);
			String[] locations = mapperAnn.locations();
			
			Field[] fields = bean.getClass().getDeclaredFields();
			SqlSessionFactory fac = null;
			SqlSession sess = null;
			Configuration config = null;
			
			//for each field in the class
			for(Field field : fields){
				
				if(SqlSessionFactory.class.isAssignableFrom(field.getType())
						|| SqlSession.class.isAssignableFrom(field.getType())){
					try{
						if(SqlSessionFactory.class.isAssignableFrom(field.getType())){
							fac = (SqlSessionFactory)(field.get(bean));
							config = fac.getConfiguration();
						}
						else if(SqlSession.class.isAssignableFrom(field.getType())){
							sess = (SqlSession)(field.get(bean));
							config = sess.getConfiguration();
						}
						
						//@todo Needs logics when some of the specified XMLMapper was already 
						//loaded into the SQL session before.
						
						InputStream is = null;
						XMLMapperBuilder builder = null;
						for(String location : locations){
							try{
								is = Resources.getResourceAsStream(location);
								builder = new XMLMapperBuilder(is, config, location, config.getSqlFragments());
								builder.parse();
							}
							catch(IOException ex){
								throw ex;
							}
							finally{
								is.close();
							}
						}
						
					}catch(Exception ex){
						//Sangmoon Oh, 2011-06-03
						//This is not fatal situation. But there's no NormalBeanException. --; 
						throw new FatalBeanException(
								"There is an error to post process the bean of " + beanName  + " after initialization", ex);
					}
				}
			}
		}
		
		return bean;
	}

}
