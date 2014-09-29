package thirdstage.sirius.support.mybatis;

import java.io.InputStream;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration("/thirdstage/shop3/confs/spring.xml")
public class MyBatisTest extends AbstractTestNGSpringContextTests {

	
	@Resource(name="sqlSessionFactory")
	SqlSessionFactory factory;

	
	@Test
	public void testAddXmlMapper() throws Exception{
		
		Configuration config =  factory.getConfiguration();
		int n1 = config.getMappedStatements().size();
		String resource = "thirdstage/sirius/support/mybatis/Category2.mapper.xml";
		InputStream is = Resources.getResourceAsStream(resource);
		XMLMapperBuilder builder = new XMLMapperBuilder(is, config, resource, config.getSqlFragments());
		builder.parse();
		
		Collection<MappedStatement> stmts = config.getMappedStatements();
		int n2 = stmts.size();
		for(MappedStatement stmt : stmts){
			System.out.printf("ID : %1$s%n%2$s%n", stmt.getId(), stmt.getSqlSource().getBoundSql(null).getSql());
		}
		
		Assert.assertTrue((n2 == n1 + 1) || (n2 == n1 + 2));
	}
	

}
