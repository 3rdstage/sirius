package thirdstage.sirius.logging;

public interface FrameworkLogger {
	
	void logSQL(String sql, Object[] params);
	
	void logException(Throwable th);
	
	
	

}
