package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SegmentRepository;
import domain.Brotherhood;
import domain.Path;
import domain.Segment;

@Service
@Transactional
public class SegmentService {

	// Manage Repository
	@Autowired
	private SegmentRepository	segmentRepository;

	// Supporting services
	@Autowired
	private BrotherhoodService	brotherhoodService;


	// CRUD methods
	public Segment create() {
		final Segment result = new Segment();
		
		return result;
	}

	public Segment findOne(final int segmentID) {
		final Segment result = this.segmentRepository.findOne(segmentID);
		Assert.notNull(result);

		return result;
	}

	public Collection<Segment> findAll() {
		final Collection<Segment> result = this.segmentRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Segment save(final Segment segment) {
		Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.notNull(segment);
		Assert.isTrue(principal.getProcessions().contains(segment.getPath().getProcession()));

		final Segment result = this.segmentRepository.save(segment);
		
		if (result.getNumber() < result.getPath().getSegments().size() - 1) {
				Segment next = this.findByNumber(result.getPath(), result.getNumber() + 1);
				next.setOriginLatitude(result.getDestinationLatitude());
				next.setOriginLongitude(result.getDestinationLongitude());
				this.segmentRepository.save(next);
		}
		return result;
	}

	public void delete(final Segment segment) {
		Assert.notNull(segment);
		Path path = segment.getPath();
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(principal.getProcessions().contains(path.getProcession()));
		
		path.getSegments().remove(segment);

		for (Segment s : new ArrayList<Segment>(path.getSegments())) {
			if (s.getNumber() - 1 == segment.getNumber()) {
				s.setOriginLatitude(segment.getOriginLatitude());
				s.setOriginLongitude(segment.getOriginLongitude());
				s.setOriginTime(segment.getOriginTime());
				s.setNumber(segment.getNumber());
			} else if (s.getNumber() > segment.getNumber()) {
				s.setNumber(s.getNumber() - 1);
			}
		}
		this.segmentRepository.delete(segment);
	}
	/*** Other methods ***/

	public Segment getLastSegment(Path path) {
		Assert.notNull(path);
		Segment result = null;
		int i = -1;
		for (Segment segment : path.getSegments()) {
			if (segment.getNumber() > i) {
				result = segment;
			}
		}
		return result;
	}
	
	public Segment findByNumber(Path path, int number) {
		Segment result = this.segmentRepository.findByNumber(path.getId(), number);
		Assert.notNull(result, "Not segment found with index " + number + " in path " + path.getId());
		return result;
	}

	public void flush() {
		this.segmentRepository.flush();
	}

}