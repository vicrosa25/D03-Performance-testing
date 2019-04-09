
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
public class AdminDashBoardTest extends AbstractTest {

	// System under test ---------------------------------------------------------------------------
	@Autowired
	private AdministratorService adminService;


	/********************************* Dashboard Level C **************************************************/
	// Tests Query1-----------------------------------------------------------------------------------------
	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 1: The average, the minimum, the maximum, and the standard deviation of the number
	 * of  positions per company
	 * 
	 * 2. Positive test.
	 * 
	 **/
	@Test
	public void query1Positive() {
		super.authenticate("admin");
		this.adminService.query1();
		super.unauthenticate();
	}

	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 1: The average, the minimum, the maximum, and the standard deviation of the number
	 * of  positions per company
	 * 
	 * 2. Negative test.
	 * 3. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 * 
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void query1Negative() {
		super.authenticate(null);
		this.adminService.query1();
		super.unauthenticate();
	}

	// Tests Query2-----------------------------------------------------------------------------------------
	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 2: The average, the minimum, the maximum, and the standard deviation of the
	 *			   number of applications per hacker
	 * 
	 * 2. Positive test.
	 * 
	 **/
	@Test
	public void query2Positive() {
		super.authenticate("admin");
		this.adminService.query2();
		super.unauthenticate();
	}

	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 2: The average, the minimum, the maximum, and the standard deviation of the
	 *			   number of applications per hacker
	 *
	 * 2. Negative test.
	 * 3. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 * 
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void query2Negative() {
		super.authenticate(null);
		this.adminService.query2();
		super.unauthenticate();
	}

	// Tests Query3-----------------------------------------------------------------------------------------
	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 3: The companies that have offered more positions.
	 * 
	 * 2. Positive test.
	 * 
	 **/
	@Test
	public void query3Positive() {
		super.authenticate("admin");
		this.adminService.query3();
		super.unauthenticate();
	}

	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 3: The companies that have offered more positions.
	 * 
	 * 2. Negative test.
	 * 3. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 * 
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void query3Negative() {
		super.authenticate(null);
		this.adminService.query3();
		super.unauthenticate();
	}

	// Tests Query4-----------------------------------------------------------------------------------------
	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 4: The hackers who have made more applications.
	 * 
	 * 2. Positive test.
	 * 
	 **/
	@Test
	public void query4Positive() {
		super.authenticate("admin");
		this.adminService.query4();
		super.unauthenticate();
	}

	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 4: The hackers who have made more applications.
	 * 
	 * 2. Negative test.
	 * 3. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 * 
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void query4Negative() {
		super.authenticate(null);
		this.adminService.query4();
		super.unauthenticate();
	}

	// Tests Query5-----------------------------------------------------------------------------------------
	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 5: The average, the minimum, the maximum, and the standard deviation of the 
	 *             the salaries offered.
	 * 
	 * 2. Positive test.
	 * 
	 **/
	@Test
	public void query5Positive() {
		super.authenticate("admin");
		this.adminService.query5();
		super.unauthenticate();
	}

	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 5: The average, the minimum, the maximum, and the standard deviation of the 
	 *             the salaries offered.
	 * 
	 * 2. Negative test.
	 * 3. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 * 
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void query5Negative() {
		super.authenticate(null);
		this.adminService.query5();
		super.unauthenticate();
	}

	// Tests Query6-----------------------------------------------------------------------------------------
	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 6: The best and the worst position in terms of salary
	 * 
	 * 2. Positive test.
	 * 
	 **/
	@Test
	public void query6Positive() {
		super.authenticate("admin");
		this.adminService.query6();
		super.unauthenticate();
	}

	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 6: The best and the worst position in terms of salary
	 * 
	 * 2. Negative test.
	 * 3. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 * 
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void query6Negative() {
		super.authenticate(null);
		this.adminService.query6();
		super.unauthenticate();
	}

	// Tests Query7-----------------------------------------------------------------------------------------
	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 7: The ratio of parades in draft mode versus parades in final mode.
	 * 
	 * 2. Positive test.
	 * 
	 **/
	@Test
	public void query7Positive() {
		super.authenticate("admin");
		//this.adminService.query1();
		super.unauthenticate();
	}

	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 7: The ratio of parades in draft mode versus parades in final mode.
	 * 
	 * 2. Negative test.
	 * 3. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 * 
	 **/
	@Test
	public void query7Negative() {
		super.authenticate(null);
		//this.adminService.query1();
		super.unauthenticate();
	}

	// Tests Query8-----------------------------------------------------------------------------------------
	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 8: The ratio of parades in final mode grouped by status.
	 * 
	 * 2. Positive test.
	 * 
	 **/
	@Test
	public void query8Positive() {
		super.authenticate("admin");
		//this.adminService.query1();
		super.unauthenticate();
	}

	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query8: The ratio of parades in final mode grouped by status.
	 * 
	 * 2. Negative test.
	 * 3. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 * 
	 **/
	@Test
	public void query8Negative() {
		super.authenticate(null);
		//this.adminService.query1();
		super.unauthenticate();
	}

	/********************************* Dashboard Level A **************************************************/
	// Tests Query9-----------------------------------------------------------------------------------------
	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 9: The ratio of active sponsorships.
	 * 
	 * 2. Positive test.
	 * 
	 **/
	@Test
	public void query9Positive() {
		super.authenticate("admin");
		//this.adminService.query1();
		super.unauthenticate();
	}

	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 9: The ratio of active sponsorships.
	 * 
	 * 2. Negative test.
	 * 3. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 * 
	 **/
	@Test
	public void query9Negative() {
		super.authenticate(null);
		//this.adminService.query1();
		super.unauthenticate();
	}

	// Tests Query10-----------------------------------------------------------------------------------------
	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 10: The average, the minimum, the maximum, and the standard deviation of active sponsorships
	 * per sponsor
	 * 
	 * 2. Positive test.
	 * 
	 **/
	@Test
	public void query10Positive() {
		super.authenticate("admin");
		//this.adminService.query1();
		super.unauthenticate();
	}

	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 10: The average, the minimum, the maximum, and the standard deviation of active sponsorships
	 * per sponsor
	 * 
	 * 2. Negative test.
	 * 3. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 * 
	 **/
	@Test
	public void query10Negative() {
		super.authenticate(null);
		//this.adminService.query1();
		super.unauthenticate();
	}

	// Tests Query11-----------------------------------------------------------------------------------------
	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 11: The top-5 sponsors in terms of number of active sponsorships.
	 * 
	 * 2. Positive test.
	 * 
	 **/
	@Test
	public void query11Positive() {
		super.authenticate("admin");
		//this.adminService.query1();
		super.unauthenticate();
	}

	/**
	 * Requirement: An actor who is authenticated as an administrator must be able to Display a dashboard.
	 * 
	 * 1. Query 11: The top-5 sponsors in terms of number of active sponsorships.
	 * 
	 * 2. Negative test.
	 * 3. Business rule that is intended to broke: The actor is not authenticated as an Admin
	 * 
	 **/
	@Test
	public void query11Negative() {
		super.authenticate(null);
		//this.adminService.query1();
		super.unauthenticate();
	}

}
