package thirdstage.sirius.catalog;

import thirdstage.sirius.data.value.BaseValue;

public class Category extends BaseValue<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2416977046075803783L;

	private String parentId = null;
	
	private int order = -1;
	
	private String desc = null;
	
	public Category(String id, String name, String parentId, int order, String desc){
		super(id, name);
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
}
