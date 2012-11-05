package thirdstage.shop3.catalog.daos;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import thirdstage.shop3.catalog.values.Category;

@ContextConfiguration("/thirdstage/shop3/confs/spring.xml")
public class CategoryDaoImplTest extends AbstractTestNGSpringContextTests{

	@Resource(name="categoryDao")
	private CategoryDao dao;


	@Test
	public void findAllCategories() {
		
		List<Category> categories = this.dao.findAllCategories();
		
		for(Category category : categories){
			System.out.println(category.toString());
		}
		
		
		
	}
}
