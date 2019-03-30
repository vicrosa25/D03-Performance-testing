
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Area extends DomainEntity {

	// Attributes
	private String name;
	private Collection<Url> pictures;

	
	
	@ElementCollection
	@Valid
	public Collection<Url> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<Url> pictures) {
		this.pictures = pictures;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	
	
	// Relationships ----------------------------------------------------------------------
	private Collection<Brotherhood> brotherhoods;
	private Chapter					chapter;

	
	@OneToMany(mappedBy = "area")
	public Collection<Brotherhood> getBrotherhoods() {
		return this.brotherhoods;
	}

	public void setBrotherhoods(Collection<Brotherhood> brotherhoods) {
		this.brotherhoods = brotherhoods;
	}

	@OneToOne(optional = true)
	public Chapter getChapter() {
		return this.chapter;
	}

	
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	// Other methods ------------------------------------------------------------------------
	@Override
	public String toString() {
		return "Area [name=" + this.name + "]";
	}
}
