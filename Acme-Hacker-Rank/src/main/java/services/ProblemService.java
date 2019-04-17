
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProblemRepository;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Position;
import domain.Problem;
import domain.Url;

@Service
@Transactional
public class ProblemService {

	// Manage Repository
	@Autowired
	private ProblemRepository	problemRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ApplicationService	applicationService;


	/*************************************
	 * CRUD methods
	 *************************************/
	public Problem create() {
		Problem result;
		Actor principal;

		result = new Problem();

		// Principal must be a Company
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Company.class, principal);
		final Company company = (Company) principal;

		result.setPositions(new ArrayList<Position>());
		result.setFinalMode(false);
		result.setCompany(company);
		result.setAttachments(new ArrayList<Url>());

		return result;
	}

	public Problem findOne(final int problemId) {
		final Problem result = this.problemRepository.findOne(problemId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Problem> findAll() {
		final Collection<Problem> result = this.problemRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Problem save(final Problem problem) {
		Assert.notNull(problem);
		Assert.isTrue(this.companyService.findByPrincipal() == problem.getCompany());

		final Problem result = this.problemRepository.save(problem);

		return result;
	}

	public void delete(final Problem problem) {
		Assert.notNull(problem);
		Assert.isTrue(this.companyService.findByPrincipal() == problem.getCompany());

		for (final Position p : this.positionService.findAll())
			p.getProblems().remove(problem);

		this.problemRepository.delete(problem);
	}

	/** OTHER METHODS **/

	public Collection<Problem> getPrincipalFinalMode() {
		final Collection<Problem> result = this.getCompanyFinals(this.companyService.findByPrincipal());
		Assert.notNull(result);
		return result;
	}

	public Collection<Problem> getCompanyFinals(final Company company) {
		final Collection<Problem> result = this.problemRepository.getCompanyFinals(company.getId());
		Assert.notNull(result);
		return result;
	}

	public boolean checkApplicationsProblem(final Problem problem) {
		final Collection<Application> result = this.applicationService.findByProblem(problem);
		Assert.notNull(result);

		return !result.isEmpty();
	}
}
