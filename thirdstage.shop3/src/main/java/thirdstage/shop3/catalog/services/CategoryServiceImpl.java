package thirdstage.shop3.catalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import thirdstage.shop3.catalog.daos.CategoryDao;
import thirdstage.shop3.catalog.values.Category;

public class CategoryServiceImpl implements CategoryService {
	
	private CategoryDao dao;

	public CategoryDao getCategoryDao() {
		return dao;
	}

	@Required
	public void setCateogryDao(CategoryDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Category> findAllCategories() {
		return dao.findAllCategories();
	}

	@Override
	public Category findCategory(String id) {
		return dao.findCategory(id);
	}

	@Override
	public List<Category> findSubcategories(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCategory(Category cat) {
		// TODO Auto-generated method stub

	}

}
