
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	// Attributes -------------------------------------------------------------
	private String					commercialName;

	// Relationships ----------------------------------------------------------
	private Collection<Position>	positions;
	private Collection<Problem>		problems;


	// ------------------------------------------------------------
	@NotBlank
	public String getCommercialName() {
		return this.commercialName;
	}

	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}

	//--------------------------------------------------------------
	@OneToMany(mappedBy = "company")
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(Collection<Position> positions) {
		this.positions = positions;
	}

	@OneToMany(mappedBy = "company")
	public Collection<Problem> getProblems() {
		return this.problems;
	}

	public void setProblems(Collection<Problem> problems) {
		this.problems = problems;
	}

}
