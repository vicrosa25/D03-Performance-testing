
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Sponsor;
import services.SponsorService;
import utilities.AbstractTest;
import utilities.Md5;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterASponsorTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private SponsorService sponsorService;

	// Test ------------------------------------------------------
	/*
	 * An actor who is not authenticated must be able to:
	 * Register to the system as a Sponsor.
	 * 
	 * 01- All ok
	 * 02- Blank password; Error
	 * 03- Blank username; Error
	 * 04- Blank name; Error
	 * 05- Blank middlename; Error
	 * 06- Blank suername; Error
	 * 07- Blank photo; Error
	 * 08- Blank mail; Error
	 * 09- Blank phoneNumber; Error
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				null, "password", "userName", "name", "middleName", "surname", "http://pictureTest.com", "test@gmail.com", "+341234"
			}, {
				DataIntegrityViolationException.class, "", "userName", "name", "middleName", "surname", "http://pictureTest.com", "test@gmail.com", "+341234"
			}, {
				DataIntegrityViolationException.class, "password", "", "name", "middleName", "surname", "http://pictureTest.com", "test@gmail.com", "+341234"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "", "middleName", "surname", "http://pictureTest.com", "test@gmail.com", "+341234"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "", "surname", "http://pictureTest.com", "test@gmail.com", "+341234"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "middleName", "", "http://pictureTest.com", "test@gmail.com", "+341234"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "middleName", "surname", "", "test@gmail.com", "+341234"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "middleName", "surname", "http://pictureTest.com", "", "+341234"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "middleName", "surname", "http://pictureTest.com", "test@gmail.com", ""
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "middleName", "surname", "http://pictureTest.com", "test@gmail.com", "+341234"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
						  (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], 
						  (String) testingData[i][6], (String) testingData[i][7], (String) testingData[i][8]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void template(Class<?> expected, String pass, String userName, String name, 
							String middleName, String surname, String photo, String email, 
							String phoneNumber) {
		Class<?> caught;
		caught = null;

		try {
			String password;
			int i;
			i = this.sponsorService.findAll().size();

			super.authenticate(null);

			// Create new Chapter
			Sponsor sponsor = this.sponsorService.create();
			
			// Chapter userAccount
			password = Md5.encodeMd5(pass);
			sponsor.getUserAccount().setPassword(password);
			sponsor.getUserAccount().setUsername(userName);
			
			// Actor attributes
			sponsor.setName(name);
			sponsor.setMiddleName(middleName);
			sponsor.setSurname(surname);
			sponsor.setPhoto(photo);
			sponsor.setEmail(email);
			sponsor.setPhoneNumber(phoneNumber);
			sponsor.setIsBanned(false);
			
			
			// Chapter Relatioships
			sponsor.setSponsorships(null);
			sponsor.setAddress(null);
			
			// Save new Chapter
			this.sponsorService.save(sponsor);

			Assert.isTrue(this.sponsorService.findAll().size() > i);
			
			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
//			System.out.println(oops.getMessage());
//			System.out.println(oops.getClass());
//			System.out.println(oops.getCause());
		}
		super.checkExceptions(expected, caught);
	}

}
