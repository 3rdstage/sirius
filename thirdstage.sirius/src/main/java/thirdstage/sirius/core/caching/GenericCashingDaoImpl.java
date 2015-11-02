package thirdstage.sirius.core.caching;

import java.util.List;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Required;

public class GenericCashingDaoImpl<T> implements GenericCashingDao<T> {
	
	private DataSource dataSource;
	
	protected DataSource getDataSource(){
		return this.dataSource;
	}
	
	@Required
	public void setDataSource(DataSource ds){
		this.dataSource = ds;
	}
	

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T getData(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQuery(String query) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsingExternalCache() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getKeyInExternalCache() {
		// TODO Auto-generated method stub
		return null;
	}

}
