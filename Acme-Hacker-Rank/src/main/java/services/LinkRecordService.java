package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LinkRecordRepository;
import domain.Brotherhood;
import domain.History;
import domain.LinkRecord;

@Service
@Transactional
public class LinkRecordService {

	// Manage Repository
	@Autowired
	private LinkRecordRepository	linkRecordRepository;

	// Supporting services
	@Autowired
	private BrotherhoodService		brotherhoodService;


	// CRUD methods
	public LinkRecord create() {
		final LinkRecord result = new LinkRecord();

		return result;
	}

	public LinkRecord findOne(final int linkRecordID) {
		final LinkRecord result = this.linkRecordRepository.findOne(linkRecordID);
		Assert.notNull(result);

		return result;
	}

	public Collection<LinkRecord> findAll() {
		final Collection<LinkRecord> result = this.linkRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public LinkRecord save(final LinkRecord linkRecord) {
		boolean nuevo = false;
		Assert.notNull(linkRecord);
		final History principal = this.brotherhoodService.findByPrincipal().getHistory();
		if(linkRecord.getId() == 0){
			nuevo = true;
		}else{
			Assert.isTrue(principal.getLinkRecords().contains(linkRecord));
		}
		final LinkRecord result = this.linkRecordRepository.save(linkRecord);
		
		if(nuevo){
			principal.getLinkRecords().add(result);
			principal.updateRecordNumber();
		}
		
		return result;
	}

	public void delete(final LinkRecord linkRecord) {
		Assert.notNull(linkRecord);
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(principal.getHistory().getLinkRecords().contains(linkRecord));
		
		principal.getHistory().getLinkRecords().remove(linkRecord);

		this.linkRecordRepository.delete(linkRecord);
	}
	/*** Other methods ***/
	
	public void flush() {
		this.linkRecordRepository.flush();
	}

}