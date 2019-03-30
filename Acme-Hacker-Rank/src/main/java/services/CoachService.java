
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CoachRepository;
import domain.Actor;
import domain.Brotherhood;
import domain.Coach;
import domain.Url;

@Service
@Transactional
public class CoachService {

	// Manage Repository

	@Autowired
	private CoachRepository		coachRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	/************************************* CRUD methods ********************************/
	public Coach create() {
		Coach result;
		Actor principal;

		result = new Coach();

		// Principal must be a Brotherhood
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Brotherhood.class, principal);

		result.setPictures(new ArrayList<Url>());

		return result;
	}

	public Coach findOne(final int id) {
		final Coach result = this.coachRepository.findOne(id);

		Assert.notNull(result);

		return result;
	}

	public Collection<Coach> findAll() {
		final Collection<Coach> result = this.coachRepository.findAll();
		//Assert.notNull(result);

		return result;
	}

	public Coach save(final Coach coach) {
		Boolean isNew = false;
		Assert.notNull(coach);
		Actor principal;

		// Principal must be a Brotherhood
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Brotherhood.class, principal);
		if (coach.getId() == 0) {
			isNew = true;
		}
		final Brotherhood bro = this.brotherhoodService.findByPrincipal();
		if (isNew) {
			final Coach saved = this.coachRepository.save(coach);
			bro.getCoaches().add(saved);
			return saved;
		} else {
			Assert.isTrue(bro.getCoaches().contains(coach));
			return this.coachRepository.save(coach);
		}

	}

	public void delete(final int coachId) {
		Assert.isTrue(coachId != 0);
		final Coach coach = this.findOne(coachId);

		Assert.notNull(coach);
		Brotherhood principal;

		// Principal must be a Brotherhood
		principal = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(principal.getCoaches().contains(coach));

		principal.getCoaches().remove(coach);

		this.coachRepository.delete(coach);

	}

	/************************************* Other business methods ********************************/

	public Collection<Coach> findByBrotherhood(final int brotherhoodId) {
		Assert.isTrue(brotherhoodId > 0);
		final Collection<Coach> result = this.coachRepository.findByBrotherhood(brotherhoodId);
		Assert.notNull(result);
		return result;
	}

}
