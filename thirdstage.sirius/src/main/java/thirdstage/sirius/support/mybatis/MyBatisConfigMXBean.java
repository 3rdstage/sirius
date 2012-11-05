package thirdstage.sirius.support.mybatis;

import javax.management.MXBean;

/**
 * @author Sangmoon Oh
 * @version 0.3, Sangmoon Oh, 2011-05-20
 */
@MXBean(true)
public interface MyBatisConfigMXBean {

	
	/**
	 * @return
	 * @since 0.3
	 * @see org.apache.ibatis.session.Configuration#getDefaultExecutorType
	 */
	String getDefaultExecutorType();
	
	/**
	 * @return
	 * @since 0.3
	 * @see org.apache.ibatis.session.Configuration#getDefaultStatementTimeout
	 */
	int getDefaultStatementTimeout();
	
 	
}
