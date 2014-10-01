package thirdstage.sirius.core.caching;

import java.util.List;

public interface GenericCashingDao<T> extends CachingDao {
	
	
	T getData(String... keys);
	
	List<T> getData();
	
	void reset();
	
	void setQuery(String query);
	
	String getQuery();
	
	boolean isUsingExternalCache();
	
	String getKeyInExternalCache();
}
