package thirdstage.sirius.support.mybatis;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MyBatisXmlMapper annotation specifies the MyBatis mapper file in XML format.
 * <p>This annotation can be used to load the specified mapper and inject
 * it to the SqlSession or SqlSessionFactory instance.
 * 
 * @author Sangmoon Oh
 * @version 1.0
 * 
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyBatisXmlMapper {
	
	
	/**
	 * The locations of XML mapper files.
	 * <p>Each string is expected to for classpath resource with or without 
	 * "classpath:" prefix 
	 * 
	 * @since 1.0
	 */
	String[] locations() default {};
	

}
