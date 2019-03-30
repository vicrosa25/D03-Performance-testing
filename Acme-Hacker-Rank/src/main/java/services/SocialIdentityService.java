
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	// Manage Repository
	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	// Supporting services
	@Autowired
	private ActorService				actorService;


	// CRUD methods
	public SocialIdentity create() {
		final SocialIdentity result = new SocialIdentity();

		return result;
	}

	public SocialIdentity findOne(final int socialIdentityID) {
		final SocialIdentity result = this.socialIdentityRepository.findOne(socialIdentityID);
		Assert.notNull(result);

		return result;
	}

	public Collection<SocialIdentity> findAll() {
		final Collection<SocialIdentity> result = this.socialIdentityRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SocialIdentity save(SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);
		SocialIdentity saved;
		
		Actor principal = this.actorService.findByPrincipal();

		if (socialIdentity.getId() == 0) {
			socialIdentity.setActor(principal);
			saved = this.socialIdentityRepository.save(socialIdentity);
		} else {
			Assert.isTrue(this.socialIdentityRepository.findByActor(principal.getId()).contains(socialIdentity));
			saved = this.update(socialIdentity);
		}
		return saved;
	}
	
	public SocialIdentity update(final SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);

		return this.socialIdentityRepository.save(socialIdentity);
	}

	
	
	public void delete(final SocialIdentity socialIdentity) {
		
		Actor principal = this.actorService.findByPrincipal();
		
		Assert.notNull(principal);
		Assert.isTrue(this.socialIdentityRepository.findByActor(principal.getId()).contains(socialIdentity));
		
		principal.getSocialIdentities().remove(socialIdentity);
		
		this.socialIdentityRepository.delete(socialIdentity);
	}

	// Other methods

	public Collection<SocialIdentity> findAllByActor(final int actorID) {
		return this.socialIdentityRepository.findByActor(actorID);
	}
}
