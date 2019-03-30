
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
public class History extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Integer							recordNumber;
	// Relationships ----------------------------------------------------------
	private InceptionRecord					inceptionRecord;
	private Collection<PeriodRecord>		periodRecords;
	private Collection<LegalRecord>			legalRecords;
	private Collection<LinkRecord>			linkRecords;
	private Collection<MiscellaneousRecord>	miscellaneousRecords;


	public Integer getRecordNumber(){
		return this.recordNumber;
	}
	public void setRecordNumber(Integer recordNumber){
		this.recordNumber = recordNumber;
	}
	@Valid
	@NotNull
	@OneToOne(optional = false)
	public InceptionRecord getInceptionRecord() {
		return this.inceptionRecord;
	}

	public void setInceptionRecord(final InceptionRecord inceptionRecord) {
		this.inceptionRecord = inceptionRecord;
	}

	@Valid
	@OneToMany
	public Collection<PeriodRecord> getPeriodRecords() {
		return this.periodRecords;
	}

	public void setPeriodRecords(final Collection<PeriodRecord> periodRecords) {
		this.periodRecords = periodRecords;
	}

	@Valid
	@OneToMany
	public Collection<LegalRecord> getLegalRecords() {
		return this.legalRecords;
	}

	public void setLegalRecords(final Collection<LegalRecord> legalRecords) {
		this.legalRecords = legalRecords;
	}

	@Valid
	@OneToMany
	public Collection<LinkRecord> getLinkRecords() {
		return this.linkRecords;
	}

	public void setLinkRecords(final Collection<LinkRecord> linkRecords) {
		this.linkRecords = linkRecords;
	}

	@Valid
	@OneToMany
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}

	public void setMiscellaneousRecords(final Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}
	
	public Integer updateRecordNumber(){
		Integer total = this.getLegalRecords().size()+this.getLinkRecords().size()+this.getMiscellaneousRecords().size()+
			this.getMiscellaneousRecords().size()+1;
		this.setRecordNumber(total);
		return total;
	}
}