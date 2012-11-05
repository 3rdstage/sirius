package thirdstage.sirius.support.mybatis;

import javax.annotation.Resource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Required;


/**
 * <p>
 * This class expect that SqlSessionFactory object to be exposed
 * is defined as a bean with name of 'sqlSessionFactory'.<br/>
 * If you want other name for SqlSessionFactory object,
 * you can explicitly set <code>sqlSessionFactory</code> property when
 * defining the bean for this class.
 * </p>
 * 
 * @author Sangmoon Oh
 * @version 0.3, Sangmoon Oh, 2011-05-20 
 * */
public class DefaultMyBatisConfigMXBean implements MyBatisConfigMXBean {

	private SqlSessionFactory sqlSessionFactory;
	
	private Configuration sqlSessionConfig;

	@Required
	public void setSqlSessionFactory(SqlSessionFactory factory){
		if(factory == null) throw new IllegalArgumentException("factory should not null.");
		
		this.sqlSessionFactory = factory;
		this.sqlSessionConfig = factory.getConfiguration();
	}
	
	protected Configuration getSqlSessionConfig(){
		return this.sqlSessionConfig;
	}
	
	
	public String getDefaultExecutorType() {
		
		return this.getSqlSessionConfig().getDefaultExecutorType().toString();
	}

	public int getDefaultStatementTimeout() {
		return this.getSqlSessionConfig().getDefaultStatementTimeout();
	}

}
