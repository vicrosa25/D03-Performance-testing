
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Brotherhood;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;
import services.BrotherhoodService;
import services.InceptionRecordService;
import services.LegalRecordService;
import services.LinkRecordService;
import services.MiscellaneousRecordService;
import services.PeriodRecordService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManageHistoryTest extends AbstractTest {

	// Systems under test ------------------------------------------------------
	@Autowired
	private InceptionRecordService		inceptionRecordService;

	@Autowired
	private PeriodRecordService			periodRecordService;

	@Autowired
	private LinkRecordService			linkRecordService;

	@Autowired
	private LegalRecordService			legalRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	// Supporting services
	@Autowired
	private BrotherhoodService		brotherhoodService;


	// Test ------------------------------------------------------

	/** Test about history, all are done using brothehood1 account **/

	/*
	 * Brotherhoods can manage their histories: Inception record
	 * 
	 * 01- Change title and description; 				-> Correct
	 * 02- Change title and description, blank title; 	-> Error
	 */

	@Test
	public void driverInception() {
		final Object testingData[][] = {
			// exception, title, description
			{
				null, "Test title", "Test description"
			}, {
				ConstraintViolationException.class, "Test title", null
			}, {
				ConstraintViolationException.class, null, "Test Description"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateInception((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2]);
	}

	/*
	 * Brotherhoods can manage their histories: Period record
	 * 
	 * 01- Change title, description, start year and end year;	-> Correct
	 * 02- blank title;											-> Error
	 * 03- End before start;									-> Error
	 */

	@Test
	public void driverPeriod() {
		final Object testingData[][] = {
			// exception, title, description, start, end
			{
				null, "Test title", "Test description", 2010, 2011
			}, {
				ConstraintViolationException.class, "", "Test description", 2010, 2011
			}, {
				ConstraintViolationException.class, "Test title", "Test description", 2011, 2010
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePeriod((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
				(Integer) testingData[i][3], (Integer) testingData[i][4]);
	}

	/*
	 * Brotherhoods can manage their histories: Legal record
	 * 
	 * 01- Change title, description, legal name, vat and laws; -> Correct
	 * 02- blank legal name;									-> Error
	 */

	@Test
	public void driverLegal() {
		final Object testingData[][] = {
			// exception, title, description, legal name, VAT, laws
			{
				null, "Test title", "Test description", "Test name", 9.5, "Test law"
			}, {
				ConstraintViolationException.class, "Test title", "Test description", "", 9.5, "Test law"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateLegal((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
				(String) testingData[i][3], (Double) testingData[i][4], (String) testingData[i][5]);
	}

	/*
	 * Brotherhoods can manage their histories: Link record
	 * 
	 * 01- Change title, description, and link brotherhood; -> Correct
	 * 02- Null link;										-> Error
	 */

	@Test
	public void driverLink() {
		final Object testingData[][] = {
			// exception, title, description, link name
			{
				null, "Test title", "Test description", "brotherhood2"
			}, {
				ConstraintViolationException.class, "Test title", "Test description", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateLink((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3]);
	}

	/*
	 * Brotherhoods can manage their histories: Miscellaneous record
	 * 
	 * 01- Change title and description; 							-> Correct
	 * 02- Null title; 												-> Error
	 * 03- Not autenticated; 										-> Error
	 * 04- Autenticated but saving to another brotherhood history; 	-> Error
	 */

	@Test
	public void driverMiscellaneous() {
		final Object testingData[][] = {
			// exception, title, description, autentication name, other brotherhood name
			{
				null, "Test title", "Test description", "brotherhood1", "brotherhood1"
			}, {
				IllegalArgumentException.class, "Test title", "Test description", null, null
			}, {
				IllegalArgumentException.class, "Test title", "Test description", "brotherhood2", "brotherhood1"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateMiscellaneous((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
				(String) testingData[i][3], (String) testingData[i][4]);
	}

	// TEMPLATES ------------------------------------------------------------------------------------------------------------------

	protected void templateMiscellaneous(Class<?> expected, String title, String description, String principal, String otherBro) {
		Class<?> caught;
		caught = null;

		try {
			// Authentication
			this.authenticate(principal);

			// Creating a legal record
			MiscellaneousRecord record = this.miscellaneousRecordService.create();

			// Applying the changes
			record.setTitle(title);
			record.setDescription(description);

			// Saving to the principal
			record = this.miscellaneousRecordService.save(record);
			this.miscellaneousRecordService.flush();

			if (otherBro != null) {
				// Editing by the other
				this.unauthenticate();
				this.authenticate(otherBro);

				record.setTitle("now this is mine");

				//Saving again
				record = this.miscellaneousRecordService.save(record);
				this.miscellaneousRecordService.flush();
			}
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateLink(Class<?> expected, String title, String description, String linkName) {
		Class<?> caught;
		caught = null;

		try {
			// Authentication
			this.authenticate("brotherhood1");

			// Creating a legal record
			LinkRecord record = this.linkRecordService.create();

			// Applying the changes
			record.setTitle(title);
			record.setDescription(description);
			for (Brotherhood bro : this.brotherhoodService.findAll()) {
				if (bro.getName().equals(linkName)) {
					record.setLink(bro);
				}
			}

			this.linkRecordService.save(record);
			this.linkRecordService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateLegal(Class<?> expected, String title, String description, String legalName, Double vat, String laws) {
		Class<?> caught;
		caught = null;

		try {
			// Authentication
			this.authenticate("brotherhood1");

			// Creating a legal record
			LegalRecord record = this.legalRecordService.create();

			// Applying the changes
			record.setTitle(title);
			record.setDescription(description);
			record.setLaws(laws);
			record.setLegalName(legalName);
			record.setVat(vat);

			this.legalRecordService.save(record);
			this.legalRecordService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templatePeriod(Class<?> expected, String title, String description, Integer startYear, Integer endYear) {
		Class<?> caught;
		caught = null;

		try {
			// Authentication
			this.authenticate("brotherhood1");

			// Creating a period record
			PeriodRecord record = this.periodRecordService.create();

			// Applying the changes
			record.setTitle(title);
			record.setDescription(description);
			record.setStartYear(startYear);
			record.setEndYear(endYear);

			this.periodRecordService.save(record);
			this.periodRecordService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	protected void templateInception(Class<?> expected, String title, String description) {
		Class<?> caught;
		caught = null;

		try {
			// Authentication
			this.authenticate("brotherhood1");

			// Getting the inception record
			InceptionRecord record = this.brotherhoodService.findByPrincipal()
				.getHistory().getInceptionRecord();

			// Applying the changes
			record.setTitle(title);
			record.setDescription(description);

			this.inceptionRecordService.save(record);
			this.inceptionRecordService.flush();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
/*
 * 
   System.out.println(oops.getMessage());
   System.out.println(oops.getClass());
   System.out.println(oops.getCause());
 */