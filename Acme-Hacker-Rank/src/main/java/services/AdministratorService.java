
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
	
	@Autowired
	private ActorService			actorService;

	// Supporting services
	@Autowired
	private ConfigurationsService	configurationsService;


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
	
	public Collection<Position> query6() {
		Actor principal;
		
		// Check principal must be an admin
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Administrator.class, principal);
		
		Collection<Position> result = new ArrayList<Position>();
		result.add(this.adminRepository.query6a());
		result.add(this.adminRepository.query6b());
		
		return result;
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
}
