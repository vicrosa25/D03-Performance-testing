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
public class deactivateSponsorshipsProcessTest extends AbstractTest {
	
	// System under test ---------------------------------------------------------------------------
	@Autowired
	private AdministratorService 	adminService;
	
	
	// Tests -----------------------------------------------------------------------------------------
	
	
	/**
	 * Requirement: Launch a process that automatically de-activates the sponsorships whose credit
					cards have expired.
	 *  
	 * 	1. Positive test.
	 *  
	 **/
	@Test
	public void deactivatePositive(){
		super.authenticate("admin");
		this.adminService.deactivateSponsorships();
		super.unauthenticate();
	}
	
	
	/**
	 * Requirement: Launch a process that automatically de-activates the sponsorships whose credit
					cards have expired.
	 *  
	 * 1. Negative test.
	 * 2. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 *  
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void selfAssignaAnAreaNegative(){
		super.authenticate(null);
		this.adminService.deactivateSponsorships();
		super.unauthenticate();
	}
	

}
