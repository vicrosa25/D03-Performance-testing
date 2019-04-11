
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Message;
import services.ActorService;
import services.MessageService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreateAndSendMessageTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActorService   actorService;


	// Test ------------------------------------------------------
	/*
	 * An actor who is not authenticated must be able to:
	 * Sending a Message
	 * 
	 * 01- Positive test, OK
	 * 02- Negative test, Blank sender; Error
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				null, "hacker1", "hacker2", "subject", "body", "LOW", "TAG"
			}, {
				IllegalArgumentException.class, "", "hacker2", "subject", "body", "LOW", "TAG"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((Class<?>) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], 
						 (String) testingData[i][3],  (String) testingData[i][4], (String) testingData[i][5],
						 (String) testingData[i][6]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void template(Class<?> expected, String sender, String recipient, String subject, 
							String body, String priority, String tag) {
		Class<?> caught;
		caught = null;
		Message message;
		Message saved;
		int senderId;
		int recipientId;
		

		try {
			
			// Authenticate as Actor
			super.authenticate(sender);
			
			int i = this.messageService.findAll().size();
			
			
			// Create new message
			message = this.messageService.create();
			
			// Setting sender and recipient
			senderId = 		super.getEntityId(sender);
			recipientId = 	super.getEntityId(recipient);

			
			// Message setting
			message.setSender(this.actorService.findOne(senderId));
			message.getRecipients().add(this.actorService.findOne(recipientId));
			message.setSubject(subject);
			message.setBody(body);
			message.setPriority(priority);
			message.getTags().add(tag);
			message.setIsNotification(false);
			
			
			saved = this.messageService.save(message);
			
			int ii = this.messageService.findAll().size();
			
			// Check effectively create and send message.
			Assert.isTrue(ii > i);
			Assert.isTrue(this.actorService.findOne(senderId).getMessages().contains(saved));
			Assert.isTrue(this.actorService.findOne(recipientId).getMessages().contains(saved));
			
			super.unauthenticate();
			
			
		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}




