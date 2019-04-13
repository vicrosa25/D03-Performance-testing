
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.CompanyService;
import utilities.AbstractTest;
import utilities.Md5;
import domain.Company;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterAsCompanyTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CompanyService	companyService;


	// Test ------------------------------------------------------
	/*
	 * An actor who is not authenticated must be able to:
	 * Register to the system as a Company.
	 * 
	 * 01- All ok
	 * 02- Blank password; Error
	 * 03- Blank username; Error
	 * 04- Blank name; Error
	 * 05- Blank surname; Error
	 * 06- Blank vat; Error
	 * 07- Blank cardNumber; Error
	 * 08- Blank mail; Error
	 * 09- Blank phoneNumber; Error
	 * 10- Blank commercialName; Error
	 * 11- Negative VAT; Error
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				null, "password", "userName", "name", "surname", 2, "378721273855309", "test@gmail.com", "+341234", "CompanyName"
			}, {
				DataIntegrityViolationException.class, "", "userName", "name", "surname", 2, "378721273855309", "test@gmail.com", "+341234", "CompanyName"
			}, {
				DataIntegrityViolationException.class, "password", "", "name", "surname", 2, "378721273855309", "test@gmail.com", "+341234", "CompanyName"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "", "surname", 2, "378721273855309", "test@gmail.com", "+341234", "CompanyName"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "", 2, "378721273855309", "test@gmail.com", "+341234", "CompanyName"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "surname", null, "378721273855309", "test@gmail.com", "+341234", "CompanyName"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "surname", 2, "", "test@gmail.com", "+341234", "CompanyName"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "surname", 2, "378721273855309", "", "+341234", "CompanyName"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "surname", 2, "378721273855309", "test@gmail.com", "", "CompanyName"
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "surname", 2, "378721273855309", "test@gmail.com", "+346547", ""
			}, {
				DataIntegrityViolationException.class, "password", "userName", "name", "surname", -1, "378721273855309", "test@gmail.com", "+346547", "CompanyName"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], 
						 (String) testingData[i][4], (Integer) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void template(Class<?> expected, String pass, String userName, String name, String surname, Integer vat, String cardNumber, String email, String phoneNumber, String companyName) {
		Class<?> caught;
		caught = null;

		try {
			String password;
			int i;
			i = this.companyService.findAll().size();

			super.authenticate(null);

			// Create new Company
			Company company = this.companyService.create();

			// Company userAccount
			password = Md5.encodeMd5(pass);
			company.getUserAccount().setPassword(password);
			company.getUserAccount().setUsername(userName);

			// Actor attributes
			company.setName(name);
			company.setSurname(surname);
			company.setVat(vat);
			company.setCardNumber(cardNumber);
			company.setEmail(email);
			company.setPhoneNumber(phoneNumber);
			company.setIsBanned(false);

			// Company attributes
			company.setCommercialName(companyName);

			// Company Relatioships


			// Save new Company
			this.companyService.save(company);

			Assert.isTrue(this.companyService.findAll().size() > i);

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
