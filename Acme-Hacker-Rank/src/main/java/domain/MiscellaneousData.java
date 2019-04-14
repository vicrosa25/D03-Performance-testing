
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousData extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String				text;
	private Collection<Url>		attachments;
	// Relationship -----------------------------------------------------------
	private Curricula		curricula;


	@ManyToOne(optional = false)
	public Curricula getCurricula() {
		return this.curricula;
	}

	public void setCurricula(Curricula curricula) {
		this.curricula = curricula;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Valid
	@ElementCollection
	public Collection<Url> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(Collection<Url> attachments) {
		this.attachments = attachments;
	}
}
