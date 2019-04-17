
package usecases;

import java.text.SimpleDateFormat;
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
import services.EducationDataService;
import services.HackerService;
import services.MiscellaneousDataService;
import services.PersonalDataService;
import services.PositionDataService;
import utilities.AbstractTest;
import domain.Curricula;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManageCurriculaTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private PersonalDataService			personalDataService;
	@Autowired
	private PositionDataService			positionDataService;
	@Autowired
	private EducationDataService		educationDataService;
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	// Supporting systems ------------------------------------------------------
	@Autowired
	private HackerService				hackerService;


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
				null, null, "test fullName", "654789654", "https://github.com/"
			}, {
				IllegalArgumentException.class, "", "test fullName", "654789654", "https://github.com/"
			}, {
				ConstraintViolationException.class, null, null, "654789654", "https://github.com/"
			}, {
				ConstraintViolationException.class, null, "test fullName", "6547854", "https://github.com/"
			}, {
				IllegalArgumentException.class, "hacker2", "test fullName", "654789654", "https://github.com/"
			}, {
				ConstraintViolationException.class, null, "test fullName", "654789654", "github"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.personalDataTemplate((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4]);
	}

	/*
	 * An actor who is authenticated as a company must be able to manage his/her curricula
	 * ADD POSITION DATA
	 * 
	 * 01- All ok
	 * 02- Future start date; Error
	 * 03- Blank title; Error
	 * 04- Not authenticated; Error
	 */

	@Test
	public void driverCreatePositionData() {
		final Object testingData[][] = {
			{
				null, "hacker1", "10/10/2010", "test title"
			}, {
				IllegalArgumentException.class, "", "10/10/2010", "test title"
			}, {
				ConstraintViolationException.class, "hacker1", "10/10/2020", "test title"
			}, {
				ConstraintViolationException.class, "hacker1", "10/10/2010", ""
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createPositionDataTemplate((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3]);
	}

	/*
	 * An actor who is authenticated as a company must be able to manage his/her curricula
	 * DELETE POSITION DATA
	 * 
	 * 01- All ok
	 * 02- Delete position data without being authenticated; Error
	 * 03- Hacker2 deleting hacker1's position data; Error
	 */

	@Test
	public void driverDeletePositionData() {
		final Object testingData[][] = {
			{
				IllegalArgumentException.class, ""
			}, {
				IllegalArgumentException.class, "hacker2"
			}, {
				null, "hacker1"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deletePositionDataTemplate((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}

	/*
	 * An actor who is authenticated as a company must be able to manage his/her curricula
	 * ADD EDUCATION DATA
	 * 
	 * 01- All ok
	 * 02- Future start date; Error
	 * 03- Blank institution; Error
	 * 04- Not authenticated; Error
	 */

	@Test
	public void driverCreateEducationData() {
		final Object testingData[][] = {
			{
				null, "hacker1", "10/10/2010", "test title"
			}, {
				IllegalArgumentException.class, "", "10/10/2010", "test insitution"
			}, {
				ConstraintViolationException.class, "hacker1", "10/10/2020", "test insitution"
			}, {
				ConstraintViolationException.class, "hacker1", "10/10/2010", ""
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createEducationDataTemplate((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3]);
	}

	/*
	 * An actor who is authenticated as a company must be able to manage his/her curricula
	 * DELETE EDUCATION DATA
	 * 
	 * 01- All ok
	 * 02- Delete position data without being authenticated; Error
	 * 03- Hacker2 deleting hacker1's position data; Error
	 */

	@Test
	public void driverDeleteEducationData() {
		final Object testingData[][] = {
			{
				IllegalArgumentException.class, ""
			}, {
				IllegalArgumentException.class, "hacker2"
			}, {
				null, "hacker1"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deletePositionDataTemplate((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}

	/*
	 * An actor who is authenticated as a company must be able to manage his/her curricula
	 * ADD MISCELLANEOUS DATA
	 * 
	 * 01- All ok
	 * 02- Future start date; Error
	 * 03- Blank text; Error
	 * 04- Not authenticated; Error
	 */

	@Test
	public void driverCreateMiscellaneousData() {
		final Object testingData[][] = {
			{
				null, "hacker1", "test text"
			}, {
				IllegalArgumentException.class, "", "test text"
			}, {
				ConstraintViolationException.class, "hacker1", ""
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createMiscellaneousDataTemplate((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2]);
	}

	/*
	 * An actor who is authenticated as a company must be able to manage his/her curricula
	 * DELETE MISCELLANEOUS DATA
	 * 
	 * 01- All ok
	 * 02- Delete position data without being authenticated; Error
	 * 03- Hacker2 deleting hacker1's position data; Error
	 */

	@Test
	public void driverDeleteMiscellaneousData() {
		final Object testingData[][] = {
			{
				IllegalArgumentException.class, ""
			}, {
				IllegalArgumentException.class, "hacker2"
			}, {
				null, "hacker1"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deletePositionDataTemplate((Class<?>) testingData[i][0], (String) testingData[i][1]);
	}

	/** --------------------- -- -- TEMPLATES -- -- --------------------- **/

	protected void createTemplate(final Class<?> expected, final String username, final String title) {
		Class<?> caught;
		caught = null;
		int i = 0;

		try {
			super.startTransaction();
			super.authenticate(username);
			i = this.curriculaService.findAllPrincipalNotApplied().size();

			// create new curricula
			final Curricula curricula = this.curriculaService.create();

			// set attributes
			curricula.setHacker(this.hackerService.findByPrincipal());
			curricula.setTitle(title);

			//save
			this.curriculaService.save(curricula);

			Assert.isTrue(this.curriculaService.findAllPrincipalNotApplied().size() > i);

			super.unauthenticate();
			super.commitTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void personalDataTemplate(final Class<?> expected, final String username, final String fullName, final String phone, final String gitHub) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate("hacker1");

			// get hacker1 not applied curricula personal data
			final PersonalData pd = new ArrayList<Curricula>(this.curriculaService.findAllPrincipalNotApplied()).get(0).getPersonalData();
			if (username != null) {
				super.unauthenticate();
				super.authenticate(username);
			}
			// set attributes
			pd.setFullName(fullName);
			pd.setGitHub(gitHub);
			pd.setPhoneNumber(phone);

			//save
			this.personalDataService.save(pd);

			super.unauthenticate();
			this.personalDataService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void createPositionDataTemplate(final Class<?> expected, final String username, final String startDate, final String title) {
		Class<?> caught;
		caught = null;

		try {
			int i;
			i = this.positionDataService.findAll().size();
			super.authenticate(username);

			// create new positionData
			final PositionData pd = this.positionDataService.create();

			// set attributes
			pd.setTitle(title);
			pd.setDescription("test description");
			pd.setCurricula((Curricula) this.curriculaService.findAllPrincipalNotApplied().toArray()[0]);
			if (startDate != null)
				pd.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(startDate));

			//save
			this.positionDataService.save(pd);

			super.unauthenticate();
			this.positionDataService.flush();
			Assert.isTrue(this.positionDataService.findAll().size() > i);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void deletePositionDataTemplate(final Class<?> expected, final String username) {
		Class<?> caught;
		caught = null;

		try {
			int i;
			i = this.positionDataService.findAll().size();
			super.authenticate("hacker1");

			// get hacker1 position data
			final PositionData pd = (PositionData) new ArrayList<Curricula>(this.curriculaService.findAllPrincipalNotApplied()).get(0).getPositionData().toArray()[0];
			if (username != null) {
				super.unauthenticate();
				super.authenticate(username);
			}

			//delete
			this.positionDataService.delete(pd);

			Assert.isTrue(this.positionDataService.findAll().size() < i);
			super.unauthenticate();
			this.positionDataService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void createEducationDataTemplate(final Class<?> expected, final String username, final String startDate, final String institution) {
		Class<?> caught;
		caught = null;

		try {
			int i;
			i = this.educationDataService.findAll().size();
			super.authenticate(username);

			// create new EducationData
			final EducationData ed = this.educationDataService.create();

			// set attributes
			ed.setInstitution(institution);
			ed.setDegree("test degree");
			ed.setMark(5.06);
			ed.setCurricula((Curricula) this.curriculaService.findAllPrincipalNotApplied().toArray()[0]);
			if (startDate != null)
				ed.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(startDate));

			//save
			this.educationDataService.save(ed);

			super.unauthenticate();
			this.educationDataService.flush();
			Assert.isTrue(this.educationDataService.findAll().size() > i);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void deleteEducationDataTemplate(final Class<?> expected, final String username) {
		Class<?> caught;
		caught = null;

		try {
			int i;
			i = this.educationDataService.findAll().size();
			super.authenticate("hacker1");

			// get hacker1 position data
			final EducationData ed = (EducationData) new ArrayList<Curricula>(this.curriculaService.findAllPrincipalNotApplied()).get(0).getPositionData().toArray()[0];
			if (username != null) {
				super.unauthenticate();
				super.authenticate(username);
			}

			//delete
			this.educationDataService.delete(ed);

			Assert.isTrue(this.educationDataService.findAll().size() < i);
			super.unauthenticate();
			this.educationDataService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void createMiscellaneousDataTemplate(final Class<?> expected, final String username, final String text) {
		Class<?> caught;
		caught = null;

		try {
			int i;
			i = this.miscellaneousDataService.findAll().size();
			super.authenticate(username);

			// create new MiscellaneousData
			final MiscellaneousData ed = this.miscellaneousDataService.create();

			// set attributes
			ed.setText(text);
			ed.setCurricula((Curricula) this.curriculaService.findAllPrincipalNotApplied().toArray()[0]);

			//save
			this.miscellaneousDataService.save(ed);

			super.unauthenticate();
			this.miscellaneousDataService.flush();
			Assert.isTrue(this.miscellaneousDataService.findAll().size() > i);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void deleteMiscellaneousDataTemplate(final Class<?> expected, final String username) {
		Class<?> caught;
		caught = null;

		try {
			int i;
			i = this.miscellaneousDataService.findAll().size();
			super.authenticate("hacker1");

			// get hacker1 position data
			final MiscellaneousData ed = (MiscellaneousData) new ArrayList<Curricula>(this.curriculaService.findAllPrincipalNotApplied()).get(0).getPositionData().toArray()[0];
			if (username != null) {
				super.unauthenticate();
				super.authenticate(username);
			}

			//delete
			this.miscellaneousDataService.delete(ed);

			Assert.isTrue(this.miscellaneousDataService.findAll().size() < i);
			super.unauthenticate();
			this.miscellaneousDataService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
