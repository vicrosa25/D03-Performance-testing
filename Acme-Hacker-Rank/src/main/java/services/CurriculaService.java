package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@Service
@Transactional
public class CurriculaService {

	// Manage Repository
	@Autowired
	private CurriculaRepository	curriculaRepository;

	// Supporting services
	@Autowired
	private HackerService			hackerService;

	@Autowired
	private PersonalDataService			personalDataService;

	@Autowired
	private PositionDataService			positionDataService;

	@Autowired
	private EducationDataService		educationDataService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;


	// CRUD methods
	public Curricula create() {
		final Curricula result = new Curricula();

		result.setEducationData(new ArrayList<EducationData>());
		result.setPositionData(new ArrayList<PositionData>());
		result.setMiscellaneousData(new ArrayList<MiscellaneousData>());

		return result;
	}

	public Curricula findOne(final int curriculaID) {
		final Curricula result = this.curriculaRepository.findOne(curriculaID);
		Assert.notNull(result);

		return result;
	}

	public Curricula save(final Curricula curricula) {
		Assert.notNull(curricula);
		final Hacker principal = this.hackerService.findByPrincipal();

		//Check the owner is the principal, if it has no owner, assign the principal.
		if (curricula.getId() != 0) {
			Assert.isTrue(principal.getCurriculas().contains(curricula));
		} else {
			curricula.setApplied(false);
			curricula.setHacker(principal);
		}
		final Curricula result = this.curriculaRepository.save(curricula);

		return result;
	}

	public void delete(final Curricula curricula) {
		Assert.notNull(curricula);
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.isTrue(principal.getCurriculas().contains(curricula));

		if (curricula.getPersonalData() != null) {
			this.personalDataService.delete(curricula.getPersonalData());
		}
		Iterator<EducationData> education = new ArrayList<EducationData>(curricula.getEducationData()).iterator();
		Iterator<MiscellaneousData> misc = new ArrayList<MiscellaneousData>(curricula.getMiscellaneousData()).iterator();
		Iterator<PositionData> position = new ArrayList<PositionData>(curricula.getPositionData()).iterator();

		while (education.hasNext()) {
			EducationData next = education.next();
			this.educationDataService.delete(next);
			education.remove();
		}

		while (misc.hasNext()) {
			MiscellaneousData next = misc.next();
			this.miscellaneousDataService.delete(next);
			misc.remove();
		}

		while (position.hasNext()) {
			PositionData next = position.next();
			this.positionDataService.delete(next);
			position.remove();
		}

		curricula.getHacker().getCurriculas().remove(curricula);

		this.curriculaRepository.delete(curricula);
	}
	/*** Other methods ***/
	
	public void flush() {
		this.curriculaRepository.flush();
	}

	public Curricula copyCurricula(Curricula curricula) {
		Curricula result = this.create();
		result.setHacker(this.hackerService.findByPrincipal());
		result.setTitle(curricula.getTitle());
		result.setApplied(true);
		
		result = this.curriculaRepository.save(result);
		curricula.getHacker().getCurriculas().add(result);
		
		if(curricula.getPersonalData()!=null){
			PersonalData copy = new PersonalData();
			PersonalData pd = curricula.getPersonalData();
			
			copy.setCurricula(result);
			copy.setFullName(pd.getFullName());
			copy.setGitHub(pd.getGitHub());
			copy.setLinkedIn(pd.getLinkedIn());
			copy.setPhoneNumber(pd.getPhoneNumber());
			copy.setStatement(pd.getStatement());
			copy = this.personalDataService.save(copy);
			result.setPersonalData(copy);
		}
		for(EducationData ed:curricula.getEducationData()){
			EducationData copy = new EducationData();
			copy.setCurricula(result);
			copy.setDegree(ed.getDegree());
			copy.setEndDate(ed.getEndDate());
			copy.setInstitution(ed.getInstitution());
			copy.setMark(ed.getMark());
			copy.setStartDate(ed.getStartDate());
			copy = this.educationDataService.save(copy);
			result.getEducationData().add(copy);
		}
		for(MiscellaneousData md:curricula.getMiscellaneousData()){
			MiscellaneousData copy = new MiscellaneousData();
			copy.setCurricula(result);
			copy.setText(md.getText());
			copy.setAttachments(md.getAttachments());
			copy = this.miscellaneousDataService.save(copy);
			result.getMiscellaneousData().add(copy);
		}
		for (PositionData pd : curricula.getPositionData()) {
			PositionData copy = new PositionData();
			copy.setCurricula(result);
			copy.setDescription(pd.getDescription());
			copy.setTitle(pd.getTitle());
			copy.setEndDate(pd.getEndDate());
			copy.setStartDate(pd.getStartDate());
			copy = this.positionDataService.save(copy);
			result.getPositionData().add(copy);
		}

		result.setApplied(true);

		return this.save(result);
	}

	public Collection<Curricula> findAllPrincipalNotApplied() {
		Collection<Curricula> result = this.curriculaRepository.findAllNoApplied(this.hackerService.findByPrincipal().getId());
		Assert.notNull(result);

		return result;
	}
}