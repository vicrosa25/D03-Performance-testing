
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Hacker extends Actor {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Collection<Application> applications;
	private Collection<Curricula>	curriculas;
	private Finder					finder;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@OneToMany(mappedBy = "hacker")
	public Collection<Application> getApplications() {
		return applications;
	}

	public void setApplications(Collection<Application> applications) {
		this.applications = applications;
	}

	@OneToMany(mappedBy = "hacker")
	public Collection<Curricula> getCurriculas() {
		return curriculas;
	}

	public void setCurriculas(Collection<Curricula> curriculas) {
		this.curriculas = curriculas;
	}

}
