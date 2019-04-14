
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.bouncycastle.asn1.x509.sigi.PersonalData;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	// Attributes -------------------------------------------------------------
	//	private Integer							dataNumber;
	// Relationships ----------------------------------------------------------
	private PersonalData					personalData;
	private Collection<PositionData>		positionData;
	private Collection<EducationData>		educationData;
	private Collection<MiscellaneousData>	miscellaneousData;


	//	public Integer getRecordNumber(){
	//		return this.recordNumber;
	//	}
	//	public void setRecordNumber(Integer recordNumber){
	//		this.recordNumber = recordNumber;
	//	}

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

	@Valid
	@OneToMany
	public Collection<PositionData> getPositionData() {
		return this.positionData;
	}

	public void setPositionData(final Collection<PositionData> positionData) {
		this.positionData = positionData;
	}

	@Valid
	@OneToMany
	public Collection<EducationData> getEducationData() {
		return this.educationData;
	}

	public void setEducationData(final Collection<EducationData> educationData) {
		this.educationData = educationData;
	}

	@Valid
	@OneToMany
	public Collection<MiscellaneousData> getMiscellaneousData() {
		return this.miscellaneousData;
	}

	public void setMiscellaneousData(final Collection<MiscellaneousData> miscellaneousData) {
		this.miscellaneousData = miscellaneousData;
	}
	
	//	public Integer updateRecordNumber(){
	//		Integer total = this.getMiscellaneousData().size()+this.getEducationData().size()+this.getPositionData().size()+1;
	//		this.setRecordNumber(total);
	//		return total;
	//	}
}