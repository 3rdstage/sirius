/**
 * 
 */
package thirdstage.shop1.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 3rdstage
 * 
 */
@Entity
@Table(name="sm_supplier")
@org.hibernate.annotations.ForeignKey(name = "fk_supplier_1")
public class Supplier extends Account{

}
