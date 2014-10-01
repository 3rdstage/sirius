package thirdstage.sirius.catalog;

import java.util.HashSet;
import java.util.Set;

import thirdstage.sirius.data.value.BaseValue;

public class ItemType<C extends Category> extends BaseValue<String>{
	
	private String categoryId = null;
	
	private C category = null;
	
	private Set<C> categories = new HashSet<C>();
	
	private String desc = null;
	
	private Catalog<C, ?, ?> catalog = null;
	
	public ItemType(String id, String name, String categoryId, String desc){
		super(id, name);
		this.desc = desc;
		this.categoryId = categoryId;
	}
	
	protected C getCategory(){
		//@todo double-checked locking is necessary
		if(this.category == null){
			this.category = this.catalog.findCategory(this.categoryId);
		}
		
		return this.category; 
	}
	
	public String getDesc(){ return this.desc; }
	
	//@todo 
	protected void bindCatalg(Catalog<C, ?, ?> catalog){
		this.catalog = catalog;
	}
	
	public void addCategory(C cat){
		this.categories.add(cat);
	}
	
	
}
