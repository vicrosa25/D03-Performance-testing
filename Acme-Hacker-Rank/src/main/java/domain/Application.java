
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Date	creationMoment;
	private String	status;
	private Date	submitMoment;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	@NotBlank
	@Pattern(regexp = "^(PENDING|SUBMITTED|ACCEPTED|REJECTED)$")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getSubmitMoment() {
		return submitMoment;
	}

	public void setSubmitMoment(Date submitMoment) {
		this.submitMoment = submitMoment;
	}


	// Relationships ----------------------------------------------------------
	private Hacker		hacker;
	private Answer		answer;
	private Position	position;
	private Problem		problem;
	private Curricula	curricula;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Curricula getCurricula() {
		return this.curricula;
	}

	public void setCurricula(Curricula curricula) {
		this.curricula = curricula;
	}
	
	
	@ManyToOne(optional = true)
	public Hacker getHacker() {
		return hacker;
	}

	
	public void setHacker(Hacker hacker) {
		this.hacker = hacker;
	}

	
	@Valid
	@OneToOne(optional = true)
	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	
	@Valid
	@ManyToOne(optional = false)
	public Problem getProblem() {
		return problem;
	}

	
	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	@Override
	public String toString() {
		return "Application [creationMoment=" + creationMoment + ", status=" + status + ", submitMoment=" + submitMoment + ", answer=" + answer + "]";
	}
}
