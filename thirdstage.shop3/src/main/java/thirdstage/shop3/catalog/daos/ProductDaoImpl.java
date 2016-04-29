/**
 * 
 */
package thirdstage.shop3.catalog.daos;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Required;

import thirdstage.shop3.catalog.values.Product;
import thirdstage.sirius.support.mybatis.MyBatisXmlMapper;

/**
 * @author 3rdstage
 *
 */
@MyBatisXmlMapper(locations={"thirdstage/shop3/catalog/daos/sqlmaps/Product.mapper.xml"})
public class ProductDaoImpl implements ProductDao {

	@Resource(name="sqlSession")
	public SqlSession sqlSession;
	

	public void setSqlSession(SqlSession sqlSess){
		this.sqlSession = sqlSess;
	}
	
	public SqlSession getSqlSession(){
		return this.sqlSession;
	}
	
	
	/* (non-Javadoc)
	 * @see thirdstage.shop3.catalog.daos.ProductDao#findProduct(java.lang.String)
	 */
	@Override
	public Product findProduct(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
