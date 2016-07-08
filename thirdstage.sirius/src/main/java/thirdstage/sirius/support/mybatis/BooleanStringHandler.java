package thirdstage.sirius.support.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.LoggerFactory;

/**
 * Maps automatically {@code boolean} type property in Java object and string type column in database.
 * <p>
 * The column handled by this class is expected to be {@code CHAR} or {@code VACHAR} datatype.
 * <p>
 * The mapping for {@code NULL} value in database can be configured in constructor.
 *
 * @author Sangmoon Oh
 * @since 2016-07-05
 * @see <code>https://github.com/mybatis/mybatis-3/blob/master/src/main/java/org/apache/ibatis/type/BooleanTypeHandler.java</code>
 */
@MappedTypes(Boolean.class) @MappedJdbcTypes(JdbcType.CHAR)
public class BooleanStringHandler extends BaseTypeHandler<Boolean>{

   private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

   /**
    * the list strings that represent {@code true}
    */
   private final List<String> trueValues = new ArrayList<String>();

   /**
    * the list strings that represent {@code false}
    */
   private final List<String> falseValues = new ArrayList<String>();

   /**
    * determines whether {@code NULL} value in database will be mapped to {@code false} or not.
    */
   private final boolean isNullFalse;

   /**
    * At least one string value should be given for each list of {@code true} values and
    * list of {@code false} values.
    * <p>
    * If more than two values are specified, the first value in either list will be used
    * when inserting or updating the database column.
    *
    * @param trueValues
    * @param falseValues
    */
   public BooleanStringHandler(@NotEmpty String[] trueValues, @NotEmpty String[] falseValues, boolean isNullFalse){
      Validate.isTrue(!ArrayUtils.isEmpty(trueValues),
         "At least one string for true should included.");
      Validate.isTrue(!ArrayUtils.isEmpty(falseValues),
         "At least one string for false should included.");

      for(int i = 0, n = trueValues.length; i < n; i++){ this.trueValues.add(trueValues[i]); }
      for(int i = 0, n = falseValues.length; i < n; i++){ this.falseValues.add(falseValues[i]); }
      this.isNullFalse = isNullFalse;
   }

   @Override
   public void setNonNullParameter(PreparedStatement ps, int i,
      Boolean parameter, JdbcType jdbcType) throws SQLException{

      if(parameter){ ps.setString(i, this.trueValues.get(0)); }
      else{ ps.setString(i, this.falseValues.get(0)); }
   }

   @Override
   public Boolean getNullableResult(ResultSet rs, String columnName)
      throws SQLException{

      String value = rs.getString(columnName);
      if(value == null){ return !isNullFalse; }
      else{ return this.getBooleanFromStringValue(value); }
   }

   @Override
   public Boolean getNullableResult(ResultSet rs, int columnIndex)
      throws SQLException{

      String value = rs.getString(columnIndex);
      if(value == null){ return !isNullFalse; }
      else{ return this.getBooleanFromStringValue(value); }
   }

   @Override
   public Boolean getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException{

      String value = cs.getString(columnIndex);
      if(value == null){ return !isNullFalse; }
      else{ return this.getBooleanFromStringValue(value); }
   }

   @Nonnull
   private Boolean getBooleanFromStringValue(String value){
      if(this.trueValues.contains(value)){ return Boolean.TRUE; }
      else if(this.falseValues.contains(value)){ return Boolean.FALSE; }
      else{
         this.logger.error("The value('{}') in database is not among expected values.", value);
         throw new IllegalStateException("The value in database is not among expected values.");
      }
   }

}
