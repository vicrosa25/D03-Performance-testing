package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationDataRepository;
import domain.EducationData;
import domain.Hacker;

@Service
@Transactional
public class EducationDataService {

	// Manage Repository
	@Autowired
	private EducationDataRepository	educationDataRepository;

	// Supporting services
	@Autowired
	private HackerService			hackerService;


	// CRUD methods
	public EducationData create() {
		final EducationData result = new EducationData();

		return result;
	}

	public EducationData findOne(final int educationDataID) {
		final EducationData result = this.educationDataRepository.findOne(educationDataID);
		Assert.notNull(result);

		return result;
	}

	public EducationData save(final EducationData educationData) {
		Assert.notNull(educationData);

		if (educationData.getId() != 0) {
			final Hacker principal = this.hackerService.findByPrincipal();
			Assert.isTrue(principal.getCurriculas().contains(educationData.getCurricula()));
		}
		final EducationData result = this.educationDataRepository.save(educationData);

		return result;
	}

	public void delete(final EducationData educationData) {
		Assert.notNull(educationData);
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.isTrue(principal.getCurriculas().contains(educationData.getCurricula()));

		this.educationDataRepository.delete(educationData);
	}
	/*** Other methods ***/
	
	public void flush() {
		this.educationDataRepository.flush();
	}
}