package thirdstage.sirius.data.tree;

import thirdstage.sirius.data.value.AbstractIdentifiableValue;

public class CategoryValue extends AbstractIdentifiableValue<String> implements Cloneable, Comparable<CategoryValue>{

	private String name;
	
	private String parentId;
	
	private int order;
	
	private String desc;
	
	public CategoryValue(String id, String name, String parentId, int order, String desc){
		super(id);
		
		this.name = name;
		this.parentId = parentId;
		this.order = order;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public String getParentId() {
		return parentId;
	}

	public int getOrder() {
		return order;
	}

	public String getDesc() {
		return desc;
	}


	@Override
	public String toString(){
		return this.name;
	}
	
	@Override
	public CategoryValue clone(){
		
		try{
			return (CategoryValue) super.clone();
		}catch(CloneNotSupportedException ex){
			throw new AssertionError();
		}
		
		//@todo Why the following simple creation of copy object using constructor is not recommaned?
		//Check again the chapter on clone in the book of "Effective Java"
		//return new CategoryValue(this.getId(), this.name, this.parentId, this.order, this.desc);
	}

	/**
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(CategoryValue value) {
		
		if(value == null) return -1;
		else{
			return this.getOrder() - value.getOrder();
		}
	}
	
}