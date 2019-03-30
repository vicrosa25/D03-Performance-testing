package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProclaimRepository;
import domain.Chapter;
import domain.Proclaim;

@Service
@Transactional
public class ProclaimService {
	
	// Manage Repository
	@Autowired
	private ProclaimRepository	proclaimRepository;
	
	@Autowired
	private ChapterService		chapterService;
	
	
	// CRUD methods
	public Proclaim create() {
		Proclaim result = new Proclaim();
		
		result.setMoment(new Date());

		return result;
	}
	
	
	public Proclaim findOne(int proclaimID) {
		final Proclaim result = this.proclaimRepository.findOne(proclaimID);
		Assert.notNull(result);

		return result;
	}
	
	
	public Collection<Proclaim> findAll() {
		return this.proclaimRepository.findAll();
	}
	
	
	public Proclaim save(Proclaim proclaim) {
		Proclaim result;
		Assert.notNull(proclaim);
		Assert.isTrue(proclaim.getId() == 0);
		Chapter principal = this.chapterService.findByPrincipal();

		result = this.proclaimRepository.save(proclaim);
		principal.getProclaims().add(result);

		return result;
	}
	
	
	// Other methods
	
	public Collection<Proclaim> findByChapter(int chapterId) {
		return this.proclaimRepository.findByChapter(chapterId);
	}

	public void flush() {
		this.proclaimRepository.flush();

	}
}
