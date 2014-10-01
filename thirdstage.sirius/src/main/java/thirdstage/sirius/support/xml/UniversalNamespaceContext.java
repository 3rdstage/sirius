package thirdstage.sirius.support.xml;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * Provide namespace context based on the specified DOM document.
 * Borrowed by the Holger Kraus's article at IBM developerWorks which can be found
 * at <a href="http://www.ibm.com/developerworks/xml/library/x-nmspccontext/index.html">Using the Java language NamespaceContext object with XPath</a>
 * 
 * @author 3rdstage
 *
 */
public class UniversalNamespaceContext implements NamespaceContext {
	
	private final Document doc;
	
	public UniversalNamespaceContext(@Nonnull Document doc){
		if(doc == null) throw new IllegalArgumentException("The document to lookup the namespace shouldn't be null.");
		
		this.doc = doc;
	}

	@Override
	public String getNamespaceURI(String prefix) {
		if(XMLConstants.DEFAULT_NS_PREFIX.equals(prefix)){
			return doc.lookupNamespaceURI(null);
		}else if("".equals(prefix)){
			return doc.lookupNamespaceURI(null);
		}else{
			return doc.lookupNamespaceURI(prefix);
		}
	}

	@Override
	public String getPrefix(String namespaceURI) {
		return doc.lookupPrefix(namespaceURI);
	}

	@Override
	public Iterator getPrefixes(String namespaceURI) {
		return null;
	}

}
