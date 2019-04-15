
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalData extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	fullName;
	private String	statement;
	private String	phoneNumber;
	private String	gitHub;
	private String	linkedIn;
	// Relationship -----------------------------------------------------------
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

	@NotBlank
	public String getFullName() {
		return this.fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@NotBlank
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}

	@NotBlank
	@Pattern(regexp = "[0-9]{9}")
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@NotBlank
	@URL
	public String getGitHub() {
		return gitHub;
	}
	public void setGitHub(String gitHub) {
		this.gitHub = gitHub;
	}

	@NotBlank
	@URL
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
}
