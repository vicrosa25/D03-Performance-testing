
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Answer;
import domain.Application;
import services.AnswerService;
import services.ApplicationService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HackerUpdateAppTest extends AbstractTest {

	// System under test ---------------------------------------------------------------------------
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private AnswerService		answerService;


	// Tests -----------------------------------------------------------------------------------------

	/**
	 * Requirement: An actor who is authenticated as a hacker must be able to: "Update an Application"
	 * 
	 * 1. Positive test.
	 * 
	 **/
	@Test
	public void createAppPositive() {
		int appId;
		Application app;
		Application saved;
		Answer answer;

		super.authenticate("hacker1");

		// Application
		appId = super.getEntityId("application1");
		app = this.applicationService.findOne(appId);

		// Answer Settings
		answer = this.answerService.create();
		answer.setText("Test text");
		answer.setLink("http://linkTest");
		app.setAnswer(this.answerService.save(answer));

		saved = this.applicationService.save(app);

		Assert.isTrue(app.getId() == saved.getId());

		super.unauthenticate();

	}

	/**
	 * Requirement: An actor who is authenticated as a hacker must be albe to: "Update an Application"
	 * 
	 * 1. Negative test.
	 * 2. Business rule that is intended to broke: The actor is not authenticated as a hacker
	 * 
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void createAppNegative() {
		int appId;
		Application app;
		Answer answer;

		super.authenticate(null);
		
		// Application
		appId = super.getEntityId("application1");
		app = this.applicationService.findOne(appId);

		// Answer Settings
		answer = this.answerService.create();
		answer.setText("Test text");
		answer.setLink("http://linkTest");
		app.setAnswer(this.answerService.save(answer));

	}
}
