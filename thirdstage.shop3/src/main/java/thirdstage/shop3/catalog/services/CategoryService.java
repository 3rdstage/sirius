package thirdstage.shop3.catalog.services;

import java.util.List;

import thirdstage.shop3.catalog.values.Category;

public interface CategoryService {
	
	List<Category> findAllCategories();
	
	Category findCategory(String id);
	
	List<Category> findSubcategories(String id);
	
	void addCategory(Category cat);
	

}
