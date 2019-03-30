
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;



@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private String		banner;
	private String		targetPage;
	private CreditCard	creditCard;
	private Boolean 	active;
	private double		charge;


	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@URL
	public String getTargetPage() {
		return this.targetPage;
	}
	public void setTargetPage(final String targetPage) {
		this.targetPage = targetPage;
	}

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	@NotNull
	public Boolean getActive() {
		return this.active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}

	@NotNull
	@DecimalMin("0.0")
	public double getCharge() {
		return this.charge;
	}

	public void setCharge(double charge) {
		this.charge = charge;
	}




	// Relationships ----------------------------------------------------------
	private Sponsor 	sponsor;
	private Procession 	procession;
	

	@NotNull
	@ManyToOne(optional=false)
	public Sponsor getSponsor() {
		return this.sponsor;
	}
	
	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
	
	@NotNull
	@ManyToOne(optional=false)
	public Procession getProcession() {
		return this.procession;
	}
	
	public void setProcession(Procession procession) {
		this.procession = procession;
	}
	@Override
	public String toString() {
		return "Sponsorship [getBanner()=" + this.getBanner() + ", getTargetPage()=" + this.getTargetPage() + ", getCreditCard()=" + this.getCreditCard() + "]";
	}

}
