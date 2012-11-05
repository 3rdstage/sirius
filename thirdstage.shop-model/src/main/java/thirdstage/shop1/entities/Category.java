/**
 * 
 */
package thirdstage.shop1.entities;

import java.io.Serializable;
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
@Table(name = "sm_category")
@SequenceGenerator(name = "categorySequence", sequenceName = "seq_category", initialValue = 100)
@org.hibernate.annotations.AccessType("field")
@org.hibernate.annotations.Entity(dynamicInsert = false, dynamicUpdate = false)
@org.hibernate.annotations.Table(appliesTo = "sm_category", comment = "상품분류")
public class Category implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "categorySequence")
	@Column(name = "id")
	private Long id;

	@Basic
	@Column(name = "name", unique = true)
	@org.hibernate.annotations.Index(name = "ix_category_1")
	@org.hibernate.validator.Length(max = 80)
	@org.hibernate.validator.NotNull
	private String name;

	@ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	@org.hibernate.annotations.ForeignKey(name = "fk_category_1")
	@org.hibernate.annotations.Index(name = "ix_category_2")
	private Category parentCategory;

	/**
	 * The order among sibling categories
	 */
	@Basic
	@Column(name = "seq", precision = 5, scale = 0)
	@org.hibernate.validator.Length(max = 5)
	@org.hibernate.validator.Max(99999)
	private int sequence;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "descn")
	@org.hibernate.validator.Length(max = 1000)
	private String description;

}
