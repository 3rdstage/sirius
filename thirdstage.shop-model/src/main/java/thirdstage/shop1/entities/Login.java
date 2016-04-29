/**
 * 
 */
package thirdstage.shop1.entities;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author 3rdstage
 * 
 */
@Embeddable
public class Login implements Serializable{

	@org.hibernate.annotations.Parent
	private User user;

	@Basic
	@Column(name = "seq", precision = 6, scale = 0)
	@org.hibernate.validator.Length(max = 6)
	private Integer sequence;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "login_datetime")
	private Calendar loginDatetime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logout_datetime")
	private Calendar logoutDatetime;

}
