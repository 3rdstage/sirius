/**
 * 
 */
package thirdstage.shop1.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author 3rdstage
 * 
 */
@Embeddable
@org.hibernate.annotations.AccessType("field")
public class Address implements Serializable{

	/**
	 * 우편번호
	 */
	@Column(name = "zip_code")
	@org.hibernate.validator.Length(max = 7)
	private String zipCode;

	/**
	 * 특별시/광역시/도
	 */
	@Column(name = "province")
	@org.hibernate.validator.Length(max = 30)
	private String province;

	/**
	 * 시/군/구
	 */
	@Column(name = "city")
	@org.hibernate.validator.Length(max = 30)
	private String city;

	/**
	 * 읍/면/동 및 집합건물(아파트 등)
	 */
	@Column(name = "village")
	@org.hibernate.validator.Length(max = 80)
	private String village;

	/*
	 * IMPORTANT Validator in Embedded Object 
	 * had no effect on schema generation using Hibernate Tools upto 3.2.3.GA.
	 */
	/**
	 * 번지 및 상세주소
	 */
	@Column(name = "street")
	@org.hibernate.validator.Length(max = 80)
	private String street;

}
