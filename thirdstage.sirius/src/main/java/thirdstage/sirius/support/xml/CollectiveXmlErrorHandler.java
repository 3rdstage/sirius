package thirdstage.sirius.support.xml;

import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class CollectiveXmlErrorHandler extends DefaultHandler{

   private List<SAXParseException> fatalErrors = new java.util.ArrayList<SAXParseException>();

   private List<SAXParseException> errors = new java.util.ArrayList<SAXParseException>();

   private List<SAXParseException> warnings = new java.util.ArrayList<SAXParseException>();

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


}