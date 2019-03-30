
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Area;
import domain.Url;
import repositories.AreaRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AreaService {

	// Manage Repository
	@Autowired
	private AreaRepository		areaRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	/**
	 * CRUD methods
	 */
	public Area create() {
		final Area result = new Area();
		final Collection<Url> pictures = new ArrayList<Url>();
		result.setPictures(pictures);

		return result;
	}

	public Area findOne(final int id) {
		final Area result = this.areaRepository.findOne(id);
		Assert.notNull(result);

		return result;
	}

	public Collection<Area> findAll() {
		final Collection<Area> result = this.areaRepository.findAll();

		//Assert.notNull(result);

		return result;
	}

	public Area save(Area area) {
		Assert.notNull(area);
		Area result;

		//Principal must be an Admin or Chapter
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		Authority adminAuthority = new Authority();
		adminAuthority.setAuthority("ADMIN");

		Authority chapterAuthority = new Authority();
		chapterAuthority.setAuthority("CHAPTER");
		
		Assert.isTrue(userAccount.getAuthorities().contains(adminAuthority) || userAccount.getAuthorities().contains(chapterAuthority));
		

		result = this.areaRepository.save(area);
		return result;
	}

	public void delete(final Area area) {
		Assert.notNull(area);
		Actor principal;

		// Principal must be an Admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);
		Assert.isTrue(area.getId() != 0);

		// Posistion must not be in use to be deleted
		Assert.isNull(this.brotherhoodService.findBrotherhoodByArea(area));

		this.areaRepository.delete(area);
	}
	
	/**
	 * Ancillary methods
	 */
	public Collection<Area> findFreeAreas() {
		
		return this.areaRepository.findAllFreeAreas();
	}
}
