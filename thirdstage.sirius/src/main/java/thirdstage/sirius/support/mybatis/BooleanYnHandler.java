package thirdstage.sirius.support.mybatis;

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
public class BooleanYnHandler extends BooleanStringHandler{

   public BooleanYnHandler(){
      super(new String[]{"Y"}, new String[]{"N"}, true);
   }
}
