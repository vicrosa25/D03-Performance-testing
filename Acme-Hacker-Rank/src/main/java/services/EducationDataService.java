
package services;

import java.util.Collection;

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

	public Collection<EducationData> findAll() {
		final Collection<EducationData> result = this.educationDataRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EducationData save(final EducationData educationData) {
		boolean nuevo = false;
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.notNull(educationData);
		Assert.isTrue(principal.getCurriculas().contains(educationData.getCurricula()));

		if (educationData.getId() == 0)
			nuevo = true;
		final EducationData result = this.educationDataRepository.save(educationData);

		if (nuevo)
			result.getCurricula().getEducationData().add(result);

		return result;
	}

	public void delete(final EducationData educationData) {
		Assert.notNull(educationData);
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.isTrue(principal.getCurriculas().contains(educationData.getCurricula()));

		educationData.getCurricula().getEducationData().remove(educationData);

		this.educationDataRepository.delete(educationData);
	}
	/*** Other methods ***/

	public void flush() {
		this.educationDataRepository.flush();
	}
}
