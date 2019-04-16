
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Hacker;
import services.HackerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EditHackerProfileTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private HackerService hackerService;


	// Test ------------------------------------------------------
	/*
	 * An actor who is not authenticated must be able to:
	 * Register to the system as a Hacker.
	 * 
	 * 01- Positive test, OK
	 * 02- Negative test, Blank name; Error
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				null, "name", "surname", 2, "378721273855309", "test@gmail.com", "+341234"
			}, {
				ConstraintViolationException.class, "", "surname", 2, "378721273855309", "test@gmail.com", "+341234"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
						 (Integer) testingData[i][3],  (String) testingData[i][4], (String) testingData[i][5],
						 (String) testingData[i][6]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void template(Class<?> expected, String name, String surname, Integer vat, String cardNumber, 
							String email, String phoneNumber) {
		Class<?> caught;
		caught = null;
		BindingResult binding = null;

		try {
			
			// Authenticate as 'hacker1'
			super.authenticate("hacker1");
			Hacker hacker1 = this.hackerService.findByPrincipal();
			int version = hacker1.getVersion();


			// Actor attributes
			hacker1.setName(name);
			hacker1.setSurname(surname);
			hacker1.setVat(vat);
			hacker1.setCardNumber(cardNumber);
			hacker1.setEmail(email);
			hacker1.setPhoneNumber(phoneNumber);
			

			// Update Hacker1
			hacker1 = this.hackerService.reconstruct(hacker1, binding);
			
			this.hackerService.save(hacker1);
			
			// Check effectively change Hacker1.
			Assert.isTrue(hacker1.getVersion() != version);
			super.unauthenticate();
			
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}




