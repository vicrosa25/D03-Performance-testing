package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousDataRepository;
import domain.Hacker;
import domain.MiscellaneousData;

@Service
@Transactional
public class MiscellaneousDataService {

	// Manage Repository
	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	// Supporting services
	@Autowired
	private HackerService			hackerService;


	// CRUD methods
	public MiscellaneousData create() {
		final MiscellaneousData result = new MiscellaneousData();

		return result;
	}

	public MiscellaneousData findOne(final int miscellaneousDataID) {
		final MiscellaneousData result = this.miscellaneousDataRepository.findOne(miscellaneousDataID);
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousData save(final MiscellaneousData miscellaneousData) {
		Assert.notNull(miscellaneousData);

		if (miscellaneousData.getId() != 0) {
			final Hacker principal = this.hackerService.findByPrincipal();
			Assert.isTrue(principal.getCurriculas().contains(miscellaneousData.getCurricula()));
		}
		final MiscellaneousData result = this.miscellaneousDataRepository.save(miscellaneousData);

		return result;
	}

	public void delete(final MiscellaneousData miscellaneousData) {
		Assert.notNull(miscellaneousData);
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.isTrue(principal.getCurriculas().contains(miscellaneousData.getCurricula()));

		this.miscellaneousDataRepository.delete(miscellaneousData);
	}
	/*** Other methods ***/
	
	public void flush() {
		this.miscellaneousDataRepository.flush();
	}
}