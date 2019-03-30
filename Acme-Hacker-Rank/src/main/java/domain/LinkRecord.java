
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class LinkRecord extends Record {

	// Attributes -------------------------------------------------------------
	private Brotherhood link;

	@NotNull
	@ManyToOne(optional = true)
	public Brotherhood getLink() {
		return this.link;
	}

	public void setLink(Brotherhood link) {
		this.link = link;
	}
}
