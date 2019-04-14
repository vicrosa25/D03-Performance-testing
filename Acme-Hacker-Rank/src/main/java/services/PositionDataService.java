package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import domain.Hacker;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	// Manage Repository
	@Autowired
	private PositionDataRepository	positionDataRepository;

	// Supporting services
	@Autowired
	private HackerService			hackerService;


	// CRUD methods
	public PositionData create() {
		final PositionData result = new PositionData();

		return result;
	}

	public PositionData findOne(final int positionDataID) {
		final PositionData result = this.positionDataRepository.findOne(positionDataID);
		Assert.notNull(result);

		return result;
	}

	public PositionData save(final PositionData positionData) {
		Assert.notNull(positionData);

		if (positionData.getId() != 0) {
			final Hacker principal = this.hackerService.findByPrincipal();
			Assert.isTrue(principal.getCurriculas().contains(positionData.getCurricula()));
		}
		final PositionData result = this.positionDataRepository.save(positionData);

		return result;
	}

	public void delete(final PositionData positionData) {
		Assert.notNull(positionData);
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.isTrue(principal.getCurriculas().contains(positionData.getCurricula()));

		this.positionDataRepository.delete(positionData);
	}
	/*** Other methods ***/
	
	public void flush() {
		this.positionDataRepository.flush();
	}
}