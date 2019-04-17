
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Finder;
import domain.Message;
import domain.Position;
import domain.Problem;

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
	private FinderService		finderService;

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ProblemService		problemService;

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
		final Company company = (Company) principal;

		// Generate a ticker for the position
		result.setTicker(this.generateTicker(company.getCommercialName()));

		result.setApplications(new ArrayList<Application>());
		result.setProblems(new ArrayList<Problem>());
		result.setFinalMode(false);
		result.setCancelled(false);

		return result;
	}

	public Position findOne(final int positionId) {
		final Position result = this.positionRepository.findOne(positionId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Position> findAll() {
		final Collection<Position> result = this.positionRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Position> findAllFinal() {
		final Collection<Position> result = this.positionRepository.findAllFinal();
		Assert.notNull(result);

		return result;
	}

	public Position save(final Position position) {
		Assert.notNull(position);
		Assert.isTrue(position.getCompany() == this.companyService.findByPrincipal());

		// If there is no problems, assign an empty collection to avoid nullPointerException
		if (position.getProblems() == null)
			position.setProblems(new ArrayList<Problem>());

		final Position result = this.positionRepository.save(position);
		for (final Problem p : result.getCompany().getProblems()) {
			if (!p.getPositions().contains(result) && position.getProblems().contains(p))
				p.getPositions().add(result);
			if (p.getPositions().contains(result) && !position.getProblems().contains(p))
				p.getPositions().remove(result);
		}
		if (position.getFinalMode())
			this.automaticNotification(result);

		return result;
	}

	public void delete(final Position position) {
		Assert.notNull(position);
		Assert.isTrue(this.companyService.findByPrincipal() == position.getCompany());
		Assert.isTrue(!position.getFinalMode());

		for (final Problem p : position.getProblems())
			p.getPositions().remove(position);

		this.positionRepository.delete(position);
	}

	public void forceDelete(final Position position) {
		Assert.notNull(position);
		Assert.isTrue(this.companyService.findByPrincipal() == position.getCompany());

		for (final Problem p : this.problemService.findAll())
			p.getPositions().remove(position);
		for (final Finder finder : this.finderService.findAll())
			finder.getPositions().remove(position);
		final Iterator<Application> apps = new ArrayList<Application>(position.getApplications()).iterator();

		while (apps.hasNext()) {
			final Application next = apps.next();
			this.applicationService.forceDelete(next);
			position.getApplications().remove(next);
			apps.remove();
		}

		this.positionRepository.delete(position);
	}

	public void cancel(final Position position) {
		Assert.notNull(position);
		Assert.isTrue(this.companyService.findByPrincipal() == position.getCompany());
		Assert.isTrue(position.getFinalMode());

		position.setCancelled(true);

		this.positionRepository.save(position);
	}

	/*************************************
	 * Other methods
	 *************************************/
	public String generateTicker(final String companyName) {
		String ticker = "";
		final String tickerText = companyName.substring(0, 4);
		final String tickerAlphanumeric = RandomStringUtils.randomNumeric(4);
		ticker = ticker.concat(tickerText).concat("-").concat(tickerAlphanumeric);
		return ticker;
	}

	public Position reconstruct(final Position position, final Errors errors) {
		final Position result = this.create();

		if (position.getId() != 0) {
			final Position temp = this.findOne(position.getId());
			Assert.isTrue(this.companyService.findByPrincipal() == temp.getCompany());

			// Not updated attributes
			result.setId(temp.getId());
			result.setVersion(temp.getVersion());
			result.setTicker(temp.getTicker());

			// Relantionships
			result.setProblems(temp.getProblems());
		} else
			result.setTicker(this.generateTicker(this.companyService.findByPrincipal().getCommercialName()));

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

		result.setCompany(this.companyService.findByPrincipal());
		result.setProblems(position.getProblems());

		this.validator.validate(result, errors);

		return result;
	}

	private void automaticNotification(final Position position) {
		final Collection<Actor> recipients = new ArrayList<Actor>();
		for (Finder finder : this.finderService.findAll()) {
			finder = this.finderService.updateResults(finder);
			if (finder.getPositions().contains(position))
				recipients.add(this.hackerService.findByFinder(finder));
		}
		if (!recipients.isEmpty()) {
			final Message notification = this.messageService.create();

			notification.setBody("Your finder has a new match");
			notification.setIsNotification(true);
			notification.setPriority("MEDIUM");
			notification.setRecipients(recipients);
			notification.setSubject("The company " + position.getCompany().getCommercialName() + " has made a new offer that match your finder: " + position.getTitle());

			this.messageService.save(notification);
		}
	}
}
