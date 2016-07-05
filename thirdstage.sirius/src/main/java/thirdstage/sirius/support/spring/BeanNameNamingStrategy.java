package thirdstage.sirius.support.spring;

import java.util.Hashtable;
import javax.annotation.Nullable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;

/**
 * @author Sangmoon Oh
 * @since 2016-06-30
 *
 */
public class BeanNameNamingStrategy implements ObjectNamingStrategy{

   private final String domain;

   private final Hashtable<String, String> baseProps= new Hashtable<String, String>();

   public BeanNameNamingStrategy(@Nullable String domain, @Nullable String type){
      if(StringUtils.isNotBlank(domain)) this.domain = domain;
      else this.domain = "";

      if(StringUtils.isNotBlank(type)){
         this.baseProps.put("type", type);
      }
   }

   @Override
   public ObjectName getObjectName(Object managedBean, String beanKey)
      throws MalformedObjectNameException{
      Hashtable<String, String> props = new Hashtable<String, String>();
      props.putAll(this.baseProps);
      props.put("name", beanKey);

      return new ObjectName(this.domain, props);
   }

}
