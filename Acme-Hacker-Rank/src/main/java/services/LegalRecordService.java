package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LegalRecordRepository;
import domain.Brotherhood;
import domain.History;
import domain.LegalRecord;

@Service
@Transactional
public class LegalRecordService {

	// Manage Repository
	@Autowired
	private LegalRecordRepository	legalRecordRepository;

	// Supporting services
	@Autowired
	private BrotherhoodService		brotherhoodService;


	// CRUD methods
	public LegalRecord create() {
		final LegalRecord result = new LegalRecord();

		return result;
	}

	public LegalRecord findOne(final int legalRecordID) {
		final LegalRecord result = this.legalRecordRepository.findOne(legalRecordID);
		Assert.notNull(result);

		return result;
	}

	public Collection<LegalRecord> findAll() {
		final Collection<LegalRecord> result = this.legalRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public LegalRecord save(final LegalRecord legalRecord) {
		boolean nuevo = false;
		Assert.notNull(legalRecord);
		final History principal = this.brotherhoodService.findByPrincipal().getHistory();
		if(legalRecord.getId() == 0){
			nuevo = true;
		}else{
			Assert.isTrue(principal.getLegalRecords().contains(legalRecord));
		}
		final LegalRecord result = this.legalRecordRepository.save(legalRecord);
		
		if(nuevo){
			principal.getLegalRecords().add(result);
			principal.updateRecordNumber();
		}
		
		return result;
	}

	public void delete(final LegalRecord legalRecord) {
		Assert.notNull(legalRecord);
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(principal.getHistory().getLegalRecords().contains(legalRecord));
		
		principal.getHistory().getLegalRecords().remove(legalRecord);
		principal.getHistory().updateRecordNumber();

		this.legalRecordRepository.delete(legalRecord);
	}
	/*** Other methods ***/
	
	public void flush() {
		this.legalRecordRepository.flush();
	}

}