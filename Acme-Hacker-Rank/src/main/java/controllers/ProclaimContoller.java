
package controllers;

import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
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

import services.ChapterService;
import services.ProclaimService;
import domain.Chapter;
import domain.Proclaim;

@Controller
@RequestMapping("/proclaim")
public class ProclaimContoller extends AbstractController {

	@Autowired
	private ProclaimService	proclaimService;

	@Autowired
	private ChapterService	chapterService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		JOptionPane.showMessageDialog(null, "Forbidden operation");
		return new ModelAndView("redirect:/");
	}

	// list ------------------------------------------------------------------------------------
	@RequestMapping(value = "/chapter/list", method = RequestMethod.GET)
	public ModelAndView proclaimList() {
		ModelAndView result;
		Chapter chapter;
		Collection<Proclaim> proclaims;
		try {
			chapter = this.chapterService.findByPrincipal();
			proclaims = chapter.getProclaims();

			result = new ModelAndView("chapter/proclaim/list");
			result.addObject("requestURI", "chapter/proclaim/list.do");
			result.addObject("proclaims", proclaims);
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
		}
		return result;
	}
	// Publish ---------------------------------------------------------------
	@RequestMapping(value = "/chapter/publish", method = RequestMethod.GET)
	public ModelAndView publish() {
		ModelAndView result;
		Proclaim proclaim;
		try {
			proclaim = this.proclaimService.create();
			result = this.createEditModelAndView(proclaim);
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Publish -------------------------------------------------------------
	@RequestMapping(value = "/chapter/publish", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Proclaim proclaim, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());
			result = this.createEditModelAndView(proclaim);
		} else
			try {
				this.proclaimService.save(proclaim);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				System.out.println(proclaim);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = this.createEditModelAndView(proclaim, "profile.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Proclaim proclaim) {
		ModelAndView result;

		result = this.createEditModelAndView(proclaim, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Proclaim proclaim, final String message) {
		ModelAndView result;

		result = new ModelAndView("proclaim/chapter/publish");
		result.addObject("proclaim", proclaim);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

}
