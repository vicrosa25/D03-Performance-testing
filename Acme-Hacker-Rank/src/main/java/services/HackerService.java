
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Hacker;
import forms.HackerForm;
import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class HackerService {

	// Manage Repository
	private HackerRepository	hackerRepository;

	// Supporting services

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
		authority.setAuthority(Authority.ADMIN);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		hacker.setUserAccount(userAccount);
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

	public Hacker save(final Hacker hacker) {
		Assert.notNull(hacker);
		final Hacker result = this.hackerRepository.save(hacker);

		return result;
	}

	public void delete(final Hacker hacker) {
		Assert.notNull(hacker);

		this.hackerRepository.delete(hacker);
	}

	/*************************************
	 * Reconstruct object, check validity and update binding
	 *************************************/

	/** Form Object **/
	public Hacker reconstruct(HackerForm hackerForm, BindingResult binding) {
		Hacker result = new Hacker();
		try {
			result = this.findByPrincipal();
		} catch (final IllegalArgumentException a) {
			result = this.create();
		}

		result.getUserAccount().setPassword(hackerForm.getUserAccount().getPassword());
		result.getUserAccount().setUsername(hackerForm.getUserAccount().getUsername());
		result.setUsername(hackerForm.getUserAccount().getUsername());

		result.setAddress(hackerForm.getAddress());
		result.setEmail(hackerForm.getEmail());
		result.setName(hackerForm.getName());
		result.setPhoneNumber(hackerForm.getPhoneNumber());
		result.setPhoto(hackerForm.getPhoto());

		result.setSurname(hackerForm.getSurname());

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
		result.setAddress(hacker.getAddress());
		result.setEmail(hacker.getEmail());
		result.setName(hacker.getName());
		result.setPhoneNumber(hacker.getPhoneNumber());
		result.setPhoto(hacker.getPhoto());
		result.setSurname(hacker.getSurname());
		result.setName(hacker.getName());

		// Not updated attributes
		result.setId(temp.getId());
		result.setVersion(temp.getVersion());
		result.setUsername(temp.getUsername());
		result.setIsSpammer(temp.getIsSpammer());
		result.setIsBanned(temp.getIsBanned());

		// Relantionships from Hacker

		// Relatienships from Actor
		result.setUserAccount(temp.getUserAccount());
		//result.setMessageBoxes(temp.getMessageBoxes());
		result.setSocialIdentities(temp.getSocialIdentities());

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
}
