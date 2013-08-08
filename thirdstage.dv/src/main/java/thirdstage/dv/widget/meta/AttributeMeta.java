package thirdstage.dv.widget.meta;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * @author 3rdstage
 * @version 0.8, 2013-08-09, initial
 */
@Immutable
public class AttributeMeta {

	// TODO(Sangmoon Oh, 2013-08-09)
	// . Add options, default, format
	// . Consider Range class of Apache Commons or Guava

	private String name;

	private AttributeType type;

	private String title;

	private String description;

	private Double min;

	private Boolean includesMin;

	private Double max;

	private Boolean includesMax;

	private Integer minLength;

	private Integer maxLength;

	private String format;

	private Boolean required;

	/**
	 * field for toString method.
	 */
	private transient String stringValue;

	public AttributeMeta(String name, AttributeType type, String title,
			String description, Double min, Boolean includesMin, Double max,
			Boolean includesMax, Integer minLength, Integer maxLength,
			String format, Boolean required) {
		super();
		this.name = name;
		this.type = type;
		this.title = title;
		this.description = description;
		this.min = min;
		this.includesMin = includesMin;
		this.max = max;
		this.includesMax = includesMax;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.format = format;
		this.required = required;
	}

	/**
	 * @return the name
	 */
	public String getName() { return name; }

	/**
	 * @return the type
	 */
	public AttributeType getType() { return type; }

	/**
	 * @return the title
	 */
	public String getTitle() { return title; }

	/**
	 * @return the description
	 */
	public String getDescription() { return description; }

	/**
	 * @return the min
	 */
	public Double getMin() { return min; }

	/**
	 * @return the includesMin
	 */
	public Boolean getIncludesMin() { return includesMin; }

	/**
	 * @return the max
	 */
	public Double getMax() { return max; }

	/**
	 * @return the includesMax
	 */
	public Boolean getIncludesMax() { return includesMax; }

	/**
	 * @return the minLength
	 */
	public Integer getMinLength() { return minLength; }

	/**
	 * @return the maxLength
	 */
	public Integer getMaxLength() { return maxLength; }

	/**
	 * @return the format
	 */
	public String getFormat() { return format; }

	/**
	 * @return the required
	 */
	public Boolean getRequired() { return required;	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder().append("{").append("name : ")
				.append(this.getName()).append(", type : ")
				.append(this.getType()).append("}");

		return sb.toString();
	}
}
