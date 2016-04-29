package thirdstage.shop3.catalog.daos;

import thirdstage.shop3.catalog.values.Product;

public interface ProductDao {
	
	Product findProduct(String id);
	

}
