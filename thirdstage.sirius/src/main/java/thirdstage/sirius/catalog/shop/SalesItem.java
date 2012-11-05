package thirdstage.sirius.catalog.shop;

import thirdstage.sirius.catalog.Item;

public class SalesItem extends Item<Category, Product> {
	
	public SalesItem(String id, String name, String productId, String categoryId){
		super(id, name, productId, categoryId);
	}
	
	public Category getCategory(){
		return super.getCategory();
	}
	
	public Product getProduct(){
		return super.getType();
	}

}
