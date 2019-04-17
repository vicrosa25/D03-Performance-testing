
package controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CompanyService;
import utilities.Md5;
import domain.Company;
import forms.CompanyForm;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	@Autowired
	private CompanyService	companyService;
	
	@Autowired
	private ActorService	actorService;
	
	

	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	// List ------------------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Company> companies;

		try {
			companies = this.companyService.findAll();
			result = new ModelAndView("company/list");
			result.addObject("requestUri", "company/list.do");
			result.addObject("companies", companies);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}
		

		return result;
	}


	// Register ------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CompanyForm company;

		try {
			company = new CompanyForm();
			result = this.createEditModelAndView(company);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Save the new company ------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final CompanyForm companyForm, final BindingResult binding) {
		ModelAndView result;
		Company company;
		String password;
		
		company = this.companyService.reconstruct(companyForm, binding);
		
		if (!companyForm.isAccepted()) {
			binding.rejectValue("accepted", "register.terms.error", "Service terms must be accepted");
		}if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());

			result = this.createEditModelAndView(companyForm);
		}

		else
			try {
				password = Md5.encodeMd5(company.getUserAccount().getPassword());
				company.getUserAccount().setPassword(password);
				this.companyService.save(company);
				result = new ModelAndView("redirect:../security/login.do");
			} catch (final Throwable oops) {
				System.out.println(company);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = this.createEditModelAndView(companyForm);

				if (oops instanceof DataIntegrityViolationException)
					result = this.createEditModelAndView(companyForm, "company.duplicated.username");
				else
					result = this.createEditModelAndView(companyForm, "company.registration.error");
			}
		return result;
	}

	// Edit ------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Company company;
		Company pruned = new Company();

		try {
			company = this.companyService.findByPrincipal();

			// Fill attributes to edit with the original ones
			pruned.setAddress(company.getAddress());
			pruned.setCardNumber(company.getCardNumber());
			pruned.setCommercialName(company.getCommercialName());
			pruned.setEmail(company.getEmail());
			pruned.setId(company.getId());
			pruned.setName(company.getName());
			pruned.setPhoneNumber(company.getPhoneNumber());
			pruned.setPhoto(company.getPhoto());
			pruned.setSurname(company.getSurname());
			pruned.setVat(company.getVat());

			result = new ModelAndView("company/edit");
			result.addObject("company", pruned);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			oops.printStackTrace();
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// save edit ------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(final Company prune, final BindingResult binding) {
		ModelAndView result;
		final Company company;

		company = this.companyService.reconstruct(prune, binding);

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());

			result = new ModelAndView("company/edit");
			result.addObject("company", prune);
		}

		else
			try {
				this.companyService.save(company);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				System.out.println(company);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = this.editModelAndView(company, "company.registration.error");
			}
		return result;
	}

	// Generate pdf ------------------------------------------------------------------------------------
	@RequestMapping(value = "/generatePDF")
	public void generatePDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Company company;

		try {
			final ServletContext servletContext = request.getSession().getServletContext();
			final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			final String temperotyFilePath = tempDirectory.getAbsolutePath();
			company = this.companyService.findByPrincipal();

			String fileName = company.getName() + ".pdf";
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);

			this.actorService.generatePersonalInformationPDF(company, temperotyFilePath + "\\" + fileName);
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

	// Display ------------------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int companyId) {
		ModelAndView result;
		Company company;

		try {
			company = this.companyService.findOne(companyId);

			result = new ModelAndView("company/display");
			result.addObject("company", company);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			oops.printStackTrace();
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Delete ------------------------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		Company company;

		try {
			company = this.companyService.findByPrincipal();
			this.companyService.delete(company);
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

	// Ancillary methods -----------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final CompanyForm companyForm) {
		ModelAndView result;

		result = this.createEditModelAndView(companyForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CompanyForm companyForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("company/create");
		result.addObject("companyForm", companyForm);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editModelAndView(final Company company) {
		ModelAndView result;

		result = this.editModelAndView(company, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Company company, final String message) {
		ModelAndView result;

		result = new ModelAndView("company/edit");
		result.addObject("company", company);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}
}
