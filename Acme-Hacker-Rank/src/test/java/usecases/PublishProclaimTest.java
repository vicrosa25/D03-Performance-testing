
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ProclaimService;
import utilities.AbstractTest;
import domain.Proclaim;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PublishProclaimTest extends AbstractTest {

	// Systems under test ------------------------------------------------------
	@Autowired
	private ProclaimService	proclaimService;


	// Test ------------------------------------------------------

	/**
	 * Publish a proclaim. Note that once a proclaim is published, there's no way to update
	 * or delete it, so double confirmation prior to publication is a must.
	 * **/

	/*
	 * 01- Publish proclaim; -> Correct
	 * 02- Blank text; -> Error
	 * 03- Unautenticated; -> Error
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			// exception, user, text
			{
				null, "chapter1", "Test text"
			}, {
				ConstraintViolationException.class, "chapter1", ""
			}, {
				IllegalArgumentException.class, null, "Test text"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2]);
	}

	// TEMPLATES ------------------------------------------------------------------------------------------------------------------

	protected void template(Class<?> expected, String user, String text) {
		Class<?> caught;
		caught = null;

		try {
			// Authentication
			authenticate(user);

			// Create proclaim
			Proclaim proclaim = this.proclaimService.create();
			proclaim.setText(text);

			// save
			proclaim = this.proclaimService.save(proclaim);
			this.proclaimService.flush();

			Assert.isTrue(this.proclaimService.findAll().contains(proclaim));
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}