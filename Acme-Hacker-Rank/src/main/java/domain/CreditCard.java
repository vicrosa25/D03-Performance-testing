
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

// datatype
@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	private String	holderName;
	private String	brandName;
	private String	number;
	private Date	expiration;
	private Integer	cvvCode;


	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}
	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	public String getBrandName() {
		return this.brandName;
	}
	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	@NotBlank
	@CreditCardNumber
	public String getNumber() {
		return this.number;
	}
	public void setNumber(final String number) {
		this.number = number;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public Integer getCvvCode() {
		return this.cvvCode;
	}
	public void setCvvCode(final Integer cvvCode) {
		this.cvvCode = cvvCode;
	}
	@Override
	public String toString() {
		return ("Holder Name: " + this.getHolderName() + "   Brand Name: " + this.getBrandName() + "   Number: " + this.getNumber() + "   Expiration:" + this.getExpiration() + "   CVV Code: " + this.getCvvCode());
	}

	@NotNull
	//@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/YY")
	public Date getExpiration() {
		return this.expiration;
	}
	public void setExpiration(final Date expiration) {
		this.expiration = expiration;
	}
}
