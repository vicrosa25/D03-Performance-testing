
package usecases;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ApplicationService;
import services.CompanyService;
import utilities.AbstractTest;
import domain.Application;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CompanyManageApplicationTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ApplicationService	applicationService;

	// Supporting systems ------------------------------------------------------
	@Autowired
	private CompanyService	companyService;


	// Test ------------------------------------------------------
	/*
	 * An actor who is authenticated as a company must be able to manage the applications
	 * to their positions
	 * 
	 * ACCEPT
	 * 
	 * 01- All ok
	 * 02- Accept application that is pending; 		Error
	 * 03- Accept application of another company; 	Error
	 */

	@Test
	public void driverAccept() {
		final Object testingData[][] = {
			{
				null, "company1", "SUBMITTED"
			}, {
				IllegalArgumentException.class, "company1", "PENDING"
			}, {
				IllegalArgumentException.class, "company2", "SUBMITTED"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.acceptTemplate((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2]);
	}
	/*
	 * An actor who is authenticated as a company must be able to manage the applications
	 * to their positions
	 * 
	 * REJECT
	 * 
	 * 01- All ok
	 * 02- Reject application that is pending; Error
	 * 03- Reject application of another company; Error
	 */

	@Test
	public void driverReject() {
		final Object testingData[][] = {
			{
				null, "company1", "SUBMITTED"
			}, {
				IllegalArgumentException.class, "company1", "PENDING"
			}, {
				IllegalArgumentException.class, "company2", "SUBMITTED"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.rejectTemplate((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void acceptTemplate(Class<?> expected, String username, String status) {
		Class<?> caught;
		caught = null;
		int i = 0;

		try {

			super.authenticate("company1");
			
			for (Application application : new ArrayList<Application>(this.applicationService.findByCompany(this.companyService.findByPrincipal()))) {
				if (application.getStatus().equals(status)) {
					if (username != null) {
						super.unauthenticate();
						super.authenticate(username);
					}
					i = application.getId();
					this.applicationService.accept(application);

				}
			}

			Assert.isTrue(this.applicationService.findOne(i).getStatus().equals("ACCEPTED"));

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void rejectTemplate(Class<?> expected, String username, String status) {
		Class<?> caught;
		caught = null;
		int i = 0;

		try {

			super.authenticate("company1");

			for (Application application : new ArrayList<Application>(this.applicationService.findByCompany(this.companyService.findByPrincipal()))) {
				if (application.getStatus().equals(status)) {
					if (username != null) {
						super.unauthenticate();
						super.authenticate(username);
					}
					i = application.getId();
					this.applicationService.reject(application);
					
				}
			}

			Assert.isTrue(this.applicationService.findOne(i).getStatus().equals("REJECTED"));

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
