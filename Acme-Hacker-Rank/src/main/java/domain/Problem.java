
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Problem extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String				title;
	private String				statement;
	private Boolean				finalMode;
	private String				hint;
	private Collection<Url>		attachments;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	public String getHint() {
		return this.hint;
	}

	public void setHint(final String hint) {
		this.hint = hint;
	}

	@NotNull
	@Valid
	@ElementCollection
	public Collection<Url> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Collection<Url> attachments) {
		this.attachments = attachments;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(Boolean finalMode) {
		this.finalMode = finalMode;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Position>	positions;
	private Company					company;


	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(Collection<Position> positions) {
		this.positions = positions;
	}
}
