package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PeriodRecordRepository;
import domain.Brotherhood;
import domain.History;
import domain.PeriodRecord;
import domain.Url;

@Service
@Transactional
public class PeriodRecordService {

	// Manage Repository
	@Autowired
	private PeriodRecordRepository	periodRecordRepository;

	// Supporting services
	@Autowired
	private BrotherhoodService		brotherhoodService;


	// CRUD methods
	public PeriodRecord create() {
		final PeriodRecord result = new PeriodRecord();
		result.setPictures(new ArrayList<Url>());
		
		return result;
	}

	public PeriodRecord findOne(final int periodRecordID) {
		final PeriodRecord result = this.periodRecordRepository.findOne(periodRecordID);
		Assert.notNull(result);

		return result;
	}

	public Collection<PeriodRecord> findAll() {
		final Collection<PeriodRecord> result = this.periodRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public PeriodRecord save(final PeriodRecord periodRecord) {
		boolean nuevo = false;
		Assert.notNull(periodRecord);
		final History principal = this.brotherhoodService.findByPrincipal().getHistory();
		if(periodRecord.getId() == 0){
			nuevo = true;
		}else{
			Assert.isTrue(principal.getPeriodRecords().contains(periodRecord));
		}
		final PeriodRecord result = this.periodRecordRepository.save(periodRecord);
		
		if(nuevo){
			principal.getPeriodRecords().add(result);
			principal.updateRecordNumber();
		}
		
		return result;
	}

	public void delete(final PeriodRecord periodRecord) {
		Assert.notNull(periodRecord);
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(principal.getHistory().getPeriodRecords().contains(periodRecord));
		
		principal.getHistory().getPeriodRecords().remove(periodRecord);
		principal.getHistory().updateRecordNumber();

		this.periodRecordRepository.delete(periodRecord);
	}
	/*** Other methods ***/
	
	public void flush() {
		this.periodRecordRepository.flush();
	}

}