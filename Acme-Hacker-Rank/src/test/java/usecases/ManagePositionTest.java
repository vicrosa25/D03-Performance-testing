
package usecases;

import java.text.SimpleDateFormat;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.CompanyService;
import services.PositionService;
import utilities.AbstractTest;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManagePositionTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private PositionService	positionService;

	// Supporting systems ------------------------------------------------------
	@Autowired
	private CompanyService	companyService;


	// Test ------------------------------------------------------
	/*
	 * An actor who is authenticated as a company must be able to manage their positions
	 * CREATE
	 * 
	 * 01- All ok
	 * 02- No deadline; 		Error
	 * 03- Past deadline; 		Error
	 * 04- Negative salary; 	Error
	 * 05- Blank profile; 		Error
	 * 06- not authenticated; 	Error
	 */

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				null, "company1", "10/10/2019", 15.06, "test profile"
			}, {
				ConstraintViolationException.class, "company1", null, 15.06, "test profile"
			}, {
				ConstraintViolationException.class, "company1", "10/10/2018", 15.06, "test profile"
			}, {
				ConstraintViolationException.class, "company1", "10/10/2019", -15.06, "test profile"
			}, {
				ConstraintViolationException.class, "company1", "10/10/2019", 15.06, ""
			}, {
				ConstraintViolationException.class, "", "10/10/2019", 15.06, "test profile"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createTemplate((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3],
						 (String) testingData[i][4]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void createTemplate(Class<?> expected, String username, String deadline, Double salary, String profile) {
		Class<?> caught;
		caught = null;

		try {
			int i;
			i = this.positionService.findAll().size();

			super.authenticate(username);

			// Create new Position
			Position position = this.positionService.create();

			// Position attibutes
			position.setCompany(this.companyService.findByPrincipal());
			position.setDescription("test description");
			position.setProfile(profile);
			position.setSalary(salary);
			position.setSkills("test skills");
			position.setTechnologies("test tech");
			position.setTitle("test title");
			if (deadline != null)
				position.setDeadline(new SimpleDateFormat("dd/MM/yyyy").parse(deadline));

			// Save new Position
			this.positionService.save(position);

			Assert.isTrue(this.positionService.findAll().size() > i);

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
