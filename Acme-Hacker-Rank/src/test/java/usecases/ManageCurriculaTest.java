
package usecases;

import java.util.ArrayList;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.CurriculaService;
import services.HackerService;
import services.PersonalDataService;
import utilities.AbstractTest;
import domain.Curricula;
import domain.PersonalData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManageCurriculaTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private PersonalDataService	personalDataService;

	// Supporting systems ------------------------------------------------------
	@Autowired
	private HackerService		hackerService;


	// Test ------------------------------------------------------
	/*
	 * An actor who is authenticated as a company must be able to manage his/her curricula
	 * CREATE
	 * 
	 * 01- All ok
	 * 02- Not authenticated; Error
	 * 03- Blank title; Error
	 */

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				null, "hacker1", "test title"
			}, {
				IllegalArgumentException.class, "", "test title"
			}, {
				ConstraintViolationException.class, "hacker1", ""
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createTemplate((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2]);
	}

	/*
	 * An actor who is authenticated as a company must be able to manage his/her curricula
	 * EDIT PERSONAL DATA
	 * 
	 * 01- All ok
	 * 02- Not authenticated; Error
	 * 03- Blank fullName; Error
	 * 04- Incorrect number; Error
	 * 05- Edit other hacker's curricula; Error
	 * 06- Incorrect gitHub url; Error
	 */

	@Test
	public void driverPersonalData() {
		final Object testingData[][] = {
			{
				null, "hacker1", "test fullName", "654789654", "https://github.com/"
			}, {
				IllegalArgumentException.class, "", "test fullName", "654789654", "https://github.com/"
			}, {
				ConstraintViolationException.class, "hacker1", null, "654789654", "https://github.com/"
			}, {
				ConstraintViolationException.class, "hacker1", "test fullName", "6547854", "https://github.com/"
			//			}, {
			//				IllegalArgumentException.class, "hacker2", "test fullName", "654789654", "https://github.com/"
			//			}, {
			//				ConstraintViolationException.class, "hacker1", "test fullName", "654789654", "github"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.personalDataTemplate((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
				(String) testingData[i][3], (String) testingData[i][4]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void createTemplate(Class<?> expected, String username, String title) {
		Class<?> caught;
		caught = null;
		int i = 0;

		try {
			super.startTransaction();
			super.authenticate(username);
			i = this.curriculaService.findAllPrincipalNotApplied().size();

			// create new curricula
			Curricula curricula = this.curriculaService.create();

			// set attributes
			curricula.setHacker(this.hackerService.findByPrincipal());
			curricula.setTitle(title);

			//save
			this.curriculaService.save(curricula);
			

			Assert.isTrue(this.curriculaService.findAllPrincipalNotApplied().size() > i);

			super.unauthenticate();
			super.commitTransaction();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void personalDataTemplate(Class<?> expected, String username, String fullName, String phone, String gitHub) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(username);

			// get hacker1 not applied curricula personal data
			PersonalData pd = new ArrayList<Curricula>(this.curriculaService.findAllPrincipalNotApplied()).get(0).getPersonalData();

			// set attributes
			pd.setFullName(fullName);
			pd.setGitHub(gitHub);
			pd.setPhoneNumber(phone);

			//save
			this.personalDataService.save(pd);

			super.unauthenticate();
			this.personalDataService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
