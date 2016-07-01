package thirdstage.sirius.support.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Singleton;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Locates Spring containers.
 * <p>
 * This class can be used to access Spring container from where DI is not possible.
 * <p>
 * Note that this class is thread-safe and caches the Spring containers
 *
 * @author Sangmoon Oh
 */
@Singleton
@ThreadSafe
public class SpringContainerLocator{

   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   private static SpringContainerLocator instance;

   private volatile Map<String, ConfigurableApplicationContext> cache = new HashMap<String, ConfigurableApplicationContext>();

   private final Lock cacheLock = new ReentrantLock();

   /**
    * Test purpose filed to confirm concurrency in high congestion.
    */
   private final AtomicInteger count = new AtomicInteger(0);

   /**
    * Gets the count of Spring container loadings.
    * This method is test only purpose.
    */
   protected int getCount(){ return this.count.intValue(); }

   static{
      //No need on lazy initialization.
      if(instance == null){ instance = new SpringContainerLocator(); }
   }

   private SpringContainerLocator(){}

   public static SpringContainerLocator getSingleton(){ return instance; }

   /**
    * Gets the Spring container configured by the specified configuration file.
    * <p>
    * Currently, the location of Spring configuration file should be
    * <emp>classpath relative</emp>. The absolute or relative to file system value
    * such as 'c:/opt/vas/conf/spring.xml' or '/opt/vas/conf/spring.xml' is not supported.
    * <p>
    * The loaded Spring container will be registered shutdown hook, so you don't need to do in your code.
    *
    * @param configLoc The location of configuration file on classpath. such as 'foo/bar/spring.xml'
    * @return The Spring container configured by the specified configLoc.
    */
   @GuardedBy("cacheLock")
   public ApplicationContext getSpringContainer(@NotBlank String configLoc){
      Validate.isTrue(StringUtils.isNotBlank(configLoc), "The location of configuration should be specified.");
      ConfigurableApplicationContext container = cache.get(configLoc);

      if(container == null){ //first check
         cacheLock.lock();
         try{
            container = cache.get(configLoc);
            if(container == null){ //send check
               int cnt = this.count.incrementAndGet();

               //Never expected log that includes 2-th or 3-th ...
               logger.debug("The {}-th loading of Spring container[config: {}]", cnt, configLoc);

               //@TODO Use GenericXmlApplicationContext and for prefix-free location
               //      treat it classpath relative one.
               container = new ClassPathXmlApplicationContext(configLoc);
               container.registerShutdownHook();
               cache.put(configLoc, container);
            }else{
               logger.debug("Fetch the cached Spring conainer[config: {}]", configLoc);
            }
         }catch(Throwable ex){
            logger.error("Fail to load spring container whose configuration is {} on classpath.", configLoc, ex);
            throw new RuntimeException("Fail to load spring container whose configuration is" + configLoc + " on classpath.", ex);
         }finally{
            cacheLock.unlock();
         }
      }

      return container;
   }

}
