
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
		Company company = (Company) principal;

		result.setPositions(new ArrayList<Position>());
		result.setFinalMode(false);
		result.setCompany(company);
		result.setAttachments(new ArrayList<Url>());
		
		return result;
	}

	public Problem findOne(int problemId) {
		Problem result = this.problemRepository.findOne(problemId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Problem> findAll() {
		Collection<Problem> result = this.problemRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Problem save(Problem problem) {
		Assert.notNull(problem);
		Assert.isTrue(this.companyService.findByPrincipal() == problem.getCompany());

		Problem result = this.problemRepository.save(problem);

		return result;
	}

	public void delete(Problem problem) {
		Assert.notNull(problem);
		Assert.isTrue(this.companyService.findByPrincipal() == problem.getCompany());

		for (Position p : problem.getCompany().getPositions()) {
			p.getProblems().remove(problem);
		}

		this.problemRepository.delete(problem);
	}

	/** OTHER METHODS **/

	public Collection<Problem> getPrincipalFinalMode() {
		Collection<Problem> result = this.getCompanyFinals(this.companyService.findByPrincipal());
		Assert.notNull(result);
		return result;
	}

	public Collection<Problem> getCompanyFinals(Company company) {
		Collection<Problem> result = this.problemRepository.getCompanyFinals(company.getId());
		Assert.notNull(result);
		return result;
	}

	public boolean checkApplicationsProblem(Problem problem) {
		Collection<Application> result = this.applicationService.findByProblem(problem);
		Assert.notNull(result);

		return !result.isEmpty();
	}
}
