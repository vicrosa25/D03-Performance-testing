
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Configurations;
import services.ConfigurationsService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdminEditConfigTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ConfigurationsService configurationsService;


	// Test ------------------------------------------------------
	/*
	 * An actor who is not authenticated must be able to:
	 * Register to the system as a Administrator.
	 * 
	 * 01- All ok 							- Positive test
	 * 02- Admin is not autheticate 		- Negative test - error
	 * 03- Blank cacheTime 					- Negative test - error
	 * 04- Blank finderMaxResult 			- Negative test - error
	 * 05- Blank spanishMessage 			- Negative test - error
	 * 06- Blank englishMessage 			- Negative test - error
	 * 07- Blank countryCode 				- Negative test - error
	 * 08- Blank title 						- Negative test - error
	 * 09- Blank logo 						- Negative test - error
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				null, "admin", 2, 15, "spanishMessage", "englishMessage", "countryCode", "title", "logo"
			}, {
				IllegalArgumentException.class, "", 2, 15, "spanishMessage", "englishMessage", "countryCode", "title", "logo"
			}, {
				ConstraintViolationException.class, "admin", null, 15, "spanishMessage", "englishMessage", "countryCode", "title", "logo"
			}, {
				ConstraintViolationException.class, "admin", 2, null, "spanishMessage", "englishMessage", "countryCode", "title", "logo"
			}, {
				ConstraintViolationException.class, "admin", 2, 15, "", "englishMessage", "countryCode", "title", "logo"
			}, {
				ConstraintViolationException.class, "admin", 2, 15, "spanishMessage", "", "countryCode", "title", "logo"
			}, {
				ConstraintViolationException.class, "admin", 2, 15, "spanishMessage", "englishMessage", "", "title", "logo"
			}, {
				ConstraintViolationException.class, "admin", 2, 15, "spanishMessage", "englishMessage", "countryCode", "", "logo"
			}, {
				ConstraintViolationException.class, "admin", 2, 15, "spanishMessage", "englishMessage", "countryCode", "title", ""
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], 
						 (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
						 (String) testingData[i][8]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void template(Class<?> expected, String principal, Integer cacheTime, Integer finderMaxResult, String spanishMessage, 
							String englishMessage, String countryCode, String title, String logo) {
		Class<?> caught;
		caught = null;
		
		Configurations config;
		Configurations saved;

		try {
			
			// Logging
			super.authenticate(principal);

			
			config = this.configurationsService.getConfiguration();
		

			// Editing configurations
			config.setCacheTime(cacheTime);
			config.setFinderMaxResult(finderMaxResult);
			config.setSpanishMessage(spanishMessage);
			config.setEnglishMessage(englishMessage);
			config.setCountryCode(countryCode);
			config.setTitle(title);
			config.setLogo(logo);
			
			

			// Save new Configurations
			saved = this.configurationsService.update(config);
			
			
			Assert.isTrue(config.getId() == saved.getId());
			
			super.unauthenticate();
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
