
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Hacker;
import domain.Message;
import domain.Position;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {

	// Manage Repository
	@Autowired
	private AdministratorRepository	adminRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationsService	configurationsService;

	@Autowired
	private MessageService			messageService;


	/*************************************
	 * CRUD methods
	 *************************************/
	public Administrator create() {
		// Initialice
		UserAccount userAccount = new UserAccount();
		Collection<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		// Set Messages
		Collection<Message> messages = new ArrayList<Message>();

		Administrator admin = new Administrator();
		admin.setUserAccount(userAccount);
		admin.setMessages(messages);

		return admin;
	}

	public Collection<Administrator> findAll() {
		final Collection<Administrator> result = this.adminRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Administrator findOne(final int adminID) {
		final Administrator result = this.adminRepository.findOne(adminID);
		Assert.notNull(result);
		return result;
	}

	public Administrator save(final Administrator admin) {
		Assert.notNull(admin);
		UserAccount userAccount;

		if (admin.getId() == 0) {
			//admin.setMessageBoxes(this.messageBoxService.createSystemMessageBox());
			if (!admin.getPhoneNumber().startsWith("+")) {
				final String countryCode = this.configurationsService.getConfiguration().getCountryCode();
				final String phoneNumer = admin.getPhoneNumber();
				admin.setPhoneNumber(countryCode.concat(phoneNumer));
			}
		} else {
			if (!admin.getPhoneNumber().startsWith("+")) {
				final String countryCode = this.configurationsService.getConfiguration().getCountryCode();
				final String phoneNumer = admin.getPhoneNumber();
				admin.setPhoneNumber(countryCode.concat(phoneNumer));
			}
			userAccount = LoginService.getPrincipal();
			Assert.isTrue(userAccount.equals(admin.getUserAccount()));
		}
		return this.adminRepository.save(admin);
	}

	public void delete(final Administrator admin) {
		Assert.notNull(admin);
		Assert.isTrue(admin.getId() != 0);
		this.adminRepository.delete(admin);
	}

	/*********************************************
	 * 
	 * Admin Dashboard Queries
	 * 
	 *********************************************/
	public Object[] query1() {
		Actor principal;

		// Check principal must be an admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		return this.adminRepository.query1();
	}

	public Object[] query2() {
		Actor principal;

		// Check principal must be an admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		return this.adminRepository.query2();
	}

	public Collection<Company> query3() {
		Actor principal;

		// Check principal must be an admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		return this.adminRepository.query3();
	}

	public Collection<Hacker> query4() {
		Actor principal;

		// Check principal must be an admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		return this.adminRepository.query4();
	}

	public Object[] query5() {
		Actor principal;

		// Check principal must be an admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		return this.adminRepository.query5();
	}

	public Collection<Position> query6a() {
		Actor principal;

		// Check principal must be an admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		Collection<Position> result = new ArrayList<Position>();
		result.addAll(this.adminRepository.query6a());
		return result;
	}
	
	
	public Collection<Position> query6b() {
		Actor principal;

		// Check principal must be an admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		Collection<Position> result = new ArrayList<Position>();
		result.addAll(this.adminRepository.query6b());
		return result;
	}

	public Object[] query7() {
		Actor principal;

		// Check principal must be an admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		return this.adminRepository.query7();
	}


	public Object[] query8() {
		Actor principal;

		// Check principal must be an admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		return this.adminRepository.query8();
	}

	public Double query9() {
		Actor principal;

		// Check principal must be an admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		return this.adminRepository.query9();
	}

	/*********************************************
	 * 
	 * 24.2 Computer Spammers
	 * 
	 *********************************************/
	public void computeSpammers() {
		Actor principal;
		Collection<Message> messages;
		int spamMessages;
		Collection<Actor> users = this.actorService.findAll();
		Collection<String> spamWords = this.configurationsService.getConfiguration().getSpamWords();

		// Make sure that the principal is an Admin
		principal = this.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		for (Actor user : users) {
			spamMessages = 0;
			messages = this.messageService.findAllBySender(user.getId());

			if ((messages != null) && !messages.isEmpty())
				for (Message message : messages)
					for (String spamWord : spamWords)
						if (message.getBody().contains(spamWord) || message.getSubject().contains(spamWord))
							spamMessages++;
			if ((spamMessages != 0) && (spamMessages >= (messages.size() * 0.1)))
				user.setIsSpammer(true);
			else
				user.setIsSpammer(false);
		}
	}


	public Collection<Actor> getSpammers() {
		Actor principal;

		// Make sure that the principal is an Admin
		principal = this.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		return this.actorService.findSpammers();
	}

	/*********************************************
	 * 
	 * 24.3 Ban an Actor
	 * 
	 *********************************************/
	public Actor banAnActor(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getIsSpammer());

		// Make sure that the principal is an Admin
		final Object principal = this.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		actor.setUsername(actor.getUserAccount().getUsername());
		actor.getUserAccount().setUsername(null);
		actor.setIsBanned(true);

		return this.actorService.save(actor);

	}

	/*********************************************
	 * 
	 * 24.4 Unban an Actor
	 * 
	 *********************************************/
	public Actor unBanAnActor(final Actor actor) {
		Assert.notNull(actor);
		// Assert.notNull(actor.getUsername());

		// Make sure that the principal is an Admin
		final Object principal = this.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		actor.getUserAccount().setUsername(actor.getUsername());
		actor.setIsBanned(false);

		return this.actorService.save(actor);

	}


	/**
	 * 
	 * Manage Spam Word ****************************************************************************
	 */

	// Add SPAM Word
	public void addSpamWord(final String word) {

		// Make sure that the principal is an Admin
		final Object principal = this.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		Assert.notNull(word);
		Assert.isTrue(word != "");
		Assert.isTrue(this.configurationsService.getConfiguration().getSpamWords().contains(word) != true);

		this.configurationsService.getConfiguration().getSpamWords().add(word);
		this.configurationsService.update(this.configurationsService.getConfiguration());
	}

	// Edit SPAM Word
	public void editSpamWord(final String word, final Integer index) {

		// Make sure that the principal is an Admin
		final Object principal = this.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		Assert.notNull(word);
		Assert.isTrue(word != "");
		Assert.notNull(index);

		Assert.isTrue(this.configurationsService.getConfiguration().getSpamWords().contains(word) != true);

		final ArrayList<String> words = new ArrayList<String>(this.configurationsService.getConfiguration().getSpamWords());
		words.set(index, word);

		this.configurationsService.getConfiguration().setSpamWords(words);
		this.configurationsService.update(this.configurationsService.getConfiguration());
	}

	// Remove SPAM Word
	public void removeSpamWord(final String word) {

		// Make sure that the principal is an Admin
		final Object principal = this.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);

		Assert.notNull(word);
		Assert.isTrue(this.configurationsService.getConfiguration().getSpamWords().contains(word));

		this.configurationsService.getConfiguration().getSpamWords().remove(word);
		this.configurationsService.update(this.configurationsService.getConfiguration());
	}





	/*************************************
	 * Other business methods
	 ********************************/
	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Administrator result;

		result = this.adminRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public void informSecurityBreach() {
		final Message message = this.messageService.create();
		message.setBody("There has been a security breach in our data system.");

		message.setIsNotification(true);
		message.setPriority("HIGH");
		message.setSubject("Security breach notification");
		Collection<Actor> recipients = new ArrayList<Actor>(this.actorService.findAll());
		message.setRecipients(recipients);

		this.messageService.save(message);
	}
}
