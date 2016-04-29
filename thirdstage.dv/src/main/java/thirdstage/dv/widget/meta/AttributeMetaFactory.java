package thirdstage.dv.widget.meta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Provides singleton
 * 
 * @author 3rdstage
 *
 */
public class AttributeMetaFactory {
	
	private final static AttributeMetaFactory instance = new AttributeMetaFactory();
	
	private AttributeMetaFactory(){
		
	}
	
	public static AttributeMetaFactory getInstance(){
		return instance;
	}
	
	@Nullable
	public AttributeMeta<?> createAttributeMeta(@Nonnull String name, 
			@Nonnull AttributeType type, Boolean required, String title,
			String desc, Double min, Boolean includesMin, Double max,
			Boolean includesMax, Integer minLen, Integer maxLen, 
			Object[] enums, String format, String pattern, Object defaultValue){
		
		if(name == null) throw new IllegalArgumentException("The name of attribute shouldn't be null");
		if(type == null) throw new IllegalArgumentException("The type of attriubte shouldn't be null");
		
		AttributeMeta<?> meta = null;
		
		if(type.equals(AttributeType.BOOLEAN)){
			Boolean[] options = null;
			Boolean dflt = null;
			
			try{ options = (Boolean[])enums; }
			catch(ClassCastException ex){
				throw new IllegalArgumentException("The enums(options) of BOOLEAN type attribute should be Boolean type");
			}
			
			try{ dflt = (Boolean)defaultValue; }
			catch(ClassCastException ex){
				throw new IllegalArgumentException("The default value of BOOLEAN type attribute should be Boolean type");
			}
			
			meta = new AttributeMeta<Boolean>(name, AttributeType.BOOLEAN, required, title, desc, 
				min, includesMin, max, includesMax, minLen, maxLen, 
				options, format, pattern, dflt);
		}else if(type.equals(AttributeType.INTEGER)){
			Long[] options = null;
			Long dflt = null;
			
			try{ options = (Long[])enums; }
			catch(ClassCastException ex){
				throw new IllegalArgumentException("The enums(options) of INTEGER type attribute should be Long type");
			}
			
			try{ dflt = (Long)defaultValue; }
			catch(ClassCastException ex){
				throw new IllegalArgumentException("The default value of INTEGER type attribute should be Long type");
			}
			
			meta = new AttributeMeta<Long>(name, AttributeType.INTEGER, required, title, desc, 
				min, includesMin, max, includesMax, minLen, maxLen, 
				options, format, pattern, dflt);
		}else if(type.equals(AttributeType.NUMBER)){
			Double[] options = null;
			Double dflt = null;
			
			try{ options = (Double[])enums; }
			catch(ClassCastException ex){
				throw new IllegalArgumentException("The enums(options) of NUMBER type attribute should be Double type");
			}
			
			try{ dflt = (Double)defaultValue; }
			catch(ClassCastException ex){
				throw new IllegalArgumentException("The default value of NUMBER type attribute should be Double type");
			}
			
			meta = new AttributeMeta<Double>(name, AttributeType.NUMBER, required, title, desc, 
				min, includesMin, max, includesMax, minLen, maxLen, 
				options, format, pattern, dflt);
		}else if(type.equals(AttributeType.STRING)){
			String[] options = null;
			String dflt = null;
			
			try{ options = (String[])enums; }
			catch(ClassCastException ex){
				throw new IllegalArgumentException("The enums(options) of STRING type attribute should be String type");
			}
			
			try{ dflt = (String)defaultValue; }
			catch(ClassCastException ex){
				throw new IllegalArgumentException("The default value of STRING type attribute should be String type");
			}
			
			meta = new AttributeMeta<String>(name, AttributeType.STRING, required, title, desc, 
				min, includesMin, max, includesMax, minLen, maxLen, 
				options, format, pattern, dflt);
		}else{
			meta = null;
		}
		
		
		
		return meta;
	}
			
			
	
	
	

}
