package thirdstage.sirius.support.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * Maps automatically {@code boolean} type property in Java object and 'Y' or 'N' value in database column.
 * <p>
 * 'Y' or 'N' value in database will be converted to {@code true} or {@code false} value in Java
 * object and vice versa.
 * <p>
 * {@code NULL} value in database will be converted to {@code false}.
 *
 * @author Sangmoon Oh
 * @since 2016-07-06
 */
@MappedTypes(Boolean.class) @MappedJdbcTypes(JdbcType.CHAR)
public class BooleanYnHandler extends BooleanStringHandler{

   public BooleanYnHandler(){
      super(new String[]{"Y"}, new String[]{"N"}, true);
   }
}
