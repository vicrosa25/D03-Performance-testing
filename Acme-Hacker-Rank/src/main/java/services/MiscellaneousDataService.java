
package services;

import java.util.Collection;

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
	private HackerService				hackerService;


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

	public Collection<MiscellaneousData> findAll() {
		final Collection<MiscellaneousData> result = this.miscellaneousDataRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousData save(final MiscellaneousData miscellaneousData) {
		boolean nuevo = false;
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.notNull(miscellaneousData);
		Assert.isTrue(principal.getCurriculas().contains(miscellaneousData.getCurricula()));

		if (miscellaneousData.getId() == 0)
			nuevo = true;
		final MiscellaneousData result = this.miscellaneousDataRepository.save(miscellaneousData);

		if (nuevo)
			result.getCurricula().getMiscellaneousData().add(result);

		return result;
	}

	public void delete(final MiscellaneousData miscellaneousData) {
		Assert.notNull(miscellaneousData);
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.isTrue(principal.getCurriculas().contains(miscellaneousData.getCurricula()));

		miscellaneousData.getCurricula().getMiscellaneousData().remove(miscellaneousData);

		this.miscellaneousDataRepository.delete(miscellaneousData);
	}
	/*** Other methods ***/

	public void flush() {
		this.miscellaneousDataRepository.flush();
	}
}
