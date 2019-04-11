
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Company;
import domain.Message;
import domain.Position;
import domain.Problem;
import forms.CompanyForm;
import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class CompanyService {

	// Manage Repository
	@Autowired
	private CompanyRepository		companyRepository;

	@Autowired
	@Qualifier("validator")
	private Validator				validator;


	// CRUD methods
	public Company create() {
		final Company result = new Company();

		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		
		// Set Messages
		Collection<Message> messages = new ArrayList<Message>();

		result.setUserAccount(userAccount);
		result.setMessages(messages);
		result.setPositions(new ArrayList<Position>());
		result.setProblems(new ArrayList<Problem>());

		return result;
	}

	public Company findOne(final int companyId) {
		final Company result = this.companyRepository.findOne(companyId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Company> findAll() {
		final Collection<Company> result = this.companyRepository.findAll();
		Assert.notNull(result);
		Assert.notEmpty(result);

		return result;
	}

	public Company save(final Company company) {
		Assert.notNull(company);

		final Company result = this.companyRepository.save(company);

		return result;
	}

	public void delete(final Company company) {
		Assert.notNull(company);
		Assert.isTrue(this.findByPrincipal() == company);

		//		company.getArea().getCompanys().remove(company);
		//
		//		Iterator<Coach> coaches = new ArrayList<Coach>(company.getCoaches()).iterator();
		//		Iterator<Enrol> enrols = new ArrayList<Enrol>(company.getEnrols()).iterator();
		//		Iterator<Procession> processions = new ArrayList<Procession>(company.getProcessions()).iterator();
		//		Iterator<SocialIdentity> socialIs 	= new ArrayList<SocialIdentity>
		//(company.getSocialIdentities()).iterator();
		//
		//		while (coaches.hasNext()) {
		//			Coach next = coaches.next();
		//			this.coachService.delete(next.getId());
		//			company.getCoaches().remove(next);
		//			coaches.remove();
		//		}
		//		while (enrols.hasNext()) {
		//			Enrol next = enrols.next();
		//			this.enrolService.delete(next);
		//			company.getEnrols().remove(next);
		//			enrols.remove();
		//		}
		//		while (processions.hasNext()) {
		//			Procession p = processions.next();
		//			this.processionService.delete(p);
		//			company.getProcessions().remove(p);
		//			processions.remove();
		//		}
		//		while (socialIs.hasNext()) {
		//			SocialIdentity si = socialIs.next();
		//			this.socialIdentityService.delete(si);
		//			company.getSocialIdentities().remove(si);
		//			socialIs.remove();
		//		}
		//		company.setMessageBoxes(new ArrayList<MessageBox>());
		//		this.messageBoxService.deleteAll(company);

		this.companyRepository.delete(company);
	}

	/*** Reconstruct object, check validity and update binding ***/
	public Company reconstruct(final CompanyForm form, final BindingResult binding) {
		final Company company = this.create();

		company.getUserAccount().setPassword(form.getUserAccount().getPassword());
		company.getUserAccount().setUsername(form.getUserAccount().getUsername());

		company.setAddress(form.getAddress());
		company.setEmail(form.getEmail());
		company.setName(form.getName());
		company.setPhoneNumber(form.getPhoneNumber());
		company.setPhoto(form.getPhoto());
		company.setSurname(form.getSurname());
		company.setVat(form.getVat());
		company.setCardNumber(form.getCardNumber());
		company.setCommercialName(form.getCommercialName());

		// Default attributes from Actor
		company.setIsSpammer(false);
		company.setIsBanned(false);

		this.validator.validate(company, binding);

		return company;
	}

	public Company reconstruct(final Company company, final BindingResult binding) {
		final Company result = this.create();
		final Company temp = this.findOne(company.getId());

		Assert.isTrue(this.findByPrincipal().getId() == company.getId());

		// Updated attributes
		result.setAddress(company.getAddress());
		result.setEmail(company.getEmail());
		result.setName(company.getName());
		result.setPhoneNumber(company.getPhoneNumber());
		result.setPhoto(company.getPhoto());
		result.setSurname(company.getSurname());
		result.setVat(company.getVat());
		result.setCardNumber(company.getCardNumber());
		result.setCommercialName(company.getCommercialName());

		// Not updated attributes
		result.setId(temp.getId());
		result.setVersion(temp.getVersion());
		result.setIsSpammer(temp.getIsSpammer());
		result.setIsBanned(temp.getIsBanned());
		

		// Relantionships
		result.setPositions(temp.getPositions());
		result.setProblems(temp.getProblems());
		result.setSocialIdentities(temp.getSocialIdentities());
		result.setUserAccount(temp.getUserAccount());
		
		this.validator.validate(result, binding);

		return result;
	}

	/************************************************************************************************/

	// Other business methods
	public Company findByPrincipal() {
		Company result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Company findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Company result;

		result = this.companyRepository.findByUserAccountId(userAccount.getId());

		return result;
	}
}
