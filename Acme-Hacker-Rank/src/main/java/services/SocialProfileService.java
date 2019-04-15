
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.SocialProfile;
import repositories.SocialProfileRepository;

@Service
@Transactional
public class SocialProfileService {

	// Manage Repository
	@Autowired
	private SocialProfileRepository		socialProfileRepository;

	// Supporting services
	@Autowired
	private ActorService				actorService;


	// CRUD methods
	public SocialProfile create() {
		final SocialProfile result = new SocialProfile();

		return result;
	}

	public SocialProfile findOne(int socialProfileID) {
		final SocialProfile result = this.socialProfileRepository.findOne(socialProfileID);
		Assert.notNull(result);

		return result;
	}

	public Collection<SocialProfile> findAll() {
		final Collection<SocialProfile> result = this.socialProfileRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SocialProfile save(SocialProfile socialProfile) {
		Assert.notNull(socialProfile);
		SocialProfile saved;
		
		Actor principal = this.actorService.findByPrincipal();

		if (socialProfile.getId() == 0) {
			socialProfile.setActor(principal);
			saved = this.socialProfileRepository.save(socialProfile);
		} else {
			Assert.isTrue(this.socialProfileRepository.findByActor(principal.getId()).contains(socialProfile));
			saved = this.update(socialProfile);
		}
		return saved;
	}
	
	public SocialProfile update(SocialProfile socialProfile) {
		Assert.notNull(socialProfile);

		return this.socialProfileRepository.save(socialProfile);
	}

	
	
	public void delete(SocialProfile socialProfile) {
		
		Actor principal = this.actorService.findByPrincipal();
		
		Assert.notNull(principal);
		Assert.isTrue(this.socialProfileRepository.findByActor(principal.getId()).contains(socialProfile));
		
		principal.getSocialProfiles().remove(socialProfile);
		
		this.socialProfileRepository.delete(socialProfile);
	}

	// Other methods

	public Collection<SocialProfile> findAllByActor(int actorID) {
		return this.socialProfileRepository.findByActor(actorID);
	}
}
