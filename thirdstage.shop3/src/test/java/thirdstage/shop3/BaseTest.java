package thirdstage.shop3;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarFile;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BaseTest {
	
	private static ApplicationContext springContext = new ClassPathXmlApplicationContext("thirdstage/shop3/confs/spring.xml");

	@Test
	public void testDataSource() {
		
		DataSource ds = springContext.getBean("dataSource", DataSource.class);
		
		Assert.assertNotNull(ds);
		
	}
	
	//@todo 2011-06-01, Why the following test print out 2 statements with same id. 
	@Test
	public void testGetMyBatisMappedStatement(){
		
		SqlSessionFactory factory = springContext.getBean("sqlSessionFactory", SqlSessionFactory.class);
		
		Collection<MappedStatement> stmts = factory.getConfiguration().getMappedStatements();
		
		for(MappedStatement stmt: stmts){
			System.out.printf("%1$s :%n%2$s%n", stmt.getId(), stmt.getBoundSql(null).getSql());
		}
		
		Assert.assertTrue(true);
	}
	
	@Test
	public void listClassesAndJarsInClassPath(){
		
		String classpath = System.getProperty("java.class.path");
		StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator);
		List<String> elements = new ArrayList<String>();
		while(st.hasMoreElements()){
			elements.add(st.nextToken());
		}
		
		List<File> files = new ArrayList<File>();
		
		File f = null;
		for(String element: elements){
			f = new File(element);
			
			try{
				if(f.exists()){
					if(f.isDirectory()){
						files.addAll(FileUtils.listFiles(f, new String[]{"class"}, true));
					}
					else if(element.toLowerCase().endsWith("jar")){
						files.add(f);
					}
					else if(element.toLowerCase().endsWith("zip")){
						files.add(f);
					}
				}		
			}
			catch(Exception ex){}
		}
		
		for(File f2: files){
			System.out.println(f2.getPath());
		}

		Assert.assertTrue(true);
		
	}
}
