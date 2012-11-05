package thirdstage.shop3.catalog.daos;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Required;

import thirdstage.shop3.catalog.values.Category;

public class CategoryDaoImpl implements CategoryDao {
	
	private SqlSession sqlSession;
	
	@Required
	@Resource(name="sqlSession")
	public void setSqlSession(SqlSession sqlSess){
		this.sqlSession = sqlSess;
	}
	
	protected SqlSession getSqlSession(){
		return this.sqlSession;
	}
	

	@Override 
	public List<Category> findAllCategories() {
		
		return this.getSqlSession().selectList("thirdstage.shop3.catalog.Category.findAllCategories");
	}

	@Override
	public Category findCategory(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findSubcategories(String parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCategory(Category cat) {
		// TODO Auto-generated method stub

	}

}
