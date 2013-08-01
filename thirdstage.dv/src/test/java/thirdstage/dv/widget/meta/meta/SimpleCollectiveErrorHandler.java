/**
 * 
 */
package thirdstage.dv.widget.meta.meta;

import java.io.PrintStream;
import java.util.List;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author 3rdstage
 *
 */
public class SimpleCollectiveErrorHandler extends DefaultHandler{

	private List<SAXParseException> errors = new java.util.ArrayList<SAXParseException>();

	public List<SAXParseException> getErrors(){ return this.errors; }

	public boolean hasError(){ return !this.errors.isEmpty(); }

	@Override public void warning(SAXParseException ex){}
	@Override public void error(SAXParseException ex){ this.errors.add(ex); } 
	@Override public void fatalError(SAXParseException ex){ this.errors.add(ex); }

	public void printErrors(PrintStream ps){
		if(ps == null) return;
		ps.println(">> Errors : " + this.errors.size());
		for(SAXParseException ex: this.errors)    printSAXParseException(ps, ex);
		ps.println("");
	}

	public static void printSAXParseException(PrintStream ps, SAXParseException ex){
		ps.println("Line : " + ex.getLineNumber() + ", Column : " + ex.getColumnNumber() + " ; " + ex.getMessage());
	}
}
