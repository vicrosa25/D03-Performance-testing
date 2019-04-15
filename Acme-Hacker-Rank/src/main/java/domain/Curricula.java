
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	// Attributes -------------------------------------------------------------
	//	private Integer							dataNumber;
	private String							title;
	// Relationships ----------------------------------------------------------
	private PersonalData					personalData;
	private Collection<PositionData>		positionData;
	private Collection<EducationData>		educationData;
	private Collection<MiscellaneousData>	miscellaneousData;
	private Hacker							hacker;
	private Boolean							applied;


	//	public Integer getRecordNumber(){
	//		return this.recordNumber;
	//	}
	//	public void setRecordNumber(Integer recordNumber){
	//		this.recordNumber = recordNumber;
	//	}

	@ManyToOne(optional = false)
	public Hacker getHacker() {
		return hacker;
	}

	public void setHacker(Hacker hacker) {
		this.hacker = hacker;
	}

	@Valid
	@OneToOne(optional = true)
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

	@Valid
	@OneToMany(mappedBy = "curricula")
	public Collection<PositionData> getPositionData() {
		return this.positionData;
	}

	public void setPositionData(final Collection<PositionData> positionData) {
		this.positionData = positionData;
	}

	@Valid
	@OneToMany(mappedBy = "curricula")
	public Collection<EducationData> getEducationData() {
		return this.educationData;
	}

	public void setEducationData(final Collection<EducationData> educationData) {
		this.educationData = educationData;
	}

	@Valid
	@OneToMany(mappedBy = "curricula")
	public Collection<MiscellaneousData> getMiscellaneousData() {
		return this.miscellaneousData;
	}

	public void setMiscellaneousData(final Collection<MiscellaneousData> miscellaneousData) {
		this.miscellaneousData = miscellaneousData;
	}

	public Boolean getApplied() {
		return applied;
	}

	public void setApplied(Boolean applied) {
		this.applied = applied;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	//	public Integer updateRecordNumber(){
	//		Integer total = this.getMiscellaneousData().size()+this.getEducationData().size()+this.getPositionData().size()+1;
	//		this.setRecordNumber(total);
	//		return total;
	//	}
}