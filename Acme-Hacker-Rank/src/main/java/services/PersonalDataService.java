package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import domain.Hacker;
import domain.PersonalData;

@Service
@Transactional
public class PersonalDataService {

	// Manage Repository
	@Autowired
	private PersonalDataRepository	personalDataRepository;

	// Supporting services
	@Autowired
	private HackerService			hackerService;


	// CRUD methods
	public PersonalData create() {
		final PersonalData result = new PersonalData();

		return result;
	}

	public PersonalData findOne(final int personalDataID) {
		final PersonalData result = this.personalDataRepository.findOne(personalDataID);
		Assert.notNull(result);

		return result;
	}

	public PersonalData save(final PersonalData personalData) {
		Assert.notNull(personalData);

		if (personalData.getId() != 0) {
			final Hacker principal = this.hackerService.findByPrincipal();
			Assert.isTrue(principal.getCurriculas().contains(personalData.getCurricula()));
		}
		final PersonalData result = this.personalDataRepository.save(personalData);

		return result;
	}

	public void delete(final PersonalData personalData) {
		Assert.notNull(personalData);
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.isTrue(principal.getCurriculas().contains(personalData.getCurricula()));

		this.personalDataRepository.delete(personalData);
	}
	/*** Other methods ***/
	
	public void flush() {
		this.personalDataRepository.flush();
	}
}