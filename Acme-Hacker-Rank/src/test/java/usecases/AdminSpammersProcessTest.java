package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AdministratorService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdminSpammersProcessTest extends AbstractTest {
	
	// System under test ---------------------------------------------------------------------------
	@Autowired
	private AdministratorService 	administratorService;
	
	
	// Tests -----------------------------------------------------------------------------------------
	
	
	/**
	 * Requirement: An actor who is authenticated must be able to:  
	 * 
	 * 		"Launch a process that flags the actors of the system as spammers or not-spammers."
	 *  
	 * 	1. Positive test.
	 *  
	 **/
	@Test
	public void deleteMessagePositive(){
		
		super.authenticate("admin");
		
		this.administratorService.computeSpammers();
		
		super.unauthenticate();
		
	}

	
	/**
	 * Requirement: An actor who is authenticated must be able to:  
	 * 
	 * 		"Launch a process that flags the actors of the system as spammers or not-spammers."
	 *  
	 * 1. Negative test.
	 * 2. Business rule that is intended to broke: The actor is not authenticated
	 *  
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void deleteMessageNegativeTwo(){
		
		super.authenticate(null);
		
		this.administratorService.computeSpammers();
		
		super.unauthenticate();
	}
}
