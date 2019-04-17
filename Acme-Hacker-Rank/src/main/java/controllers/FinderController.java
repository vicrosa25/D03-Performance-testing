
package controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.HackerService;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Controller
@RequestMapping("/finder/hacker")
public class FinderController extends AbstractController {

	@Autowired
	private FinderService	finderService;

	@Autowired
	private HackerService	hackerService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	// Clear -----------------------------------------------------------
	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public ModelAndView clear() {
		ModelAndView result;
		try {
			final Finder clear = this.finderService.clear(this.hackerService.findByPrincipal().getFinder());
			result = new ModelAndView("position/list");
			result.addObject("positions", clear.getPositions());
			result.addObject("requestURI", "finder/hacker/result.do");

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			oops.printStackTrace();
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// Edit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Hacker hacker = this.hackerService.findByPrincipal();
		final Finder finder = hacker.getFinder();

		result = this.createEditModelAndView(finder);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Finder saved;
		if (finder.getMaxSalary() != null && finder.getMinSalary() != null)
			if (finder.getMaxSalary() < finder.getMinSalary())
				binding.rejectValue("maxSalary", "finder.maxSalary.error", "The maximum salary cannot be lower than the minimun");

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());
			result = this.createEditModelAndView(finder);

		} else
			try {
				saved = this.finderService.checkChanges(finder);

				result = new ModelAndView("position/list");
				result.addObject("positions", saved.getPositions());
				result.addObject("requestURI", "finder/hacker/result.do");
			} catch (final Throwable oops) {
				System.out.println(finder);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = this.createEditModelAndView(finder);
			}
		return result;
	}

	// Search -----------------------------------------------------------
	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public ModelAndView result() {
		ModelAndView result;

		try {
			final Hacker hacker = this.hackerService.findByPrincipal();
			final Finder finder = hacker.getFinder();
			final Collection<Position> positions = this.finderService.getResults(finder);

			result = new ModelAndView("position/list");
			result.addObject("positions", positions);
			result.addObject("requestURI", "finder/hacker/result.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;

		result = new ModelAndView("finder/hacker/edit");
		result.addObject("finder", finder);
		result.addObject("message", message);

		return result;
	}
}
