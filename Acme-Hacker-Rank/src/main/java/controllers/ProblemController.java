
package controllers;

import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
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

import services.CompanyService;
import services.ProblemService;
import domain.Problem;
import domain.Url;

@Controller
@RequestMapping("/problem")
public class ProblemController extends AbstractController {

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private CompanyService	companyService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		JOptionPane.showMessageDialog(null, "Forbidden operation");
		return new ModelAndView("redirect:/");
	}

	// principal List -------------------------------------------------------------
	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView principalList() {
		ModelAndView result;
		Collection<Problem> problems;
		try {

			problems = this.companyService.findByPrincipal().getProblems();

			result = new ModelAndView("problem/list");
			result.addObject("problems", problems);
			result.addObject("requestURI", "problem/company/list.do");

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// Display -------------------------------------------------------------
	@RequestMapping(value = "/company/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int problemId) {
		ModelAndView result;
		Problem problem;
		try {
			problem = this.problemService.findOne(problemId);
			Assert.isTrue(problem.getCompany() == this.companyService.findByPrincipal());

			result = new ModelAndView("problem/company/display");
			result.addObject("problem", problem);

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// create -------------------------------------------------------------
	@RequestMapping(value = "/company/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Problem problem;
		try {

			problem = this.problemService.create();

			result = this.createEditModelAndView(problem);

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// edit -------------------------------------------------------------
	@RequestMapping(value = "/company/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int problemId) {
		ModelAndView result;
		Problem problem;

		try {
			problem = this.problemService.findOne(problemId);
			Assert.isTrue(!problem.getFinalMode());
			Assert.isTrue(problem.getCompany() == this.companyService.findByPrincipal());

			result = this.createEditModelAndView(problem);

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// save ------------------------------------------------------------------------------------
	@RequestMapping(value = "company/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Problem problem, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());

			result = this.createEditModelAndView(problem);
		}

		else
			try {
				this.problemService.save(problem);
				result = new ModelAndView("redirect:/problem/company/list.do");
			} catch (final Throwable oops) {
				System.out.println(problem);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = this.createEditModelAndView(problem, "company.registration.error");
			}
		return result;
	}

	// delete ------------------------------------------------------------------------------------
	@RequestMapping(value = "company/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int problemId) {
		ModelAndView result;
		Problem problem;

		try {
			problem = this.problemService.findOne(problemId);
			Assert.isTrue(problem.getCompany() == this.companyService.findByPrincipal());

			result = this.principalList();
			if (this.problemService.checkApplicationsProblem(problem))
				result.addObject("application", "problem.delete.application");
			else
				this.problemService.delete(problem);

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// Attachment  ------------------------------------------------------------------------------------
	@RequestMapping(value = "/company/addAttachment", method = RequestMethod.GET)
	public ModelAndView addAttachment(@RequestParam final int problemId) {
		ModelAndView result;
		final Url url;
		Problem problem;

		try {
			problem = this.problemService.findOne(problemId);
			Assert.notNull(problem);
			Assert.isTrue(this.companyService.findByPrincipal() == problem.getCompany());
			url = new Url();
			url.setTargetId(problemId);
			result = new ModelAndView("problem/company/addAttachment");
			result.addObject("url", url);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	@RequestMapping(value = "/company/deleteAttachment", method = RequestMethod.GET)
	public ModelAndView deletePicture(@RequestParam final String link, @RequestParam final int problemId) {
		ModelAndView result;
		try {
			final Problem c = this.problemService.findOne(problemId);
			for (final Url picture : c.getAttachments())
				if (picture.getLink().equals(link)) {
					c.getAttachments().remove(picture);
					break;
				}
			result = this.createEditModelAndView(c);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// save attachment ------------------------------------------------------------------------------------
	@RequestMapping(value = "/company/addAttachment", method = RequestMethod.POST, params = "save")
	public ModelAndView savePicture(@Valid final Url url, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());

			result = new ModelAndView("problem/company/addAttachment");
			result.addObject("url", url);
		} else
			try {
				Problem c = this.problemService.findOne(url.getTargetId());
				c.getAttachments().add(url);
				c = this.problemService.save(c);
				result = this.createEditModelAndView(c);
			} catch (final Throwable oops) {
				System.out.println(url);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = new ModelAndView("problem/company/addAttachment");
				result.addObject("url", url);
			}
		return result;
	}

	// Ancillary methods -----------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Problem problem) {
		ModelAndView result;

		result = this.createEditModelAndView(problem, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Problem problem, final String message) {
		ModelAndView result;

		result = new ModelAndView("problem/company/edit");
		result.addObject("problem", problem);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}
}
