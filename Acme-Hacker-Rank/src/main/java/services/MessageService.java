
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Configurations;
import domain.Message;
import repositories.MessageRepository;
import security.LoginService;

@Service
@Transactional
public class MessageService {

	// Manage Repository
	@Autowired
	private MessageRepository		messageRepository;

	// Supporting devices
	@Autowired
	private ConfigurationsService	configurationsService;

	@Autowired
	private ActorService			actorService;


	// CRUD methods
	public Message create() {
		Message result = new Message();
		Calendar calendar = new GregorianCalendar();
		Collection<Actor> recipients = new ArrayList<Actor>();
		Collection<String> tags = new ArrayList<String>();

		result.setRecipients(recipients);
		result.setTags(tags);
		result.setMoment(calendar.getTime());

		return result;
	}

	public Message findOne(int messageID) {
		final Message result = this.messageRepository.findOne(messageID);
		Assert.notNull(result);

		return result;
	}
	
	
	public Collection<Message> findAll() {
		Collection<Message> result;
		
		result = this.messageRepository.findAll();
		return result;
	}

	public Message save(Message message) {
		Assert.notNull(message);
		Assert.notNull(LoginService.getPrincipal());
		Message result;
		Actor sender = this.actorService.findByPrincipal();

		if (message.getId() == 0) {
			Assert.notNull(message);

			if (!message.getSubject().contains("Change of status. Cambio del estado")) {
				message.setSender(sender);
			}

			Collection<Actor> recipients = message.getRecipients();
			Assert.notNull(recipients);
			Assert.notEmpty(recipients);

			Boolean spam = this.checkSpam(message);
			Boolean notification = message.getIsNotification();

			
			// Check for Notification or Spam
			if (notification) {
				message.getTags().add("SYSTEM");
			} 
			
			if (spam) {
				message.setIsSpam(true);
			}

			result = this.messageRepository.save(message);
			
			// Add message to the list of the sender an the recipients
			sender.getMessages().add(result);
			for (final Actor recipient : recipients) {
				recipient.getMessages().add(result);
			}

		} else {
			result = this.messageRepository.save(message);
		}
		return result;
	}

	public void delete(Message message) {
		Assert.notNull(message);

		Actor principal = this.actorService.findByPrincipal();
		
		// Checke the principal is the sender or a recipient
		Assert.isTrue(message.getRecipients().contains(principal) || message.getSender().equals(principal));

		
		// Check if the principal is the sender or a recipient
		Boolean actorRole;
		if (message.getSender() == null) {
			actorRole = true;
		} else if (message.getSender().equals(principal)) {
			actorRole = true;
		} else {
			actorRole = false;
		}

		// The message has Delete tag
		if (message.getTags().contains("DELETED")) {
			
			// The principal is a recipient
			if (!actorRole) {
				message.getRecipients().remove(principal);
				principal.getMessages().remove(message);
				this.actorService.save(principal);
			
			// the principal is the sender
			} else {
				message.setSender(null);
				principal.getMessages().remove(message);
				this.actorService.save(principal);
			}
			
			// Only when the message has not sender and recipient is deleted from the system
			if ((message.getRecipients().size() == 0) && (message.getSender() == null)) {
				this.messageRepository.delete(message);
			} else {
				this.messageRepository.save(message);
			}
			
		//The message has NOT Deleted tag
		} else {
			message.getTags().add("DELETED");
			this.messageRepository.save(message);
		}
	}

	// Other methods
	private Boolean checkSpam(final Message message) {
		Boolean spam = false;

		final Configurations configuration = this.configurationsService.getConfiguration();
		final Collection<String> spamWords = configuration.getSpamWords();
		for (final String word : spamWords) {
			if (message.getSubject().contains(word)) {
				spam = true;
				break;
			}
		}
		if (!spam) {
			for (final String word : spamWords) {
				if (message.getBody().contains(word)) {
					spam = true;
					break;
				}
			}
		}

		return spam;
	}

	public Collection<Message> findAllBySender(int senderID) {
		final Collection<Message> result = this.messageRepository.findAllBySender(senderID);
		Assert.notNull(result);

		return result;
	}

}
