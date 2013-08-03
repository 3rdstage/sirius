package thirdstage.dv.widget.meta;

public class AttributeMeta {
	
	private String name;
	
	private AttributeType type;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AttributeType getType() {
		return type;
	}

	public void setType(AttributeType type) {
		this.type = type;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder().append("{")
			.append("name : ").append(this.getName())
			.append(", type : ").append(this.getType())
			.append("}");
		
		return sb.toString();
	}
	

}
