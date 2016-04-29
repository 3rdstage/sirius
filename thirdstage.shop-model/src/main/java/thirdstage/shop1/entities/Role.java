/**
 * 
 */
package thirdstage.shop1.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author 3rdstage
 * 
 */
@Entity
@Table(name = "sm_role")
@SequenceGenerator(name = "roleSequence", sequenceName = "seq_role", initialValue = 100)
@org.hibernate.annotations.AccessType(value = "field")
@org.hibernate.annotations.Entity(dynamicInsert = false, dynamicUpdate = false)
@org.hibernate.annotations.Table(appliesTo = "sm_role", comment = "역할")
public class Role{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "roleSequence")
	@Column(name = "id", length = 4)
	@org.hibernate.validator.Min(0)
	@org.hibernate.validator.Max(9999)
	private Long id;

	@Basic
	@Column(name = "name", unique = true)
	@org.hibernate.validator.Length(max = 80)
	@org.hibernate.validator.NotNull
	private String name;

	@ManyToMany
	@JoinTable(name = "sm_role_menu", 
			joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}, 
			inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id")}, 
			uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "menu_id"}))
	@org.hibernate.annotations.ForeignKey(name = "fk_role_menu_1", inverseName = "fk_role_menu_2")
	private List<Menu> menus = new ArrayList<Menu>();

}
