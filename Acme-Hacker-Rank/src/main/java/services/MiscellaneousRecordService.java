package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Brotherhood;
import domain.History;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Manage Repository
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services
	@Autowired
	private BrotherhoodService				brotherhoodService;


	// CRUD methods
	public MiscellaneousRecord create() {
		final MiscellaneousRecord result = new MiscellaneousRecord();

		return result;
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordID) {
		final MiscellaneousRecord result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordID);
		Assert.notNull(result);

		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {
		final Collection<MiscellaneousRecord> result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		boolean nuevo = false;
		Assert.notNull(miscellaneousRecord);
		final History principal = this.brotherhoodService.findByPrincipal().getHistory();
		if(miscellaneousRecord.getId() == 0){
			nuevo = true;
		}else{
			Assert.isTrue(principal.getMiscellaneousRecords().contains(miscellaneousRecord));
		}
		final MiscellaneousRecord result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		
		if(nuevo){
			principal.getMiscellaneousRecords().add(result);
			principal.updateRecordNumber();
		}
		
		return result;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(principal.getHistory().getMiscellaneousRecords().contains(miscellaneousRecord));
		
		principal.getHistory().getMiscellaneousRecords().remove(miscellaneousRecord);
		principal.getHistory().updateRecordNumber();
		
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}
	/*** Other methods ***/
	
	public void flush() {
		this.miscellaneousRecordRepository.flush();
	}

}