
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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

import services.BrotherhoodService;
import services.ProcessionService;
import services.SponsorshipService;
import domain.Brotherhood;
import domain.Procession;
import domain.Sponsorship;

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	// Display ------------------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int processionId) {
		ModelAndView result;
		Procession procession;

		try {
			procession = this.processionService.findOne(processionId);
			ArrayList<Sponsorship> sponsorships = sponsorshipService.findByProcession(procession);

			result = new ModelAndView("procession/display");
			result.addObject("procession", procession);
			if (!sponsorships.isEmpty()) {
				Random rand = new Random();
				Sponsorship sponsorship = sponsorships.get(rand.nextInt(sponsorships.size()));

				sponsorship = this.sponsorshipService.updateCharge(sponsorship);
				result.addObject("sponsorship", sponsorship);
			}

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// List
	// ------------------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Brotherhood principal;
		Collection<Procession> processions;

		try {
			principal = this.brotherhoodService.findByPrincipal();
			processions = this.processionService.getProcessionsSortedByStatus(principal.getId());
			result = new ModelAndView("procession/list");
			result.addObject("processions", processions);
			result.addObject("uri", "procession/list");
			result.addObject("bro", 1);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Brotherhood List ------------------------------------------------------------------------------------
	@RequestMapping(value = "/brotherhoodList", method = RequestMethod.GET)
	public ModelAndView brotherhoodList(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Collection<Procession> processions;

		try {
			processions = this.processionService.findByBrotherhoodNotDraftAndApproved(brotherhoodId);

			result = new ModelAndView("procession/list");
			result.addObject("processions", processions);
			result.addObject("uri", "procession/brotherhoodList");
		
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Create
	// ------------------------------------------------------------------------------------
	@RequestMapping(value = "brotherhood/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Procession procession;

		procession = this.processionService.create();

		result = this.createEditModelAndView(procession);

		return result;
	}

	// Edition -------------------------------------------------------------
	@RequestMapping(value = "brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId) {
		ModelAndView result;
		Procession procession;

		try {
			Brotherhood principal = this.brotherhoodService.findByPrincipal();
			procession = this.processionService.findOne(processionId);

			Assert.notNull(procession);
			Assert.isTrue(principal.getProcessions().contains(procession));
			Assert.isTrue(procession.getDraftMode());
			
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
			return result;
		}

		result = this.createEditModelAndView(procession);

		return result;
	}

	// Save -------------------------------------------------------------
	@RequestMapping(value = "brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Procession pruned, final BindingResult binding) {
		ModelAndView result;
		Procession constructed;

		constructed = this.processionService.reconstruct(pruned, binding);

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}
			result = this.createEditModelAndView(pruned);
		} else {
			try {
				this.processionService.save(constructed);
				result = new ModelAndView("redirect:../list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = this.createEditModelAndView(pruned, "procession.registration.error");
			}
		}

		return result;
	}

	// Delete
	// --------------------------------------------------------------------------------------
	@RequestMapping(value = "brotherhood/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int processionId) {
		ModelAndView result = null;
		Procession procession;
		
		try {
			Brotherhood principal = this.brotherhoodService.findByPrincipal();
			procession = this.processionService.findOne(processionId);
			Assert.notNull(procession);
			Assert.isTrue(principal.getProcessions().contains(procession));
			this.processionService.delete(procession);
			result = new ModelAndView("redirect:../list.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			oops.printStackTrace();
			result = this.forbiddenOpperation();
		}
		return result;
	}

	// Copy ------------------------------------------------------------------------------------
	@RequestMapping(value = "brotherhood/copy", method = RequestMethod.GET)
	public ModelAndView copy(@RequestParam final int processionId) {
		ModelAndView result;
		try {
			this.processionService.copy(processionId);
			
			result = new ModelAndView("redirect:../list.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.forbiddenOpperation();
		}
		return result;
	}

	// Ancillary Methods
	// -----------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Procession procession) {
		ModelAndView result;

		result = this.createEditModelAndView(procession, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Procession procession, final String message) {
		ModelAndView result;

		result = new ModelAndView("procession/brotherhood/edit");
		result.addObject("procession", procession);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}
}
