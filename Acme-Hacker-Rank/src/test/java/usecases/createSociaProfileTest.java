
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.SocialProfile;
import services.ActorService;
import services.SocialProfileService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class createSociaProfileTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private SocialProfileService	socialProficeServcie;
	
	@Autowired
	private ActorService			actorService;


	// Test ------------------------------------------------------
	/*
	 * An actor who is authenticated must be able to:
	 * 		Register theis Social Profiles.
	 * 
	 * 01- Positive test, OK
	 * 02- Negative test, The actor is not authenticated; Error
	 * 03- Negative test, socialNetwork attribute blank; Error
	 * 04- Negative test, link attribute blank; Error
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				null, "hacker2", "socialNetwork", "http://socialNetwork"
			}, {
				IllegalArgumentException.class, "", "socialNetwork", "http://socialNetwork"
			}, {
				ConstraintViolationException.class, "hacker2", "", "http://socialNetwork"
			}, {
				ConstraintViolationException.class, "hacker2", "socialNetwork", ""
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
						 (String) testingData[i][3]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void template(Class<?> expected, String actor, String socialNetwork,  String link) {
		Class<?> caught;
		caught = null;
		SocialProfile socialProfile;
		int i, ii;

		try {
			
			// Authenticate as 'hacker1'
			super.authenticate(actor);

			i = this.socialProficeServcie.findAll().size();

			// Register the social profile
			socialProfile = this.socialProficeServcie.create();
			socialProfile.setActor(this.actorService.findByPrincipal());
			socialProfile.setNick(actor);
			socialProfile.setSocialNetwork(socialNetwork);
			socialProfile.setLink(link);
			
			this.socialProficeServcie.save(socialProfile);
			
			ii = this.socialProficeServcie.findAll().size();
			
			// Check.
			Assert.isTrue(ii > i);
			
			
			super.unauthenticate();
			
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}




