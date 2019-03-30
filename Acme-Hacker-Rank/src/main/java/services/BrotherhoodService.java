
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Area;
import domain.Brotherhood;
import domain.Coach;
import domain.Enrol;
import domain.Member;
import domain.MessageBox;
import domain.Procession;
import domain.SocialIdentity;
import domain.Url;
import forms.BrotherhoodForm;

@Service
@Transactional
public class BrotherhoodService {

	// Manage Repository
	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private CoachService			coachService;

	@Autowired
	private EnrolService			enrolService;

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private SocialIdentityService	socialIdentityService;

	@Autowired
	@Qualifier("validator")
	private Validator				validator;


	// CRUD methods
	public Brotherhood create() {
		final Brotherhood result = new Brotherhood();

		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		final Collection<MessageBox> boxes = this.messageBoxService.createSystemMessageBox();

		result.setUserAccount(userAccount);
		result.setEstablishment(new Date());
		result.setPictures(new ArrayList<Url>());
		result.setCoaches(new ArrayList<Coach>());
		result.setEnrols(new ArrayList<Enrol>());
		result.setProcessions(new ArrayList<Procession>());
		result.setMessageBoxes(boxes);

		return result;
	}

	public Brotherhood findOne(final int brotherhoodId) {
		final Brotherhood result = this.brotherhoodRepository.findOne(brotherhoodId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Brotherhood> findAll() {
		final Collection<Brotherhood> result = this.brotherhoodRepository.findAll();
		Assert.notNull(result);
		Assert.notEmpty(result);

		return result;
	}

	public Brotherhood save(final Brotherhood brotherhood) {
		Assert.notNull(brotherhood);
		if (brotherhood.getId() == 0) {
			brotherhood.setHistory(this.historyService.save(brotherhood.getHistory()));
		}
		final Brotherhood result = this.brotherhoodRepository.save(brotherhood);

		return result;
	}

	public void delete(final Brotherhood brotherhood) {
		Assert.notNull(brotherhood);
		Assert.isTrue(this.findByPrincipal() == brotherhood);

		brotherhood.getArea().getBrotherhoods().remove(brotherhood);

		Iterator<Coach> coaches 			= new ArrayList<Coach>(brotherhood.getCoaches()).iterator();
		Iterator<Enrol> enrols 				= new ArrayList<Enrol>(brotherhood.getEnrols()).iterator();
		Iterator<Procession> processions 	= new ArrayList<Procession>(brotherhood.getProcessions()).iterator();
		Iterator<SocialIdentity> socialIs 	= new ArrayList<SocialIdentity>
												(brotherhood.getSocialIdentities()).iterator();

		while (coaches.hasNext()) {
			Coach next = coaches.next();
			this.coachService.delete(next.getId());
			brotherhood.getCoaches().remove(next);
			coaches.remove();
		}
		while (enrols.hasNext()) {
			Enrol next = enrols.next();
			this.enrolService.delete(next);
			brotherhood.getEnrols().remove(next);
			enrols.remove();
		}
		while (processions.hasNext()) {
			Procession p = processions.next();
			this.processionService.delete(p);
			brotherhood.getProcessions().remove(p);
			processions.remove();
		}
		while (socialIs.hasNext()) {
			SocialIdentity si = socialIs.next();
			this.socialIdentityService.delete(si);
			brotherhood.getSocialIdentities().remove(si);
			socialIs.remove();
		}
		brotherhood.setMessageBoxes(new ArrayList<MessageBox>());
		this.messageBoxService.deleteAll(brotherhood);

		this.brotherhoodRepository.delete(brotherhood);
	}

	/*** Reconstruct object, check validity and update binding ***/
	public Brotherhood reconstruct(final BrotherhoodForm form, final BindingResult binding) {
		final Brotherhood bro = this.create();

		bro.getUserAccount().setPassword(form.getUserAccount().getPassword());
		bro.getUserAccount().setUsername(form.getUserAccount().getUsername());

		bro.setAddress(form.getAddress());
		bro.setEmail(form.getEmail());
		bro.setMiddleName(form.getMiddlename());
		bro.setName(form.getName());
		bro.setPhoneNumber(form.getPhoneNumber());
		bro.setPhoto(form.getPhoto());
		bro.setSurname(form.getSurname());
		bro.setTitle(form.getTitle());
		bro.setArea(form.getArea());
		bro.getEstablishment().setTime(bro.getEstablishment().getTime() - 1000);
		bro.setHistory(this.historyService.create());

		// Default attributes from Actor
		bro.setUsername(form.getUserAccount().getUsername());
		//bro.setIsSpammer(false);
		bro.setIsBanned(false);

		this.validator.validate(bro, binding);

		return bro;
	}

	public Brotherhood reconstruct(final Brotherhood brotherhood, final BindingResult binding) {
		final Brotherhood result = this.create();
		final Brotherhood temp = this.findOne(brotherhood.getId());

		Assert.isTrue(this.findByPrincipal().getId() == brotherhood.getId());

		// Updated attributes
		result.setAddress(brotherhood.getAddress());
		result.setEmail(brotherhood.getEmail());
		result.setMiddleName(brotherhood.getMiddleName());
		result.setName(brotherhood.getName());
		result.setPhoneNumber(brotherhood.getPhoneNumber());
		result.setPhoto(brotherhood.getPhoto());
		result.setSurname(brotherhood.getSurname());
		result.setTitle(brotherhood.getTitle());

		// Not updated attributes
		result.setId(temp.getId());
		result.setVersion(temp.getVersion());
		result.setUsername(temp.getUsername());
		result.setIsSpammer(temp.getIsSpammer());
		result.setIsBanned(temp.getIsBanned());
		result.setScore(temp.getScore());
		

		// Relantionships
		result.setCoaches(temp.getCoaches());
		result.setEnrols(temp.getEnrols());
		result.setEstablishment(temp.getEstablishment());
		result.setPictures(temp.getPictures());
		result.setProcessions(temp.getProcessions());
		result.setUserAccount(temp.getUserAccount());
		result.setArea(temp.getArea());
		result.setSocialIdentities(temp.getSocialIdentities());
		result.setHistory(temp.getHistory());
		
		this.validator.validate(result, binding);

		return result;
	}

	/************************************************************************************************/

	// Other business methods
	public Brotherhood findByPrincipal() {
		Brotherhood result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Brotherhood findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Brotherhood result;

		result = this.brotherhoodRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Brotherhood findOneByUsername(final String username) {
		Assert.notNull(username);

		return this.brotherhoodRepository.findByUserName(username);
	}

	public Collection<Brotherhood> findAllByMember(final Member member) {
		final Collection<Brotherhood> result = new ArrayList<Brotherhood>();

		result.addAll(this.findAllMemberBelongs(member));
		result.addAll(this.findAllMemberBelonged(member));

		return result;
	}

	public Collection<Brotherhood> findAllMemberBelongs(final Member member) {
		final Collection<Brotherhood> bros = this.brotherhoodRepository.findBrotherhoodsMemberBelongs(member.getId());

		Assert.notNull(bros);

		return bros;
	}

	public Collection<Brotherhood> findAllMemberBelonged(final Member member) {
		final Collection<Brotherhood> bros = this.brotherhoodRepository.findBrotherhoodsMemberHashBelong(member.getId());

		Assert.notNull(bros);

		return bros;
	}

	public Brotherhood findBrotherhoodByArea(final Area area) {
		return this.brotherhoodRepository.findBrotherhoodByArea(area.getId());
	}

	public Collection<Brotherhood> findAllNotEnroller(final Member member) {
		final Collection<Brotherhood> result = this.findAll();
		result.removeAll(this.brotherhoodRepository.findBrotherhoodsMemberBelongs(member.getId()));

		Assert.notNull(result);

		return result;
	}

	public Brotherhood findByCoach(Coach coach) {
		Brotherhood result = this.brotherhoodRepository.findByCoach(coach.getId());
		Assert.notNull(result);
		return result;
	}
}
