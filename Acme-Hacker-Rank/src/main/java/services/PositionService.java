
package services;

import java.util.Collection;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Company;
import domain.Position;
import repositories.PositionRepository;

@Service
@Transactional
public class PositionService {

	// Manage Repository
	@Autowired
	private PositionRepository	positionRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


	/*************************************
	 * CRUD methods
	 *************************************/
	public Position create() {
		Position result;
		Actor principal;

		result = new Position();

		// Principal must be a Company
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Company.class, principal);
		Company company = (Company) principal;

		// Default setting for position Company and Ticker
		result.setCompany(company);
		result.setTicker(this.generateTicker(company.getCommercialName()));
		
		return result;
	}

	public Position findOne(int positionId) {
		Position result = this.positionRepository.findOne(positionId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Position> findAll() {
		Collection<Position> result = this.positionRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Position save(Position position) {
		Assert.notNull(position);
		Position result = this.positionRepository.save(position);

		return result;
	}

	public void delete(Position position) {
		Assert.notNull(position);

		this.positionRepository.delete(position);
	}
	
	/*************************************
	 * Other methods
	 *************************************/
	public String generateTicker(String companyName) {
		String ticker = "";
		final String tickerText = companyName.substring(0, 3);
		final String tickerAlphanumeric = RandomStringUtils.randomNumeric(4);
		ticker = ticker.concat(tickerText).concat("-").concat(tickerAlphanumeric);
		return ticker;
	}
}
