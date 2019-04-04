
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Application;
import domain.Hacker;
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

		result.setHacker(hacker);
		result.setStatus("PENDING");

		return result;
	}

	public Application findOne(final int id) {
		final Application result = this.applicationRepository.findOne(id);

		Assert.notNull(result);

		return result;
	}

	public Collection<Application> findAll() {
		final Collection<Application> result = this.applicationRepository.findAll();
		//Assert.notNull(result);

		return result;
	}

	public Application save(Application application) {
		Assert.notNull(application);
		Actor principal;

		// Principal must be a Member or a Brotherhood
		principal = this.actorService.findByPrincipal();

		if (application.getId() != 0) {
			Assert.isInstanceOf(Hacker.class, principal);
		}

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
