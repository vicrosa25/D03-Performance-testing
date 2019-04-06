
package controllers;

import java.util.Collection;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Application;
import services.ApplicationService;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	@Autowired
	private ApplicationService applicationService;

	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	// List ------------------------------------------------------------------------------------
	@RequestMapping(value = "/hacker/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> apps;

		try {
			apps = this.applicationService.findByHacker();
			result = new ModelAndView("application/hacker/list");
			result.addObject("requestUri", "application/hacker/list.do");
			result.addObject("apps", apps);
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
		}

		return result;
	}

	/*********************
	 * Ancillary Methods
	 *********************/
	protected ModelAndView createEditModelAndView(Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Application application, String message) {
		ModelAndView result;

		result = new ModelAndView("company/create");
		result.addObject("application", application);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView editModelAndView(Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView editModelAndView(Application application, String message) {
		ModelAndView result;

		result = new ModelAndView("company/edit");
		result.addObject("application", application);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

}
