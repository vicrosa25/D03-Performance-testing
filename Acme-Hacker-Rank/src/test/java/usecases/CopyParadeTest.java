
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ProcessionService;
import utilities.AbstractTest;
import domain.Procession;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CopyParadeTest extends AbstractTest {

	// Systems under test ------------------------------------------------------
	@Autowired
	private ProcessionService	processionService;


	// Test ------------------------------------------------------

	/**
	 * Make a copy of one of their parades. When a parade is copied, a new ticker is generated,
	 * its status is cleared, the rejection reason is cleared, and it changes to draft mode
	 * **/

	/*
	 * Brotherhoods copy parade: We are going to copy all the parades of brotherhood1
	 * 
	 * 01- Copy parades; -> Correct
	 * 02- Copy parades of other user; -> Error
	 * 03- Copy parades being unautenticated; -> Error
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			// exception, user
			{
				null, "brotherhood1"
			}, {
				IllegalArgumentException.class, "brotherhood2"
			}, {
				IllegalArgumentException.class, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}

	// TEMPLATES ------------------------------------------------------------------------------------------------------------------

	protected void template(Class<?> expected, String user) {
		Class<?> caught;
		caught = null;

		try {
			// Authentication
			authenticate(user);

			// Iterate over brotherhood1's parades 
			for (Procession procession : this.processionService.findAll()) {
				if (procession.getBrotherhood().getUserAccount().getUsername().equals("brotherhood1")) {
					this.processionService.copy(procession.getId());
				}
			}

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}