package thirdstage.sirius.catalog;

import thirdstage.sirius.data.value.BaseValue;

public class Item<C extends Category, T extends ItemType<C>> extends BaseValue<String> {
	
	private String typeId = null;
	
	private T type = null;
	
	private String categoryId = null;

	private C category = null;
	
	
	public Item(String id, String name, String typeId, String categoryId){
		super(id, name);
		this.typeId = typeId;
		this.categoryId = categoryId;
	}

	protected T getType(){ return this.type; }
	
	protected C getCategory(){ return this.category; }
}
