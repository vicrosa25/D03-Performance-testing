package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Procession extends DomainEntity {
	
	
	// Attributes -------------------------------------------------------------
	private String 	ticker;
	private String 	title;
	private String 	description;
	private Date 	moment;
	private Boolean draftMode;
	private String 	status;
	private String  reasson;
	
	
	@Pattern(regexp = "^(SUBMITTED|APPROVED|REJECTED)$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
	
	
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^([0-9]{2})(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])(-)([A-Z0-9]{5})$")
	public String getTicker() {
		return this.ticker;
	}
	
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	
	@NotBlank
	public String getTitle() {
		return this.title;
	}
	
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@NotBlank
	public String getDescription() {
		return this.description;
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return this.moment;
	}
	
	
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	
	@NotNull
	public Boolean getDraftMode() {
		return this.draftMode;
	}


	public void setDraftMode(Boolean finalMode) {
		this.draftMode = finalMode;
	}
	
	
	public String getReasson() {
		return this.reasson;
	}

	
	public void setReasson(String reasson) {
		this.reasson = reasson;
	}

	

	// Relationships ----------------------------------------------------------
	private Brotherhood 		brotherhood;
	private Collection<Request> requests;
	private Path				path;


	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}


	public void setBrotherhood(Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}


	@OneToMany(cascade = CascadeType.ALL, mappedBy="procession")
	public Collection<Request> getRequests() {
		return this.requests;
	}


	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}

	@Valid
	@OneToOne(optional = true)
	public Path getPath() {
		return this.path;
	}

	public void setPath(final Path path) {
		this.path = path;
	}
}
