
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Double 	originLatitude;
	private Double 	destinationLongitude;
	private Double 	destinationLatitude;
	private Double 	originLongitude;
	private Date	originTime;
	private Date	destinationTime;
	private int		number;


	@DecimalMin("-90.0")
	@DecimalMax("90.0")
	@NotNull
	public Double getOriginLatitude() {
		return this.originLatitude;
	}

	public void setOriginLatitude(final Double originLatitude) {
		this.originLatitude = originLatitude;
	}
	
	@DecimalMin("-180.0")
	@DecimalMax("180.0")
	@NotNull
	public Double getOriginLongitude() {
		return this.originLongitude;
	}
	public void setOriginLongitude(Double originLongitude) {
		this.originLongitude = originLongitude;
	}


	@DecimalMin("-90.0")
	@DecimalMax("90.0")
	@NotNull
	public Double getDestinationLatitude() {
		return this.destinationLatitude;
	}

	public void setDestinationLatitude(final Double destinationLatitude) {
		this.destinationLatitude = destinationLatitude;
	}
	
	@DecimalMin("-180.0")
	@DecimalMax("180.0")
	@NotNull
	public Double getDestinationLongitude() {
		return this.destinationLongitude;
	}
	public void setDestinationLongitude(Double destinationLongitude) {
		this.destinationLongitude = destinationLongitude;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getOriginTime() {
		return this.originTime;
	}
	public void setOriginTime(final Date originTime) {
		this.originTime = originTime;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDestinationTime() {
		return this.destinationTime;
	}

	public void setDestinationTime(final Date destinationTime) {
		this.destinationTime = destinationTime;
	}

	@NotNull
	@Min(0)
	public int getNumber() {
		return this.number;
	}
	public void setNumber(int number) {
		this.number = number;
	}


	// Relationships ----------------------------------------------------------
	private Path	path;


	@NotNull
	@ManyToOne(optional = false)
	public Path getPath() {
		return this.path;
	}

	public void setPath(final Path path) {
		this.path = path;
	}
}
