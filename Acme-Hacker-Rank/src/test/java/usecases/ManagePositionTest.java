
package usecases;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

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
	 * 02- Salary 0.0;			Correct
	 * 03- Next day date;		Correct
	 * 04- No deadline; 		Error
	 * 05- Past deadline; 		Error
	 * 06- Negative salary; 	Error
	 * 07- Blank profile; 		Error
	 * 08- not authenticated; 	Error
	 */

	@Test
	public void driverCreate() {
		Calendar fecha = Calendar.getInstance();
		fecha.add(Calendar.HOUR, 24);

		final Object testingData[][] = {
			{
				null, "company1", "10/10/2019", 15.06, "test profile"
			}, {
				null, "company1", "10/10/2019", 0.1, "test profile"
			}, {
				null, "company1", new SimpleDateFormat("dd/MM/yyyy").format(fecha.getTime()), 2000.0, "test profile"
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
	/*
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * An actor who is authenticated as a company must be able to manage their positions
	 * DELETE
	 * 
	 * 01- All ok
	 * 02- Delete position of other company; Error
	 */

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				null, null
			}, {
				IllegalArgumentException.class, "company2"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteTemplate((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}
	/*
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * An actor who is authenticated as a company must be able to manage their positions
	 * CANCEL
	 * 
	 * 01- All ok
	 * 02- Cancel position of other company; Error
	 * 03- Cancel position in draft mode; Error
	 */

	@Test
	public void driverCancel() {
		final Object testingData[][] = {
			{
				null, null, true
			}, {
				IllegalArgumentException.class, "company2", true
			}, {
				IllegalArgumentException.class, null, false
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.cancelTemplate((Class<?>) testingData[i][0], (String) testingData[i][1], (Boolean) testingData[i][2]);
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

			Errors errors = new BeanPropertyBindingResult(position, "position");
			this.positionService.reconstruct(position, errors);

			// Save new Position
			this.positionService.save(position);

			Assert.isTrue(this.positionService.findAll().size() > i);

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	protected void deleteTemplate(Class<?> expected, String username) {
		Class<?> caught;
		caught = null;

		try {
			int i;
			i = this.positionService.findAll().size();

			super.authenticate("company1");

			for (Position position : new ArrayList<Position>(this.companyService.findByPrincipal().getPositions())) {
				if(!position.getFinalMode()){
					if (username != null) {
						super.unauthenticate();
						super.authenticate(username);
					}
					this.positionService.delete(position);

				}
			}

			Assert.isTrue(this.positionService.findAll().size() < i);

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	protected void cancelTemplate(Class<?> expected, String username, Boolean finalMode) {
		Class<?> caught;
		caught = null;
		Position cancelled = null;

		try {

			super.authenticate("company1");

			for (Position position : new ArrayList<Position>(this.companyService.findByPrincipal().getPositions())) {
				if (position.getFinalMode().equals(finalMode)) {
					if (username != null) {
						super.unauthenticate();
						super.authenticate(username);
					}
					cancelled = position;
					this.positionService.cancel(position);
					break;
				}
			}

			Assert.isTrue(cancelled.getCancelled());

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
