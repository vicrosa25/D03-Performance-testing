
package controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

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

import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Curricula;
import domain.Hacker;
import domain.Position;
import services.ActorService;
import services.ApplicationService;
import services.CompanyService;
import services.CurriculaService;
import services.HackerService;
import services.PositionService;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private CurriculaService	curriculaService;


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
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int appId) {
		ModelAndView result;
		Application application;
		Actor principal;
		Hacker hacker;
		Company company;

		try {
			application = this.applicationService.findOne(appId);
			principal = this.actorService.findByPrincipal();
			result = new ModelAndView("application/display");

			// Check the principal is an Hacker and owns app
			if (principal instanceof Hacker) {
				hacker = (Hacker) principal;
				Assert.isTrue(hacker.getApplications().contains(application));
				result.addObject("requestUri", "application/hacker/display.do");
			}
			if (principal instanceof Company) {
				company = (Company) principal;
				Assert.isTrue(this.applicationService.findByCompany(company).contains(application));
				result.addObject("requestUri", "application/company/display.do");
			}

			result.addObject("application", application);

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	/*****************************************
	 * Create Application for a Position GET
	 ****************************************/
	@RequestMapping(value = "/hacker/create", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Application application;

		application = this.applicationService.create();
		result = this.createEditModelAndView(application);

		return result;
	}

	/********************************************
	 * Create Application for a Position POST
	 *******************************************/
	@RequestMapping(value = "/hacker/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Application application, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}
			result = this.createEditModelAndView(application);
		}
		else
			try {
				Curricula copy = this.curriculaService.copyCurricula(application.getCurricula());
				application.setCurricula(copy);
				this.applicationService.save(application);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(application, "application.commit.error");
			}
		return result;
	}
	
	
	/*****************************************
	 * Update Application GET
	 ****************************************/
	@RequestMapping(value = "/hacker/update", method = RequestMethod.GET)
	public ModelAndView update(@RequestParam int appId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(appId);
		
		result = this.editModelAndView(application);

		return result;
	}

	/********************************************
	 * Update Application POST
	 *******************************************/
	@RequestMapping(value = "/hacker/update", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid Application application, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}
			result = this.editModelAndView(application);
		}
		else
			try {
				this.applicationService.update(application);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.editModelAndView(application, "application.commit.error");
			}
		return result;
	}

	/*********************
	 * Company Methods
	 *********************/
	// List ------------------------------------------------------------------------------------
	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView companyList() {
		ModelAndView result;
		Collection<Application> apps;

		try {
			apps = this.applicationService.findByCompany(this.companyService.findByPrincipal());
			result = new ModelAndView("application/company/list");
			result.addObject("requestUri", "application/company/list.do");
			result.addObject("apps", apps);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			oops.printStackTrace();
			result = this.forbiddenOpperation();
		}

		return result;
	}
	
	// Accept ------------------------------------------------------------------------------------
	@RequestMapping(value = "/company/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam int applicationId) {
		ModelAndView result;
		Application application;

		try {
			application = this.applicationService.findOne(applicationId);
			Assert.isTrue(this.applicationService.findByCompany(this.companyService.findByPrincipal()).contains(application));
			Assert.isTrue(application.getStatus().equals("SUBMITTED"));

			this.applicationService.accept(application);
			result = new ModelAndView("redirect:/application/company/list.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			oops.printStackTrace();
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// reject ------------------------------------------------------------------------------------
	@RequestMapping(value = "/company/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam int applicationId) {
		ModelAndView result;
		Application application;

		try {
			application = this.applicationService.findOne(applicationId);
			Assert.isTrue(this.applicationService.findByCompany(this.companyService.findByPrincipal()).contains(application));
			Assert.isTrue(application.getStatus().equals("SUBMITTED"));

			this.applicationService.reject(application);
			result = new ModelAndView("redirect:/application/company/list.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			oops.printStackTrace();
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
		Collection<Position> positions;
		Collection<Curricula> curriculas;

		positions = this.positionService.findAll();
		curriculas = this.curriculaService.findAllPrincipalNotApplied();

		for (Application app : this.hackerService.findByPrincipal().getApplications()) {
			positions.remove(app.getPosition());
		}

		if (positions.isEmpty())
			message = "application.posisitons.empty";

		result = new ModelAndView("application/hacker/create");
		result.addObject("application", application);
		result.addObject("positions", positions);
		result.addObject("curriculas", curriculas);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editModelAndView(Application application) {
		ModelAndView result;

		result = this.editModelAndView(application, null);

		return result;
	}

	protected ModelAndView editModelAndView(Application application, String message) {
		ModelAndView result;

		result = new ModelAndView("application/hacker/update");
		result.addObject("application", application);
		result.addObject("problem", application.getProblem());
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

}
