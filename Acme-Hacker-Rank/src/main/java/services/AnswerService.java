
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Answer;
import repositories.AnswerRepository;

@Service
@Transactional
public class AnswerService {

	@Autowired
	private AnswerRepository answerRepository;


	// CRUD methods
	public Answer create() {
		Answer result;
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
		Assert.notNull(Answer);
		Answer result = this.answerRepository.save(Answer);

		return result;
	}

	public void delete(final Answer Answer) {
		Assert.notNull(Answer);

		this.answerRepository.delete(Answer);
	}

}
