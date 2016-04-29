/**
 * 
 */
package thirdstage.shop1.util;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * @author 3rdstage
 *
 */
public class DataNamingStrategy extends ImprovedNamingStrategy{
	
	public final String TABLE_PREFIX_DEFAULT = "sm_";
	public final String COLUMN_PREFIX_DEFAULT = "";
	public final String FOREIGN_KEY_PREFIX_DEFAULT = "fk_";
	public final String INDEX_PREFIX_DEFAULT = "ix_";
	
	protected String tablePrefix = TABLE_PREFIX_DEFAULT;
	protected String columnPrefix = COLUMN_PREFIX_DEFAULT;
	protected String foreignKeyPrefix = FOREIGN_KEY_PREFIX_DEFAULT;
	protected String indexPrefix = INDEX_PREFIX_DEFAULT;
	
	public DataNamingStrategy(){
		
	}

	public DataNamingStrategy(String tablePrefix, String columnPrefix, String foreignKeyPrefix, String indexPrefix){
		this.tablePrefix = tablePrefix;
		this.columnPrefix = columnPrefix;
		this.foreignKeyPrefix = foreignKeyPrefix;
		this.indexPrefix = indexPrefix;
	}

	
	/* 
	 * Return name for table from class name of the entity when no table name is explicitly given.<br/><br/>
	 * The name has underscore as word separator and all words are in lower-case and has prefix specified
	 * in this object.
	 * 
	 * @see org.hibernate.cfg.ImprovedNamingStrategy#classToTableName(java.lang.String)
	 */
	public String classToTableName(String className){
		return this.tablePrefix + super.classToTableName(className);
	}
	
}
