package thirdstage.shop3.catalog.values;

public class Category implements java.io.Serializable{
	
	private final static long serialVersionUID = 31L;
	
	private String id;
	
	private String name;
	
	private String parentId;
	
	private int displayOrder;
	
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString(){
		return String.format("ID : %1$s, Name : %2$s", this.id, this.name);
	}

}
