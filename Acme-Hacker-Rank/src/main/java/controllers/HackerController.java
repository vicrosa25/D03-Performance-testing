
package controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Hacker;
import forms.HackerForm;
import services.ActorService;
import services.HackerService;
import utilities.Md5;

@Controller
@RequestMapping("/hacker")
public class HackerController extends AbstractController {

	@Autowired
	private HackerService hackerService;
	
	
	@Autowired
	private ActorService actorService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	/********************************************
	 * Register a Hacker from a hackerForm object
	 ********************************************/

	// Register Form Object GEST------------------------------------------------------------------------------------
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
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Register Form Object POST ----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(HackerForm hackerForm, BindingResult binding) {
		ModelAndView result;
		String password;

		Hacker hacker = this.hackerService.reconstruct(hackerForm, binding);

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

	/********************************************
	 * Edit a Hacker from a pruned object
	 ********************************************/

	// Edit GET------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Hacker hacker;

		try {
			hacker = this.hackerService.findByPrincipal();

			// Set relations to null to use as a prune object
			hacker.setApplications(null);

			result = new ModelAndView("hacker/edit");
			result.addObject("hacker", hacker);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Edit POST ------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(Hacker prune, final BindingResult binding) {
		ModelAndView result;
		Hacker hacker;

		hacker = this.hackerService.reconstruct(prune, binding);

		if (binding.hasErrors()) {
			List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}
			result = new ModelAndView("hacker/edit");
			result.addObject("hacker", prune);
		} else {
			try {
				this.hackerService.save(hacker);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				System.out.println();
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = this.editModelAndView(hacker, "hacker.registration.error");
			}
		}
		return result;
	}

	/*********************
	 * Delete
	 *********************/
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		Hacker hacker;

		try {
			hacker = this.hackerService.findByPrincipal();
			this.hackerService.delete(hacker);
			result = new ModelAndView("redirect:/j_spring_security_logout");
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
	 * Generate PDG
	 *********************/
	@RequestMapping(value = "/generatePDF")
	public void generatePDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Hacker hacker;

		try {
			final ServletContext servletContext = request.getSession().getServletContext();
			final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			final String temperotyFilePath = tempDirectory.getAbsolutePath();
			hacker = this.hackerService.findByPrincipal();

			String fileName = hacker.getName() + ".pdf";
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);

			this.actorService.generatePersonalInformationPDF(hacker, temperotyFilePath + "\\" + fileName);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos = convertPDFToByteArrayOutputStream(temperotyFilePath + "\\" + fileName);
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			oops.printStackTrace();
		}
	}

	/*********************
	 * Ancillary Methods
	 *********************/
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
