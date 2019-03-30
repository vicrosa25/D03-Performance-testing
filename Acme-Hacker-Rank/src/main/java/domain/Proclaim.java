package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Proclaim extends DomainEntity {
	
	// Attributes
	private Date 	moment;
	private String 	text;
	
	
	// Getters and Setters
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	
	@NotBlank
	@Size(min=1, max=251)
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	
	// ToString
	@Override
	public String toString() {
		return "Proclaim [moment=" + this.moment + ", text=" + this.text + "]";
	}
}
