
package controllers;

import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Procession;
import domain.Sponsor;
import domain.Sponsorship;
import services.ProcessionService;
import services.SponsorService;
import services.SponsorshipService;

@Controller
@RequestMapping("/sponsorship")
public class SponsorshipController extends AbstractController {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private ProcessionService	processionService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		JOptionPane.showMessageDialog(null, "Forbidden operation");
		return new ModelAndView("redirect:/");
	}

	// List -------------------------------------------------------------
	@RequestMapping(value = "/sponsor/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		try {
			final Sponsor principal = this.sponsorService.findByPrincipal();

			sponsorships = this.sponsorshipService.findBySponsor(principal.getId());

			result = new ModelAndView("sponsorship/sponsor/list");
			result.addObject("sponsorships", sponsorships);
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
		}
		return result;
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/sponsor/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Sponsorship sponsorship;
		try {
			sponsorship = this.sponsorshipService.create();
			result = this.createEditModelAndView(sponsorship);
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Edit -------------------------------------------------------------
	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		try {
			final Sponsor principal = this.sponsorService.findByPrincipal();
			sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			Assert.isTrue(this.sponsorshipService.findBySponsor(principal.getId()).contains(sponsorship));
			result = this.createEditModelAndView(sponsorship);
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
		}
		return result;
	}

	// Save -------------------------------------------------------------
	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Sponsorship prune, final BindingResult binding) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.reconstruct(prune, binding);

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());
			result = this.createEditModelAndView(prune);
		}
		else
			try {
				this.sponsorshipService.save(sponsorship);
				result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = this.createEditModelAndView(prune, "profile.commit.error");
			}
		return result;
	}

	// Deactivate ------------------------------------------------------
	@RequestMapping(value = "/sponsor/deactivate", method = RequestMethod.GET)
	public ModelAndView deactivate(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		try {
			sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			Assert.isTrue(this.sponsorService.findByPrincipal() == sponsorship.getSponsor());
			Assert.isTrue(sponsorship.getActive());
			sponsorship.setActive(false);
			this.sponsorshipService.save(sponsorship);
			result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Activate ------------------------------------------------------
	@RequestMapping(value = "/sponsor/activate", method = RequestMethod.GET)
	public ModelAndView activate(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		try {
			sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			Assert.isTrue(this.sponsorService.findByPrincipal() == sponsorship.getSponsor());
			Assert.isTrue(!sponsorship.getActive());
			sponsorship.setActive(true);
			this.sponsorshipService.save(sponsorship);
			result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// charge -------------------------------------------------------------
	@RequestMapping(value = "/charge", method = RequestMethod.GET)
	public ModelAndView charge(@RequestParam final int sponsorshipId, @RequestParam final int processionId) {
		ModelAndView result;
		Sponsorship sponsorship;
		Procession procession;

		try {
			sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			result = this.createEditModelAndView(sponsorship);
			procession = this.processionService.findOne(processionId);

			Assert.isTrue(sponsorship.getProcession() == procession);

			result = new ModelAndView("procession/display");
			result.addObject("procession", procession);
			result.addObject("charged", "sponsorship.charged");
			result.addObject("sponsorship", sponsorship);
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {
		ModelAndView result;

		result = new ModelAndView("sponsorship/sponsor/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("processions", this.processionService.findAllAccepted());
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

}
