
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.SponsorshipService;
import utilities.AbstractTest;
import domain.Sponsorship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManageSponsorshipTest extends AbstractTest {

	// Systems under test ------------------------------------------------------
	@Autowired
	private SponsorshipService	sponsorshipService;


	// Test ------------------------------------------------------

	/**
	 * Manage his or her sponsorships, which includes listing, showing, creating, updating,
	 * and removing them. Note that removing a sponsorship does not actually delete it from the system,
	 * but de-activates it.
	 * **/

	/*
	 * 01- Edit sponsorship; -> Correct
	 * 02- Edit sponsorship; target page not URL -> Error
	 * 03- Edit sponsorship; blank banner -> Error
	 * 04- Edit sponsorship without authenticate -> error
	 * 05- Edit other's sponsorship -> error
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			// exception, user, target page, brand name
			{
				null, "sponsor1", "https://www.google.com/", "https://www.google.com/"
			}, {
				ConstraintViolationException.class, "sponsor1", null, "https://www.google.com/"
			}, {
				ConstraintViolationException.class, "sponsor1", "https://www.google.com/", ""
			}, {
				ConstraintViolationException.class, null, "https://www.google.com/", "https://www.google.com/"
			}, {
				ConstraintViolationException.class, "sponsor2", "https://www.google.com/", "https://www.google.com/"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3]);
	}

	// TEMPLATES ------------------------------------------------------------------------------------------------------------------

	protected void template(Class<?> expected, String user, String targetPage, String banner) {
		Class<?> caught;
		caught = null;
		Sponsorship sponsorship = null;
		try {
			// Authentication
			authenticate(user);

			// Get a sponsorship owned by sponsor1
			for (Sponsorship s : this.sponsorshipService.findAll()) {
				if (s.getSponsor().getUserAccount().getUsername().equals("sponsor1")) {
					sponsorship = s;
				}
			}

			// Editing
			sponsorship.setBanner(banner);
			sponsorship.setTargetPage(targetPage);

			// Save and flush
			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}