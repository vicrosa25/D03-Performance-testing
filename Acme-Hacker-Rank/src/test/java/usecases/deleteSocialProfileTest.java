
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.SocialProfile;
import services.SocialProfileService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class deleteSocialProfileTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private SocialProfileService	socialProficeServcie;

	// Test ------------------------------------------------------
	/*
	 * An actor who is authenticated must be able to:
	 * 		Register theis Social Profiles.
	 * 
	 * 01- Positive test, OK
	 * 02- Negative test, The actor is not authenticated; Error
	 * 03- Negative test, nickd attribute blank; Error
	 * 04- Negative test, The principal does not own the socialProfile; Error
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				null, "admin", "socialProfile1", "nick"
			}, {
				IllegalArgumentException.class, "", "socialProfile1", "nick"
			}, {
				IllegalArgumentException.class, "hacker1", "socialProfile1", "nick"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
						 (String) testingData[i][3]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void template(Class<?> expected, String actor, String socialProfile, String nick) {
		Class<?> caught;
		caught = null;
		SocialProfile socialIdentity;


		try {
			
			// Authenticate as 'admin'
			super.authenticate(actor);

			// Register the social profile
			socialIdentity = this.socialProficeServcie.findOne(super.getEntityId(socialProfile));
			socialIdentity.setNick(nick);
			
			this.socialProficeServcie.delete(socialIdentity);

			
			// Check.
			Assert.isTrue(!this.socialProficeServcie.findAll().contains(socialIdentity));
			
			
			super.unauthenticate();
			
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}




