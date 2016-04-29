/**
 * 
 */
package thirdstage.shop1.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author 3rdstage
 * 
 */
@Entity
@Table(name = "sm_menu")
@SequenceGenerator(name = "menuSequence", sequenceName = "seq_menu", initialValue = 10000)
@org.hibernate.annotations.AccessType("field")
@org.hibernate.annotations.Entity(dynamicInsert = false, dynamicUpdate = false)
@org.hibernate.annotations.Table(appliesTo = "sm_menu", comment = "메뉴")
public class Menu implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "menuSequence")
	@Column(name = "id", precision = 12, scale = 0)
	@org.hibernate.validator.Length(max = 12)
	private Long id;

	@Basic
	@Column(name = "name", unique = false)
	@org.hibernate.validator.Length(max = 160)
	@org.hibernate.validator.NotNull
	private String name;

	@ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	@org.hibernate.annotations.ForeignKey(name = "fk_menu_1")
	private Menu parentMenu;

	@Basic
	@Column(name = "display_order")
	@org.hibernate.validator.Length(max = 6)
	private int order;

	@Basic
	@Column(name = "descn")
	@org.hibernate.validator.Length(max = 2000)
	private String description;
}
