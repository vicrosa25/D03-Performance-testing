
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import domain.Procession;

public class PathForm {

	private Double		originLatitude;
	private Double		destinationLatitude;
	private Double		originLongitude;
	private Double		destinationLongitude;
	private Date		originTime;
	private Date		destinationTime;
	private Procession	procession;

	// Getters and Setters
	
	public Double getOriginLatitude() {
		return this.originLatitude;
	}
	public void setOriginLatitude(final Double originLatitude) {
		this.originLatitude = originLatitude;
	}
	
	public Double getDestinationLatitude() {
		return this.destinationLatitude;
	}
	public void setDestinationLatitude(final Double destinationLatitude) {
		this.destinationLatitude = destinationLatitude;
	}
	
	public Double getOriginLongitude() {
		return this.originLongitude;
	}
	public void setOriginLongitude(final Double originLongitude) {
		this.originLongitude = originLongitude;
	}
	
	public Double getDestinationLongitude() {
		return this.destinationLongitude;
	}
	public void setDestinationLongitude(final Double destinationLongitude) {
		this.destinationLongitude = destinationLongitude;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getOriginTime() {
		return this.originTime;
	}
	public void setOriginTime(final Date originTime) {
		this.originTime = originTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDestinationTime() {
		return this.destinationTime;
	}
	public void setDestinationTime(final Date destinationTime) {
		this.destinationTime = destinationTime;
	}

	public Procession getProcession() {
		return this.procession;
	}

	public void setProcession(final Procession procession) {
		this.procession = procession;
	}
}