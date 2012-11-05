/**
 * 
 */
package thirdstage.shop1.entities;

import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author 3rdstage
 * 
 */
@Entity
@Table(name="sm_customer")
@org.hibernate.annotations.ForeignKey(name = "fk_customer_1")
public class Customer extends Account{

	@Basic
	@Column(name = "point")
	@org.hibernate.validator.Min(0)
	private Long point;

	// Not working
	// @OneToMany
	// @JoinTable(name = "sm_customer_address", joinColumns = @JoinColumn(name="customer_id"))
	// private Set<Address> addresses;

}
