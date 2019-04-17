
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

import services.CompanyService;
import services.ProblemService;
import utilities.AbstractTest;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManageProblemTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private ProblemService	problemService;

	// Supporting systems ------------------------------------------------------
	@Autowired
	private CompanyService	companyService;


	// Test ------------------------------------------------------
	/*
	 * An actor who is authenticated as a company must be able to manage their database of problems
	 * CREATE
	 * 
	 * 01- All ok
	 * 02- No title; Error
	 * 03- Not authenticated; Error
	 */

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			{
				null, "company1", "Test title"
			}, {
				ConstraintViolationException.class, "company1", ""
			}, {
				IllegalArgumentException.class, null, "test title"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createTemplate((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2]);
	}
	/*
	 * ------------------------------------------------------------------------------------------------------------------------------------------
	 * An actor who is authenticated as a company must be able to manage their problems
	 * DELETE
	 * 
	 * 01- All ok
	 * 02- Delete problem of other company; Error
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

	// Ancillary methods ------------------------------------------------------
	protected void createTemplate(Class<?> expected, String username, String title) {
		Class<?> caught;
		caught = null;

		try {
			super.startTransaction();
			int i;
			i = this.problemService.findAll().size();


			super.unauthenticate();
			super.authenticate(username);

			// Create new Problem
			Problem problem = this.problemService.create();

			// Problem attibutes
			problem.setCompany(this.companyService.findByPrincipal());
			problem.setStatement("test statement");
			problem.setTitle(title);
			problem.setFinalMode(false);

			// Save new Problem
			this.problemService.save(problem);

			Assert.isTrue(this.problemService.findAll().size() > i);

			super.unauthenticate();
			super.commitTransaction();
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
			i = this.problemService.findAll().size();

			super.authenticate("company1");

			for (Problem problem : new ArrayList<Problem>(this.companyService.findByPrincipal().getProblems())) {
				if (!problem.getFinalMode()) {
					if (username != null) {
						super.unauthenticate();
						super.authenticate(username);
					}
					this.problemService.delete(problem);

				}
			}

			Assert.isTrue(this.problemService.findAll().size() < i);

			super.unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
