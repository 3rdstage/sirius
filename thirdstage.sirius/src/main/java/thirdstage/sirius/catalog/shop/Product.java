package thirdstage.sirius.catalog.shop;

import thirdstage.sirius.catalog.ItemType;

public class Product extends ItemType<Category> {
	
	public Product(String id, String name, String categoryId, String desc){
		super(id, name, categoryId, desc);
	}
	
	public Category getCategory(){
		return super.getCategory();
	}

}
