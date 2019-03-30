package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import domain.Brotherhood;
import domain.History;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;

@Service
@Transactional
public class HistoryService {

	// Manage Repository
	@Autowired
	private HistoryRepository		historyRepository;

	@Autowired
	private InceptionRecordService	inceptionRecordService;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	// Supporting services

	// CRUD methods
	public History create() {
		final History result = new History();
		result.setInceptionRecord(this.inceptionRecordService.create());
		result.setLegalRecords(new ArrayList<LegalRecord>());
		result.setLinkRecords(new ArrayList<LinkRecord>());
		result.setPeriodRecords(new ArrayList<PeriodRecord>());
		result.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());
		return result;
	}
	public Collection<History> findAll() {

		final Collection<History> result = this.historyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public History findOne(final int historyID) {
		final History result = this.historyRepository.findOne(historyID);
		Assert.notNull(result);

		return result;
	}

	public History save(final History history) {
		Assert.notNull(history);
		
		if (history.getId() != 0) {
			final Brotherhood principal = this.brotherhoodService.findByPrincipal();
			Assert.isTrue(principal.getHistory().getId() == history.getId());
		} else {
			history.setInceptionRecord(this.inceptionRecordService.save(history.getInceptionRecord()));
		}
		return this.historyRepository.save(history);
	}

	public void delete(final History history) {
		Assert.notNull(history);
		
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(principal.getHistory().getId() == history.getId());
		
		this.historyRepository.delete(history);
	}

	// Other business methods
	public History findByBrotherhood(final int brotherhoodID) {
		final History result = this.historyRepository.findByBrotherhood(brotherhoodID);
		Assert.notNull(result);
		return result;
	}

}