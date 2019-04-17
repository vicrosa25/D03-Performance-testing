
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Actor;
import domain.Answer;
import domain.Application;
import domain.Company;
import domain.Hacker;
import domain.Message;
import domain.Problem;

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

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private MessageService			messageService;


	/*************************************
	 * CRUD methods
	 *************************************/
	public Application create() {
		Application result;
		Actor principal;

		// Principal must be a Hacker
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Hacker.class, principal);
		Hacker hacker = (Hacker) principal;
		
		result = new Application();
		
		// Create date
		Date date = new Date();
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

	public void forceDelete(Application application) {
		Assert.notNull(application);
		application.getHacker().getApplications().remove(application);
		application.getPosition().getApplications().remove(application);

		this.applicationRepository.delete(application);
	}

	/** OTHER METHODS **/
	public Collection<Application> findByProblem(Problem problem) {
		Collection<Application> result = this.applicationRepository.findByProblem(problem.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findByCompany(Company company) {
		Collection<Application> result = this.applicationRepository.findByCompany(company.getId());
		Assert.notNull(result);

		return result;
	}

	public void accept(Application application) {
		Assert.notNull(application);
		Assert.isTrue(this.findByCompany(this.companyService.findByPrincipal()).contains(application));
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));

		application.setStatus("ACCEPTED");
		this.automaticNotification(application, "accepted");

		this.applicationRepository.save(application);

	}

	public void reject(Application application) {
		Assert.notNull(application);
		Assert.isTrue(this.findByCompany(this.companyService.findByPrincipal()).contains(application));
		Assert.isTrue(application.getStatus().equals("SUBMITTED"));

		application.setStatus("REJECTED");
		this.automaticNotification(application, "rejected");

		this.applicationRepository.save(application);

	}

	private void automaticNotification(Application application, String decission) {
		Collection<Actor> recipients = new ArrayList<Actor>();
		recipients.add(application.getHacker());

		Message notification = this.messageService.create();

		notification.setBody("Your application to the position " + application.getPosition().getTitle() + " has been " + decission);
		notification.setIsNotification(true);
		notification.setPriority("MEDIUM");
		notification.setRecipients(recipients);
		notification.setSubject("The company " + application.getPosition().getCompany().getCommercialName() + " has made a decission on your application");

		this.messageService.save(notification);
	}
}
