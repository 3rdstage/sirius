package thirdstage.dv.widget.meta;

import java.util.Arrays;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * @author 3rdstage
 * @version 0.8, 2013-08-09, initial
 */
@Immutable
public class AttributeMeta<T> {

	// TODO(Sangmoon Oh, 2013-08-09)
	// . Add options, default, format
	// . Consider Range class of Apache Commons or Guava

	private String name;

	private AttributeType type;

	private Boolean required;
	
	private String title;

	private String description;

	private Double min;

	private Boolean includesMin;

	private Double max;

	private Boolean includesMax;

	private Integer minLength;

	private Integer maxLength;
	
	private String pattern;

	private String format;
	
	private T[] enums;
	
	private T defaultValue;

	/**
	 * field for toString method.
	 */
	private volatile transient String stringValue;
	
	protected AttributeMeta(String name, AttributeType type, Boolean required, String title,
			String desc, Double min, Boolean includesMin, Double max,
			Boolean includesMax, Integer minLen, Integer maxLen, 
			T[] enums, String format, String pattern, T defaultValue) {
		super();
		this.name = name;
		this.type = type;
		this.required = required;
		this.title = title;
		this.description = desc;
		this.min = min;
		this.includesMin = includesMin;
		this.max = max;
		this.includesMax = includesMax;
		this.minLength = minLen;
		this.maxLength = maxLen;
		this.enums = enums;
		this.format = format;
		this.pattern = pattern;
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the name
	 */
	@Nonnull
	public String getName() { return name; }

	/**
	 * @return the type
	 */
	@Nonnull
	public AttributeType getType() { return type; }

	/**
	 * @return the required
	 */
	public Boolean getRequired() { return required;	}

	/**
	 * @return the title
	 */
	@Nullable
	public String getTitle() { return title; }

	/**
	 * @return the description
	 */
	@Nullable
	public String getDescription() { return description; }

	/**
	 * @return the min
	 */
	@Nullable
	public Double getMin() { return min; }

	/**
	 * @return the includesMin
	 */
	public Boolean getIncludesMin() { return includesMin; }

	/**
	 * @return the max
	 */
	@Nullable
	public Double getMax() { return max; }

	/**
	 * @return the includesMax
	 */
	public Boolean getIncludesMax() { return includesMax; }

	/**
	 * @return the minLength
	 */
	@Nullable
	public Integer getMinLength() { return minLength; }

	/**
	 * @return the maxLength
	 */
	@Nullable
	public Integer getMaxLength() { return maxLength; }

	@Nullable
	public T[] getEnums(){ return this.enums; }
	
	/**
	 * @return the format
	 */
	@Nullable
	public String getFormat() { return format; }
	
	@Nullable
	public String getPattern(){ return this.pattern; }

	@Nullable
	public T getDefault(){ return this.defaultValue; }
	
	private final Object lock = new Object();
	
	@Override
	public String toString() {
		
		String value = this.stringValue;
		if(stringValue == null){
			synchronized(lock){
				value = this.stringValue;
				if(value == null){
					StringBuilder sb = new StringBuilder().append("{")
							.append("name : ").append(this.getName())
							.append(", type : ").append(this.getType());
					
					if(this.getMin() != null){
						sb.append(", min : ").append(this.getMin());
						if(!this.getIncludesMin()) sb.append("(exclusive)");
					}
					if(this.getMax() != null){
						sb.append(", max : ").append(this.getMax());
						if(!this.getIncludesMax()) sb.append("(exclusive)");
					}
					if(this.getMinLength() != null)	sb.append(", min-length : ").append(this.getMinLength());
					if(this.getMaxLength() != null)	sb.append(", max-length : ").append(this.getMaxLength());
					if(this.getEnums() != null) sb.append(", enums : ").append(Arrays.toString(this.getEnums()));
					if(this.getFormat() != null) sb.append(", format : ").append(this.getFormat());
					if(this.getPattern() != null) sb.append(", pattern : ").append(this.getPattern());
					if(this.getDefault() != null) sb.append(", defalut : ").append(this.getDefault());
					sb.append("}");
					value = this.stringValue = sb.toString();
				}
			}
		}
		return value;
	}
}
