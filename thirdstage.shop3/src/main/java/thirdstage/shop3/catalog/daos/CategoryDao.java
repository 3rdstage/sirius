package thirdstage.shop3.catalog.daos;

import java.util.List;

import thirdstage.shop3.catalog.values.Category;

public interface CategoryDao {

	List<Category> findAllCategories();
	
	Category findCategory(String id);
	
	List<Category> findSubcategories(String id);
	
	void addCategory(Category cat);
		
	
}
