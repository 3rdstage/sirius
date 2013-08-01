package thirdstage.dv.widget.meta.meta;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import junit.framework.Assert;

import org.iso_relax.verifier.VerifierFactory;
import org.testng.annotations.Test;
import org.xml.sax.InputSource;

import com.thaiopensource.util.PropertyMap;
import com.thaiopensource.validate.Schema;
import com.thaiopensource.validate.SchemaReader;
import com.thaiopensource.validate.rng.CompactSchemaReader;

public class CompactSchemaTest {

	private URL url = ClassLoader.getSystemResource("thirdstage/dv/widget/meta/meta/widget-meta.rnc");

	
	@Test
	public void testCompactSchemaLoadUsingJaxp(){
		
		try{
			System.setProperty("javax.xml.validation.SchemaFactory:" + XMLConstants.RELAXNG_NS_URI, "com.thaiopensource.relaxng.jaxp.CompactSyntaxSchemaFactory");
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.RELAXNG_NS_URI);
			javax.xml.validation.Schema schema = factory.newSchema(new StreamSource(new java.io.File(url.toURI())));
			
			Assert.assertNotNull(schema);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Test
	public void testCompactSchemaLoad(){

		try{
			SchemaReader csr = CompactSchemaReader.getInstance();
			
			InputStream is = new FileInputStream(new java.io.File(url.toURI()));
			InputSource isrc = new InputSource(is);

			Schema sch = csr.createSchema(isrc, PropertyMap.EMPTY);
		}
		catch(Exception ex){
			ex.printStackTrace(System.err);
			Assert.fail();
		}
	}
	
	@Test
	public void testCompactSchemaUsingJarv1() throws Exception{
		
		VerifierFactory factory = new com.sun.msv.verifier.jarv.TheFactoryImpl();
		
		org.iso_relax.verifier.Schema sch = factory.compileSchema(new java.io.File(url.toURI()));
	}
	
	@Test
	public void testCompactSchemaUsingJarv2() throws Exception{
		
		VerifierFactory factory = new jp.gr.xml.relax.swift.SwiftVerifierFactory();
		
		org.iso_relax.verifier.Schema sch = factory.compileSchema(new java.io.File(url.toURI()));
	}
}
