package thirdstage.sirius.support.xml;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @version 0.5 2014-10-03, initial
 * @author Sangmoon Oh
 * @since  
 */
public class XPathUtils {
	
	/**
	 * Lock to guard {@code newInstance} methods of {@code XPathFactory} class
	 * which is documented to be neither thread-safe and re-enterant.
	 * 
	 * @see javax.xml.xpath.XPathFactory
	 */
	private final static Lock xpathFactoryLock = new ReentrantLock();
	
	
	@GuardedBy("xpathFactoryLock")
	public static XPath createNewXPathInstance(){
		XPathFactory factory = null;
		
		xpathFactoryLock.lock();
		try{
			factory = XPathFactory.newInstance();
		}finally{
			xpathFactoryLock.unlock();
		}
		
		return factory.newXPath();	
	}
	
	
	
	

}
