
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Company;
import domain.Message;
import domain.Position;
import domain.Problem;
import domain.SocialProfile;
import forms.CompanyForm;

@Service
@Transactional
public class CompanyService {

	// Manage Repository
	@Autowired
	private CompanyRepository		companyRepository;

	// Supporting services
	@Autowired
	private MessageService		messageService;
	@Autowired
	private PositionService			positionService;
	@Autowired
	private ProblemService			problemService;
	@Autowired
	private SocialProfileService	socialProfileService;

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

		Iterator<Message> messages = new ArrayList<Message>(company.getMessages()).iterator();
		Iterator<Position> positions = new ArrayList<Position>(company.getPositions()).iterator();
		Iterator<Problem> problems = new ArrayList<Problem>(company.getProblems()).iterator();
		Iterator<SocialProfile> socialIs 	= new ArrayList<SocialProfile>
			(company.getSocialProfiles()).iterator();

		while (messages.hasNext()) {
			Message next = messages.next();
			if (next.getSender() == company)
				next.setSender(null);
			next.getRecipients().remove(company);
			this.messageService.save(next);
			company.getMessages().remove(next);
			messages.remove();
		}
		while (positions.hasNext()) {
			Position next = positions.next();
			this.positionService.forceDelete(next);
			company.getPositions().remove(next);
			positions.remove();
		}
		while (problems.hasNext()) {
			Problem p = problems.next();
			this.problemService.delete(p);
			company.getProblems().remove(p);
			problems.remove();
		}
		while (socialIs.hasNext()) {
			SocialProfile si = socialIs.next();
			this.socialProfileService.delete(si);
			company.getSocialProfiles().remove(si);
			socialIs.remove();
		}

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
		result.setSocialProfiles(temp.getSocialProfiles());
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
