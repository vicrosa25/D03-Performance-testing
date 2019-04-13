
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

import services.CompanyService;
import utilities.AbstractTest;
import domain.Company;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EditCompanyProfileTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CompanyService	companyService;


	// Test ------------------------------------------------------
	/*
	 * An actor who is not authenticated must be able to:
	 * Register to the system as a Company.
	 * 
	 * 01- Positive test, OK
	 * 02- Negative test, Blank name; Error
	 * 03- Negative test, null commercial name; error
	 * 04- Negative test, negative vat; error
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				null, "name", "surname", 2, "378721273855309", "test@gmail.com", "+341234", "commercial name"
			}, {
				ConstraintViolationException.class, "", "surname", 2, "378721273855309", "test@gmail.com", "+341234", "commercialName"
			}, {
				ConstraintViolationException.class, "", "surname", 2, "378721273855309", "test@gmail.com", "+341234", null
			}, {
				ConstraintViolationException.class, "", "surname", -2, "378721273855309", "test@gmail.com", "+341234", "commercialName"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
						 (Integer) testingData[i][3],  (String) testingData[i][4], (String) testingData[i][5],
						 (String) testingData[i][6], (String) testingData[i][7]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void template(Class<?> expected, String name, String surname, Integer vat, String cardNumber, 
							String email, String phoneNumber, String commercialName) {
		Class<?> caught;
		caught = null;
		BindingResult binding = null;

		try {
			
			// Authenticate as 'company1'
			super.authenticate("company1");
			Company company1 = this.companyService.findByPrincipal();
			int version = company1.getVersion();


			// Actor attributes
			company1.setName(name);
			company1.setSurname(surname);
			company1.setVat(vat);
			company1.setCardNumber(cardNumber);
			company1.setEmail(email);
			company1.setPhoneNumber(phoneNumber);
			company1.setCommercialName(commercialName);

			// Update Company1
			company1 = this.companyService.reconstruct(company1, binding);
			
			this.companyService.save(company1);
			
			// Check effectively change Company1.
			Assert.isTrue(company1.getVersion() != version);
			super.unauthenticate();
			
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}




