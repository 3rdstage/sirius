package thirdstage.sirius.support.xml;

import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.DefaultHandler;
import thirdstage.sirius.support.xml.XmlErrorBundle.Item;
import thirdstage.sirius.support.xml.XmlErrorBundle.ItemType;

@NotThreadSafe
public class CollectiveSaxErrorHandler implements ErrorHandler{

   private final List<SAXParseException> fatalErrors = new java.util.ArrayList<SAXParseException>();

   private final List<SAXParseException> errors = new java.util.ArrayList<SAXParseException>();

   private final List<SAXParseException> warnings = new java.util.ArrayList<SAXParseException>();

   public List<SAXParseException> getFatalErrors(){
      return this.fatalErrors;
   }

   public List<SAXParseException> getErrors(){
      return this.errors;
   }

   public List<SAXParseException> getWarnings(){
      return this.warnings;
   }

   public boolean hasError(){
      return !(this.fatalErrors.isEmpty() && this.errors.isEmpty());
   }


   @Override
   public void warning(SAXParseException ex){
      this.warnings.add(ex);
   }

   @Override
   public void error(SAXParseException ex){
      this.errors.add(ex);
   }

   @Override
   public void fatalError(SAXParseException ex){
      this.errors.add(ex);
   }

   public void printErrors(PrintStream ps){
      if(ps == null) return;

      ps.println(">> Fatal Errors : " + this.fatalErrors.size());
      for(SAXParseException ex: this.fatalErrors){
         this.printSAXParseException(ps, ex);
      }
      ps.println("");

      ps.println(">> Errors : " + this.errors.size());
      for(SAXParseException ex: this.errors){
         this.printSAXParseException(ps, ex);
      }
      ps.println("");

      ps.println(">> Warnings : " + this.warnings.size());
      for(SAXParseException ex: this.warnings){
         this.printSAXParseException(ps, ex);
      }
      ps.println("");
   }

   private void printSAXParseException(PrintStream ps, SAXParseException ex){

      ps.println("Line : " + ex.getLineNumber()
            + ", Column : " + ex.getColumnNumber()
            + " ; " + ex.getMessage());
   }
   
   
   /**
    * Gets the {@code XmlValidationResult} object of current state.
    * The method doesn't cache anything and build the result object 
    * at each request. 
    * 
    * @return
    */
   public XmlErrorBundle getErrorBundle(){
   	XmlErrorBundle result = new XmlErrorBundle();
   	
   	XmlErrorBundle.Item item = null;
   	for(SAXParseException ex : this.fatalErrors){
   	   item = new Item().setType(ItemType.FATAL)
   	         .setLine(ex.getLineNumber()).setColumn(ex.getColumnNumber())
   	         .setTitle(ex.getMessage()).setDesc(ex.getMessage());
   	   result.addItem(item);
   	}
   	
      for(SAXParseException ex : this.errors){
         item = new Item().setType(ItemType.ERROR)
               .setLine(ex.getLineNumber()).setColumn(ex.getColumnNumber())
               .setTitle(ex.getMessage()).setDesc(ex.getMessage());
         result.addItem(item);
      }
   	
      for(SAXParseException ex : this.warnings){
         item = new Item().setType(ItemType.WARN)
               .setLine(ex.getLineNumber()).setColumn(ex.getColumnNumber())
               .setTitle(ex.getMessage()).setDesc(ex.getMessage());
         result.addItem(item);
      }
   	
   	return result;
   }


}