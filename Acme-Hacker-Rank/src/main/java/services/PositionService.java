
package services;

import java.util.Collection;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import domain.Actor;
import domain.Company;
import domain.Position;

@Service
@Transactional
public class PositionService {

	// Manage Repository
	@Autowired
	private PositionRepository	positionRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	@Qualifier("validator")
	private Validator			validator;


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

		// Generate a ticker for the position
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

	public Position reconstruct(final Position position, final BindingResult binding) {
		final Position result = this.create();
		final Position temp = this.findOne(position.getId());

		Assert.isTrue(this.companyService.findByPrincipal() == temp.getCompany());

		// Updated attributes
		result.setId(position.getId());
		result.setDeadline(position.getDeadline());
		result.setDescription(position.getDescription());
		result.setFinalMode(position.getFinalMode());
		result.setProfile(position.getProfile());
		result.setSalary(position.getSalary());
		result.setSkills(position.getSkills());
		result.setTechnologies(position.getTechnologies());
		result.setTitle(position.getTitle());

		// Not updated attributes
		result.setId(temp.getId());
		result.setVersion(temp.getVersion());
		result.setTicker(temp.getTicker());

		// Relantionships
		result.setProblems(temp.getProblems());
		result.setCompany(temp.getCompany());

		this.validator.validate(result, binding);

		return result;
	}
}
