
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Finder;
import domain.Hacker;
import domain.Message;
import domain.SocialProfile;
import forms.HackerForm;

@Service
@Transactional
public class HackerService {

	// Manage Repository
	@Autowired
	private HackerRepository		hackerRepository;

	// Supporting services
	@Autowired
	private ApplicationService 		applicationService;
	
	@Autowired
	private SocialProfileService 	socialIdentityService;
	
	@Autowired
	private FinderService			finderService;

	// Validator
	@Autowired
	private Validator			validator;


	/*************************************
	 * CRUD methods
	 *************************************/
	public Hacker create() {

		Hacker hacker = new Hacker();

		// Initialice
		UserAccount userAccount = new UserAccount();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority(Authority.HACKER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		Finder finder = this.finderService.create();
		
		// Set Messages
		Collection<Message> messages = new ArrayList<Message>();

		hacker.setUserAccount(userAccount);
		hacker.setMessages(messages);
		hacker.setFinder(finder);
		hacker.setIsSpammer(false);

		return hacker;

	}

	public Collection<Hacker> findAll() {
		final Collection<Hacker> result = this.hackerRepository.findAll();
		Assert.notNull(result);
		Assert.notEmpty(result);

		return result;
	}

	public Hacker findOne(int adminID) {
		Hacker result = this.hackerRepository.findOne(adminID);
		Assert.notNull(result);
		return result;
	}

	public Hacker save(Hacker hacker) {
		Assert.notNull(hacker);
		Hacker result = this.hackerRepository.save(hacker);

		return result;
	}

	public void delete(Hacker hacker) {
		Assert.isTrue(this.findByPrincipal() == hacker);
		Assert.notNull(hacker);

		Iterator<Application> applications	= new ArrayList<Application>(hacker.getApplications()).iterator();
		Iterator<SocialProfile> socialIs 	= new ArrayList<SocialProfile>(hacker.getSocialProfiles()).iterator();

		while (applications.hasNext()) {
			Application next = applications.next();
			this.applicationService.delete(next.getId());
			hacker.getApplications().remove(next);
			applications.remove();
		}
	
		while (socialIs.hasNext()) {
			SocialProfile si = socialIs.next();
			this.socialIdentityService.delete(si);
			hacker.getSocialProfiles().remove(si);
			socialIs.remove();
		}
		
//		hacker.setMessageBoxes(new ArrayList<MessageBox>());
//		this.messageBoxService.deleteAll(hacker);
		
		this.hackerRepository.delete(hacker);

	}

	/**************************************************************
	 * Reconstruct object, check validity and update binding
	 **************************************************************/

	/** Form Object **/
	public Hacker reconstruct(HackerForm hackerForm, BindingResult binding) {
		Hacker result = this.create();

		// UserAccount
		result.getUserAccount().setPassword(hackerForm.getUserAccount().getPassword());
		result.getUserAccount().setUsername(hackerForm.getUserAccount().getUsername());

		// Hacker Attributes
		result.setName(hackerForm.getName());
		result.setSurname(hackerForm.getSurname());
		result.setVat(hackerForm.getVat());
		result.setCardNumber(hackerForm.getCardNumber());
		result.setPhoto(hackerForm.getPhoto());
		result.setEmail(hackerForm.getEmail());
		result.setPhoneNumber(hackerForm.getPhoneNumber());
		result.setAddress(hackerForm.getAddress());

		// Default attributes from Actor
		result.setUsername(hackerForm.getUserAccount().getUsername());
		result.setIsBanned(false);

		this.validator.validate(result, binding);

		return result;
	}

	/** Pruned Object **/
	public Hacker reconstruct(Hacker hacker, BindingResult binding) {
		Hacker result = this.create();
		Hacker temp = this.findOne(hacker.getId());

		// Check the principal is updating his own data.
		Assert.isTrue(this.findByPrincipal().getId() == hacker.getId());

		// Updated attributes
		result.setName(hacker.getName());
		result.setSurname(hacker.getSurname());
		result.setVat(hacker.getVat());
		result.setCardNumber(hacker.getCardNumber());
		result.setPhoto(hacker.getPhoto());
		result.setEmail(hacker.getEmail());
		result.setPhoneNumber(hacker.getPhoneNumber());
		result.setAddress(hacker.getAddress());

		// Not updated attributes
		result.setId(temp.getId());
		result.setVersion(temp.getVersion());
		result.setUsername(temp.getUsername());
		result.setIsSpammer(temp.getIsSpammer());
		result.setIsBanned(temp.getIsBanned());

		// Relantionships from Hacker
		result.setApplications(temp.getApplications());
		result.setFinder(temp.getFinder());

		// Relatienships from Actor
		result.setUserAccount(temp.getUserAccount());
		result.setSocialProfiles(temp.getSocialProfiles());

		this.validator.validate(result, binding);

		return result;
	}

	/*************************************
	 * Other business methods
	 *************************************/
	public Hacker findByPrincipal() {
		Hacker result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Hacker findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Hacker result;

		result = this.hackerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Hacker findOneByUsername(final String username) {
		Assert.notNull(username);

		return this.hackerRepository.findByUserName(username);
	}

	public Hacker findByFinder(final Finder finder) {
		Assert.notNull(finder);

		return this.hackerRepository.findByFinder(finder.getId());
	}
}
