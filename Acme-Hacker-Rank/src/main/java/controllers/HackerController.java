
package controllers;

import org.hibernate.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Hacker;
import forms.HackerForm;
import services.HackerService;
import utilities.Md5;

@Controller
@RequestMapping("/hacker")
public class HackerController extends AbstractController {

	@Autowired
	private HackerService hackerService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	// Register ------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		HackerForm hackerForm;

		try {
			//Se crea un hackerform vacio
			hackerForm = new HackerForm();
			result = new ModelAndView("hacker/create");
			result.addObject("hackerForm", hackerForm);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Save the new hacker ----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final HackerForm hackerForm, final BindingResult binding) {
		ModelAndView result;
		String password;

		final Hacker hacker = this.hackerService.reconstruct(hackerForm, binding);

		if (!hackerForm.isAccepted()) {
			binding.rejectValue("accepted", "register.terms.error", "Service terms must be accepted");
		}
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(hackerForm);
		} else {
			try {
				password = Md5.encodeMd5(hacker.getUserAccount().getPassword());
				hacker.getUserAccount().setPassword(password);
				this.hackerService.save(hacker);
				result = new ModelAndView("redirect:../security/login.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(hackerForm, "hacker.commit.error");
			}
		}
		return result;
	}

	// Ancillary methods -----------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(HackerForm hackerForm) {
		ModelAndView result;

		result = this.createEditModelAndView(hackerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(HackerForm hackerForm, String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("hacker/create");
		result.addObject("hackerForm", hackerForm);

		result.addObject("message", messageCode);

		return result;

	}

	protected ModelAndView editModelAndView(Hacker hacker) {
		ModelAndView result;

		result = this.editModelAndView(hacker, null);

		return result;
	}

	protected ModelAndView editModelAndView(Hacker hacker, String message) {
		ModelAndView result;

		result = new ModelAndView("hacker/edit");
		result.addObject("hacker", hacker);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

}
