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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author 3rdstage
 * 
 */
@Entity
@Table(name = "sm_product")
@SequenceGenerator(name = "productSequence", sequenceName = "seq_product", initialValue = 1000000)
@org.hibernate.annotations.AccessType("field")
@org.hibernate.annotations.Entity(dynamicInsert = false, dynamicUpdate = false)
@org.hibernate.annotations.Table(appliesTo = "sm_product", comment = "상품")
public class Product implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "productSequence")
	@Column(name = "id", precision = 8, scale = 0)
	private Long id;

	@Basic
	@Column(name = "name")
	@org.hibernate.annotations.Index(name = "ix_product_name")
	@org.hibernate.validator.Length(max = 80)
	@org.hibernate.validator.NotNull
	private String name;

	@ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	@org.hibernate.annotations.ForeignKey(name = "fk_product_1")
	@org.hibernate.validator.NotNull
	private Category category;

	@Basic
	@Column(name = "short_descn")
	@org.hibernate.validator.Length(max = 1000)
	private String shortDescription;

	@Lob
	@Column(name = "full_descn")
	@org.hibernate.validator.Length(max = 8000)
	private String fullDescription;

}
