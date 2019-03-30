
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EnrolRepository;
import domain.Enrol;
import domain.Message;
import domain.Position;

@Service
@Transactional
public class EnrolService {

	// Managed repository
	// -------------------------------------------------------------
	@Autowired
	private EnrolRepository	enrolRepository;

	@Autowired
	private MessageService		messageService;


	// Supporting services
	// -------------------------------------------------------------

	// CRUD methods
	// ------------------------------------------------------------------
	public Enrol create() {
		final Enrol result = new Enrol();

		result.setMoment(new Date());
		result.setPositions(new ArrayList<Position>());

		return result;
	}

	public Enrol findOne(final int enrolId) {
		final Enrol result = this.enrolRepository.findOne(enrolId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Enrol> findAll() {
		final Collection<Enrol> result = this.enrolRepository.findAll();
		Assert.notEmpty(result);
		Assert.notNull(result);

		return result;
	}

	public Enrol save(final Enrol enrol) {
		Assert.notNull(enrol);
		final Enrol result = this.enrolRepository.save(enrol);

		return result;
	}

	public void delete(final Enrol enrol) {
		Assert.notNull(enrol);

		ArrayList<Position> positions = new ArrayList<Position>(enrol.getPositions());
		positions.get(0).getEnrol().remove(enrol);

		this.enrolRepository.delete(enrol);

	}

	// Other methods
	// -----------------------------------------------------------------

	public void automaticNotification(final Enrol enrol) {
		final Message message = this.messageService.create();
		ArrayList<Position> positions = new ArrayList<Position>(enrol.getPositions());
		message.setBody("The brotherhood " + enrol.getBrotherhood().getTitle() + " has enrolled you with the position "
		+ positions.get(0).getEnglishName() + ".");

		message.setIsNotification(true);
		message.setPriority("MEDIUM");
		message.setSubject("Enroll to "+enrol.getBrotherhood().getTitle());
		message.getRecipients().add(enrol.getMember());

		this.messageService.save(message);
	}
	public Enrol findEnrolByBrotherhoodAndMember(int brotherhoodId, int memberId){
		Enrol result = this.enrolRepository.findEnrolByBrotherhoodAndMember(brotherhoodId, memberId);
		Assert.notNull(result);
		return result;
	}
}
