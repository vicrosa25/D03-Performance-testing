
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Answer;
import domain.Hacker;
import repositories.AnswerRepository;

@Service
@Transactional
public class AnswerService {

	@Autowired
	private AnswerRepository	answerRepository;

	@Autowired
	private ActorService		actorService;


	// CRUD methods
	public Answer create() {
		Actor principal;
		Answer result;

		// Principal must be a Hacker
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Hacker.class, principal);

		result = new Answer();

		return result;
	}

	public Answer findOne(int AnswerId) {
		Answer result = this.answerRepository.findOne(AnswerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Answer> findAll() {
		Collection<Answer> result = this.answerRepository.findAll();
		Assert.notNull(result);
		Assert.notEmpty(result);

		return result;
	}

	public Answer save(Answer Answer) {
		Actor principal;
		Assert.notNull(Answer);

		// Principal must be a Hacker
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Hacker.class, principal);
		
		Answer result = this.answerRepository.save(Answer);

		return result;
	}

	public void delete(final Answer Answer) {
		Assert.notNull(Answer);

		this.answerRepository.delete(Answer);
	}

}
