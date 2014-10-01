package thirdstage.sirius.catalog;

import java.util.List;

public interface Catalog<C extends Category, T extends ItemType<C>, I extends Item<C, T>>{
	
	CategoryTree<C> getCategoryTree();
	
	C findCategory(String categoryId);
	
	List<C> findCategoriesByNamePattern(String namePattern);
	
	List<T> findAllItemTypes();
	
	T findItemType(String typeId);
	
	List<T> findItemTypesByCategory(C category);
	
	List<I> findAllItems();
	
	I findItem(String itemId);
	
	List<I> findItemsByCategory(C category);
	
	List<I> findItemsByNamePattern(String namePattern);

}
