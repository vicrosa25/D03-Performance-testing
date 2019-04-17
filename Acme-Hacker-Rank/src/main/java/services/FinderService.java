
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Service
@Transactional
public class FinderService {

	// Manage Repository
	@Autowired
	private FinderRepository		finderRepository;

	// Other services
	@Autowired
	private HackerService			hackerService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ConfigurationsService	configurationsService;


	// CRUD methods
	public Finder create() {
		final Finder result = new Finder();

		final Calendar lastUpdate = Calendar.getInstance();
		lastUpdate.add(Calendar.YEAR, -20);

		result.setLastUpdate(lastUpdate.getTime());
		return this.finderRepository.save(result);
	}

	public Finder findOne(final int finderID) {
		final Finder result = this.finderRepository.findOne(finderID);
		Assert.notNull(result);

		return result;
	}

	public Collection<Finder> findAll() {
		final Collection<Finder> result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.isTrue(principal.getFinder().getId() == finder.getId());

		final Finder result = this.finderRepository.save(finder);

		return result;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		final Hacker principal = this.hackerService.findByPrincipal();
		Assert.isTrue(principal.getFinder().getId() == finder.getId());

		this.finderRepository.delete(finder);
	}

	// Check if something has changed, if so the results are updated
	// If not, the results are updated if it has not been updated
	// in the specified time
	public Finder checkChanges(final Finder finder) {
		final Finder old = this.findOne(finder.getId());
		if ((finder.getMinSalary() != old.getMinSalary()) || (finder.getKeyword() != old.getKeyword()) || (finder.getMaxSalary() != old.getMaxSalary()) || (finder.getDeadline() != old.getDeadline())) {

			final Finder saved = this.updateResults(finder);
			return saved;

		} else {
			finder.setPositions(this.getResults(finder));
			return finder;
		}
	}

	// Check if it has passed enough time to update and return the results
	public Collection<Position> getResults(final Finder finder) {
		final Calendar siguienteActualizacion = Calendar.getInstance();
		siguienteActualizacion.setTime(finder.getLastUpdate());
		final Calendar actual = Calendar.getInstance();

		siguienteActualizacion.add(Calendar.HOUR, this.configurationsService.getConfiguration().getCacheTime());

		if (actual.after(siguienteActualizacion))
			this.updateResults(finder);
		return finder.getPositions();
	}

	public Finder updateResults(final Finder finder) {
		Assert.notNull(finder);
		final HashSet<Position> result = new HashSet<Position>(this.positionService.findAllFinal());

		if (finder.getMinSalary() != null)
			result.retainAll(this.finderRepository.filterByMinSalary(new Double(finder.getMinSalary())));

		if (finder.getMaxSalary() != null)
			result.retainAll(this.finderRepository.filterByMaxSalary(new Double(finder.getMaxSalary())));

		if (finder.getKeyword() != null)
			result.retainAll(this.finderRepository.filterByKeyword("%" + finder.getKeyword() + "%"));

		if (finder.getDeadline() != null)
			result.retainAll(this.finderRepository.filterByDeadline(finder.getDeadline()));

		final ArrayList<Position> positions = new ArrayList<Position>(result);

		if (result.size() > this.configurationsService.getConfiguration().getFinderMaxResult())
			finder.setPositions(positions.subList(0, this.configurationsService.getConfiguration().getFinderMaxResult() - 1));
		else
			finder.setPositions(positions);
		finder.setLastUpdate(new Date());
		return this.finderRepository.save(finder);
	}

	public Finder clear(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(this.hackerService.findByPrincipal().getFinder() == finder);

		finder.setDeadline(null);
		finder.setKeyword(null);
		finder.setLastUpdate(new Date());
		finder.setMaxSalary(null);
		finder.setMinSalary(null);
		finder.setPositions(this.positionService.findAllFinal());

		return this.save(finder);

	}
}
