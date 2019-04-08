
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Answer;
import domain.Application;
import domain.Hacker;
import domain.Problem;
import repositories.ApplicationRepository;

@Service
@Transactional
public class ApplicationService {

	// Manage Repository
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;
	
	
	@Autowired
	private AnswerService			answerService;


	/*************************************
	 * CRUD methods
	 *************************************/
	public Application create() {
		Application result;
		Actor principal;

		result = new Application();

		// Principal must be a Member
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Hacker.class, principal);
		Hacker hacker = (Hacker) principal;
		
		// Create date
		final Date date = new Date();
		date.setTime(date.getTime() - 5000);

		
		// Default settings
		result.setHacker(hacker);
		result.setCreationMoment(date);
		result.setStatus("PENDING");

		return result;
	}

	public Application findOne(int id) {
		final Application result = this.applicationRepository.findOne(id);

		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result = this.applicationRepository.findAll();
		Assert.notNull(result);

		return result;
	}
	
	
	public Collection<Application> findByHacker() {
		Collection<Application> result;
		
		// Check the principal is a Hacker
		Actor principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Hacker.class, principal);
		
		result = this.applicationRepository.findByHacker(principal.getId());
		
		return result;	
	}

	public Application save(Application application) {
		Assert.notNull(application);
		Actor principal;

		// Principal must be a Hacker 
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Hacker.class, principal);
		
		// Checke for Position and Problems
		Assert.notNull(application.getPosition());
		Assert.notEmpty(application.getPosition().getProblems());
		ArrayList<Problem> problems = new ArrayList<Problem>(application.getPosition().getProblems());
		
		// Randomly assign a apropiate problem to the application
		if (application.getProblem() == null) {
			Random rand = new Random();
			Problem problem = problems.get(rand.nextInt(problems.size()));
			Assert.notNull(problem);
			application.setProblem(problem);
		}
		
		
		// Finnaly save Application with problem
		return this.applicationRepository.save(application);
	}
	
	public Application update(Application application) {
		Assert.notNull(application);
		Assert.notNull(application.getAnswer());
		
		Answer answer;
		
		application.setStatus("SUBMITTED");
		
		
		answer = this.answerService.save(application.getAnswer());
		application.setAnswer(answer);
		
		return this.applicationRepository.save(application);
	}
	
	

	public void delete(int applicationId) {
		Assert.isTrue(applicationId != 0);
		final Application application = this.findOne(applicationId);

		Assert.notNull(application);
		Actor principal;

		Assert.isTrue(application.getStatus().equals("PENDING"));

		// Principal must be a Hacker
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Hacker.class, principal);

		Hacker hacker = (Hacker) principal;
		Assert.isTrue(hacker.getApplications().contains(application));

		this.applicationRepository.delete(application);
	}
}
