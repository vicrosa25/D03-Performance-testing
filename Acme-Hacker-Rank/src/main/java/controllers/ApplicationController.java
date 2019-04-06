
package controllers;

import java.util.Collection;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Application;
import domain.Hacker;
import services.ActorService;
import services.ApplicationService;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	@Autowired
	private ApplicationService  applicationService;
	
	@Autowired
	private ActorService		actorService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	/*********************
	 * List Hacker Apps
	 *********************/
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
	 * Display an App
	 *********************/
	@RequestMapping(value = "/hacker/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int appId) {
		ModelAndView result;
		Application application;
		Actor principal;
		Hacker hacker;

		try {
			application = this.applicationService.findOne(appId);
			principal = this.actorService.findByPrincipal();
			
			// Check the principal is an Hacker and owns app
			Assert.isInstanceOf(Hacker.class, principal);
			hacker = (Hacker) principal;
			Assert.isTrue(hacker.getApplications().contains(application));
			

			result = new ModelAndView("application/hacker/display");
			result.addObject("requestUri", "application/hacker/display.do");
			result.addObject("application", application);
			result.addObject("position", application.getPosition());
			result.addObject("problem", application.getProblem());
			result.addObject("attachments", application.getProblem().getattachments());

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
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
