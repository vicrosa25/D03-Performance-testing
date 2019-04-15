package services;

import java.util.ArrayList;
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
			curricula.setHacker(principal);
		}
		final Curricula result = this.curriculaRepository.save(curricula);

		return result;
	}

	public void delete(final Curricula curricula) {
		Assert.notNull(curricula);
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.isTrue(principal.getCurriculas().contains(curricula));

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
}