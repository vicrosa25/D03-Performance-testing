
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.Actor;
import domain.Procession;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Manage Repository
	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	@Autowired
	private SponsorService		  sponsorService;
	
	@Autowired
	private ActorService		  actorService;

	@Autowired
	private ConfigurationsService	configurationsService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	@Qualifier("validator")
	private Validator				validator;

	
	// CRUD methods
	public Sponsorship create() {
		final Sponsorship result = new Sponsorship();

		Sponsor principal = this.sponsorService.findByPrincipal();
		
		result.setSponsor(principal);
		result.setActive(true);
		
		return result;
	}

	public Sponsorship findOne(final int sponsorshipID) {
		final Sponsorship result = this.sponsorshipRepository.findOne(sponsorshipID);
		Assert.notNull(result);

		return result;
	}

	public Collection<Sponsorship> findAll() {
		final Collection<Sponsorship> result = this.sponsorshipRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsorship save(Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getProcession().getStatus().equals("APPROVED"));
		Actor principal;
		
		principal = this.actorService.findByPrincipal();
		
		if (principal.getClass().equals(Sponsor.class)) {
			Assert.isTrue(this.sponsorService.findByPrincipal() == sponsorship.getSponsor());
		}
		
		Sponsorship result = this.sponsorshipRepository.save(sponsorship);

		return result;
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getSponsor() == this.sponsorService.findByPrincipal());

		sponsorship.getSponsor().getSponsorships().remove(sponsorship);

		this.sponsorshipRepository.delete(sponsorship);
	}
	public void deleteBrotherhood(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(this.brotherhoodService.findByPrincipal().getProcessions().contains(sponsorship.getProcession()));

		sponsorship.getSponsor().getSponsorships().remove(sponsorship);

		this.sponsorshipRepository.delete(sponsorship);
	}

	// Other business methods
	public Collection<Sponsorship> findBySponsor(int sponsorId) {
		Collection<Sponsorship> result = this.sponsorshipRepository.findBySponsor(sponsorId);
		Assert.notNull(result);
		return result;
	}

	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {
		final Sponsorship result = this.create();
		
		// updated atributes
		result.setBanner(sponsorship.getBanner());
		result.setCreditCard(sponsorship.getCreditCard());
		result.setProcession(sponsorship.getProcession());
		result.setTargetPage(sponsorship.getTargetPage());

		if (sponsorship.getId() != 0) {
			//not updated atributes
			result.setId(sponsorship.getId());
			result.setVersion(sponsorship.getVersion());
			result.setActive(sponsorship.getActive());
			result.setSponsor(this.sponsorService.findByPrincipal());
		}
		// credit card brand validation
		if (sponsorship.getCreditCard().getBrandName() == null) {
			binding.rejectValue("creditCard.brandName", "creditCard.error.brand", "Incorrect brand name");
		} else if (!this.configurationsService.getConfiguration().getBrandName().contains(sponsorship.getCreditCard().getBrandName())) {
			binding.rejectValue("creditCard.brandName", "creditCard.error.brand", "Incorrect brand name");
		}

		this.validator.validate(result, binding);

		return result;
	}

	public ArrayList<Sponsorship> findByProcession(Procession procession) {
		Assert.notNull(procession);
		ArrayList<Sponsorship> result = new ArrayList<Sponsorship>
			(this.sponsorshipRepository.findByProcession(procession.getId()));
		Assert.notNull(result);

		return result;
	}

	public Sponsorship updateCharge(Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		sponsorship.setCharge(sponsorship.getCharge() +
			this.configurationsService.getConfiguration().getFare()*this.configurationsService.getConfiguration().getVat());
		return this.sponsorshipRepository.save(sponsorship);
	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}
}