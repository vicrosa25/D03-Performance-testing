
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

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.MessageBox;
import domain.SocialIdentity;
import domain.Sponsor;
import domain.Sponsorship;
import forms.SponsorForm;

@Service
@Transactional
public class SponsorService {

	// Manage Repository
	@Autowired
	private SponsorRepository		sponsorRepository;

	// Supporting services
	@Autowired
	MessageBoxService				messageBoxService;

	@Autowired
	private ConfigurationsService	configurationsService;

	@Autowired
	private SocialIdentityService	socialIdentityService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private Validator				validator;


	// CRUD methods
	public Sponsor create() {
		final Sponsor result = new Sponsor();

		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result.setSponsorships(new ArrayList<Sponsorship>());
		result.setMessageBoxes(new ArrayList<MessageBox>());
		result.setSocialIdentities(new ArrayList<SocialIdentity>());
		result.setUserAccount(userAccount);
		result.setUsername(result.getUserAccount().getUsername());

		return result;
	}

	public Sponsor findOne(final int sponsorID) {
		final Sponsor result = this.sponsorRepository.findOne(sponsorID);
		Assert.notNull(result);

		return result;
	}

	public Collection<Sponsor> findAll() {
		final Collection<Sponsor> result = this.sponsorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		final Sponsor result;
		final UserAccount userAccount;

		if (sponsor.getId() == 0) {
			sponsor.setMessageBoxes(this.messageBoxService.createSystemMessageBox());
			if (!sponsor.getPhoneNumber().startsWith("+")) {
				final String countryCode = this.configurationsService.getConfiguration().getCountryCode();
				final String phoneNumer = sponsor.getPhoneNumber();
				sponsor.setPhoneNumber(countryCode.concat(phoneNumer));
			}
			result = this.sponsorRepository.save(sponsor);
		} else {
			userAccount = LoginService.getPrincipal();
			Assert.isTrue(userAccount.equals(sponsor.getUserAccount()));
			if (!sponsor.getPhoneNumber().startsWith("+")) {
				final String countryCode = this.configurationsService.getConfiguration().getCountryCode();
				final String phoneNumer = sponsor.getPhoneNumber();
				sponsor.setPhoneNumber(countryCode.concat(phoneNumer));
			}
			result = this.sponsorRepository.save(sponsor);
		}

		return result;
	}

	public void delete(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(this.findByPrincipal() == sponsor);

		Iterator<Sponsorship> sponsorships = new ArrayList<Sponsorship>(sponsor.getSponsorships()).iterator();
		Iterator<SocialIdentity> socialIs = new ArrayList<SocialIdentity>(sponsor.getSocialIdentities()).iterator();

		while (sponsorships.hasNext()) {
			Sponsorship ss = sponsorships.next();
			this.sponsorshipService.delete(ss);
			sponsor.getSocialIdentities().remove(ss);
			sponsorships.remove();
		}

		while (socialIs.hasNext()) {
			SocialIdentity si = socialIs.next();
			this.socialIdentityService.delete(si);
			sponsor.getSocialIdentities().remove(si);
			socialIs.remove();
		}
		sponsor.setMessageBoxes(new ArrayList<MessageBox>());
		this.messageBoxService.deleteAll(sponsor);

		this.sponsorRepository.delete(sponsor);
	}

	/******************************************************************
	 * Reconstruct form object, check validity and update binding
	 ***************************************************************/
	public Sponsor reconstruct(SponsorForm form, BindingResult binding) {
		Sponsor sponsor = this.create();

		sponsor.getUserAccount().setPassword(form.getUserAccount().getPassword());
		sponsor.getUserAccount().setUsername(form.getUserAccount().getUsername());

		sponsor.setAddress(form.getAddress());
		sponsor.setEmail(form.getEmail());
		sponsor.setMiddleName(form.getMiddleName());
		sponsor.setName(form.getName());
		sponsor.setPhoneNumber(form.getPhoneNumber());
		sponsor.setPhoto(form.getPhoto());
		sponsor.setSurname(form.getSurname());

		// Default attributes from Actor
		sponsor.setUsername(form.getUserAccount().getUsername());
		sponsor.setIsBanned(false);

		this.validator.validate(sponsor, binding);

		return sponsor;
	}

	/******************************************************************
	 * Reconstruct pruned object
	 ***************************************************************/
	public Sponsor reconstruct(Sponsor prune, BindingResult binding) {
		Sponsor result = this.create();
		Sponsor temp = this.findOne(prune.getId());

		Assert.isTrue(this.findByPrincipal().getId() == prune.getId());

		// Updated attributes
		result.setAddress(prune.getAddress());
		result.setEmail(prune.getEmail());
		result.setMiddleName(prune.getMiddleName());
		result.setName(prune.getName());
		result.setPhoneNumber(prune.getPhoneNumber());
		result.setPhoto(prune.getPhoto());
		result.setSurname(prune.getSurname());
		result.setName(prune.getName());

		// Not updated attributes
		result.setId(temp.getId());
		result.setVersion(temp.getVersion());
		result.setUsername(temp.getUsername());
		result.setIsSpammer(temp.getIsSpammer());
		result.setIsBanned(temp.getIsBanned());
		result.setScore(temp.getScore());

		// Relationships from Sponsor
		result.setSponsorships(temp.getSponsorships());
		
		// Relantionships from Actor
		result.setUserAccount(temp.getUserAccount());
		result.setSocialIdentities(temp.getSocialIdentities());
		result.setMessageBoxes(temp.getMessageBoxes());
		

		this.validator.validate(result, binding);

		return result;
	}

	// Other business methods
	public Sponsor findByPrincipal() {
		Sponsor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Sponsor result;

		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

}
