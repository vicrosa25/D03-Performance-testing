package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InceptionRecordRepository;
import domain.Brotherhood;
import domain.InceptionRecord;
import domain.Url;

@Service
@Transactional
public class InceptionRecordService {

	// Manage Repository
	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;

	// Supporting services
	@Autowired
	private BrotherhoodService				brotherhoodService;


	// CRUD methods
	public InceptionRecord create() {
		final InceptionRecord result = new InceptionRecord();
		result.setDescription("Here goes the description of your inception");
		result.setTitle("Here goes the title of your inception");
		result.setPictures(new ArrayList<Url>());

		return result;
	}

	public InceptionRecord findOne(final int inceptionRecordID) {
		final InceptionRecord result = this.inceptionRecordRepository.findOne(inceptionRecordID);
		Assert.notNull(result);

		return result;
	}

	public Collection<InceptionRecord> findAll() {
		final Collection<InceptionRecord> result = this.inceptionRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public InceptionRecord save(final InceptionRecord inceptionRecord) {
		Assert.notNull(inceptionRecord);

		if (inceptionRecord.getId() != 0) {
			final Brotherhood principal = this.brotherhoodService.findByPrincipal();
			Assert.isTrue(principal.getHistory().getInceptionRecord().getId() == inceptionRecord.getId());
		}
		final InceptionRecord result = this.inceptionRecordRepository.save(inceptionRecord);

		return result;
	}

	public void delete(final InceptionRecord inceptionRecord) {
		Assert.notNull(inceptionRecord);
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(principal.getHistory().getInceptionRecord().getId() == inceptionRecord.getId());

		this.inceptionRecordRepository.delete(inceptionRecord);
	}
	/*** Other methods ***/
	
	public void flush() {
		this.inceptionRecordRepository.flush();
	}
}