package thirdstage.sirius.support.xml;

import javax.annotation.Nonnull;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * 
 * @version 1.0 2014-10-03, initial
 * @author Sangmoon Oh
 * @since 2014-10-03 
 */
public class ErrorCollectingSax2Handler extends DefaultHandler2{
   
   private final CollectiveSaxErrorHandler errorHandler = new CollectiveSaxErrorHandler();
   
   @Override
   public void warning(SAXParseException ex) throws SAXException{
      errorHandler.warning(ex);
   }
   
   @Override
   public void error(SAXParseException ex) throws SAXException{
      errorHandler.error(ex);
   }
   
   @Override
   public void fatalError(SAXParseException ex) throws SAXException{
      errorHandler.fatalError(ex);
   }
   
   @Nonnull
   public CollectiveSaxErrorHandler getErrorHandler(){
      return this.errorHandler;
   }

}
