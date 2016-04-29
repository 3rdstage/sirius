/**
 * 
 */
package thirdstage.shop1.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * @author 3rdstage
 * 
 */
@Entity
@Table(name = "sm_user")
@org.hibernate.annotations.AccessType("field")
@org.hibernate.annotations.Table(appliesTo = "sm_user", comment = "사용자")
public class User implements Serializable{

	@Id
	@Column(name = "login_id")
	@org.hibernate.validator.Length(max = 12)
	private String loginId;

	@Basic
	@Column(name = "passwd")
	@org.hibernate.validator.Length(min = 8, max = 12)
	private String password;

	@Basic
	@Column(name = "is_locked")
	private boolean isLocked;

	@Basic
	@Column(name = "is_passwd_expired")
	private boolean isPasswordExpired;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reg_datetime")
	private Calendar registeredDatetime;

	@org.hibernate.annotations.CollectionOfElements
	@JoinTable(name = "sm_login", joinColumns = @JoinColumn(name = "login_id"))
	@org.hibernate.annotations.IndexColumn(name = "seq", base = 1)
	@org.hibernate.annotations.ForeignKey(name = "fk_login_1")
	private List<Login> loginHistory;

}
