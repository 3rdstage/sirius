/**
 * 
 */
package thirdstage.shop1.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author 3rdstage
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "sm_account")
@SequenceGenerator(name = "accountSequence", sequenceName = "seq_account", initialValue = 10000)
@org.hibernate.annotations.AccessType("field")
@org.hibernate.annotations.Entity(dynamicInsert = false, dynamicUpdate = false)
@org.hibernate.annotations.Table(appliesTo = "sm_account", comment = "Account")
public class Account implements Serializable{

	@Id
	@Column(name = "id", precision = 8, scale = 0)
	private Long id;

	@Basic
	@Column(name = "name")
	@org.hibernate.validator.Length(max = 80)
	@org.hibernate.validator.NotNull
	private String name;

	@Basic
	@Column(name = "type")
	@org.hibernate.validator.Length(max = 20)
	@org.hibernate.validator.NotNull
	private String type;

	@Basic
	@Column(name = "registered_no", unique = true)
	@org.hibernate.validator.Length(max = 13)
	@org.hibernate.validator.NotNull
	private String registeredNumber;

	@Basic
	@Column(name = "email")
	@org.hibernate.validator.Length(max = 80)
	@org.hibernate.validator.Email
	private String email;

	@Basic
	@Column(name = "phone")
	@org.hibernate.validator.Length(max = 20)
	private String phone;

	@Embedded
	private Address address;

}
