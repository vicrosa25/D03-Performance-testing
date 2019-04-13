package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Message;
import services.ActorService;
import services.MessageService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DeleteMessageTest extends AbstractTest {
	
	// System under test ---------------------------------------------------------------------------
	@Autowired
	private MessageService	messageService;
	
	@Autowired
	private ActorService	actorService;
	
	
	// Tests -----------------------------------------------------------------------------------------
	
	
	/**
	 * Requirement: An actor who is authenticated must be able to:  "Delete a his or her Messages"
	 *  
	 * 	1. Positive test.
	 *  
	 **/
	@Test
	public void deleteMessagePositive(){
		Message message;
		int messagesBefore;
		int messagesAfter;
		Actor principal;
		
		super.authenticate("hacker1");
		
		principal = this.actorService.findByPrincipal();
		
		
		messagesBefore = principal.getMessages().size();

		message = this.messageService.findOne(super.getEntityId("message1"));
		message.getTags().add("DELETED");
		
		this.messageService.delete(message);
		
		messagesAfter = principal.getMessages().size();
		
		Assert.isTrue(messagesBefore > messagesAfter);
		
		super.unauthenticate();
		
	}
	
	
	/**
	 * Requirement: An actor who is authenticated must be able to:  "Delete a his or her Messages"
	 *  
	 * 1. Negative test.
	 * 2. Business rule that is intended to broke: The actor does not own the message
	 *  
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void deleteMessageNegative(){
		Message message;
		int messagesBefore;
		int messagesAfter;
		Actor principal;
		
		super.authenticate("hacker1");
		
		principal = this.actorService.findByPrincipal();
		
		
		messagesBefore = principal.getMessages().size();

		message = this.messageService.findOne(super.getEntityId("message3"));
		message.getTags().add("DELETED");
		
		this.messageService.delete(message);
		
		messagesAfter = principal.getMessages().size();
		
		Assert.isTrue(messagesBefore > messagesAfter);
		
		super.unauthenticate();
	}
	
	/**
	 * Requirement: An actor who is authenticated must be able to:  "Delete a his or her Messages"
	 *  
	 * 1. Negative test.
	 * 2. Business rule that is intended to broke: The actor is not authenticated
	 *  
	 **/
	@Test(expected = IllegalArgumentException.class)
	public void deleteMessageNegativeTwo(){
		Message message;
		int messagesBefore;
		int messagesAfter;
		Actor principal;
		
		super.authenticate(null);
		
		principal = this.actorService.findByPrincipal();
		
		
		messagesBefore = principal.getMessages().size();

		message = this.messageService.findOne(super.getEntityId("message3"));
		message.getTags().add("DELETED");
		
		this.messageService.delete(message);
		
		messagesAfter = principal.getMessages().size();
		
		Assert.isTrue(messagesBefore > messagesAfter);
		
		super.unauthenticate();
	}
}
