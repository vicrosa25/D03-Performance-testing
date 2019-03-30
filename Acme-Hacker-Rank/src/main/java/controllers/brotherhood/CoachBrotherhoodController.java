
package controllers.brotherhood;

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

import services.BrotherhoodService;
import services.CoachService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Coach;
import domain.Url;

@Controller
@RequestMapping("/coach/brotherhood")
public class CoachBrotherhoodController extends AbstractController {

	@Autowired
	private CoachService		coachService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	// List ------------------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Coach> coaches;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			coaches = brotherhood.getCoaches();
			result = new ModelAndView("coach/list");
			result.addObject("coaches", coaches);
			result.addObject("brotherhood", brotherhood);
			result.addObject("bro", 1);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Create ------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Coach coach;
		try {
			coach = this.coachService.create();
			result = this.createEditModelAndView(coach);

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			//oops.printStackTrace();
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	// Save the coach ------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Coach coach, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}

			result = this.createEditModelAndView(coach);
		} else {
			try {
				this.coachService.save(coach);
				result = new ModelAndView("redirect:/coach/brotherhood/list.do");
			} catch (final Throwable oops) {
				System.out.println(coach);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = this.createEditModelAndView(coach, "coach.commit.error");
			}
		}
		return result;
	}

	// Edit ------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int coachId) {
		ModelAndView result;
		Coach coach;

		try {
			coach = this.coachService.findOne(coachId);
			Assert.notNull(coach);
			Assert.isTrue(this.brotherhoodService.findByPrincipal() == this.brotherhoodService.findByCoach(coach));
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
			return result;
		}

		result = this.createEditModelAndView(coach);

		return result;
	}

	// Delete --------------------------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int coachId) {
		ModelAndView result;
		Coach coach;
		try {
			coach = this.coachService.findOne(coachId);
			Assert.notNull(coach);
			Assert.isTrue(this.brotherhoodService.findByPrincipal() == this.brotherhoodService.findByCoach(coach));
			this.coachService.delete(coachId);
			result = new ModelAndView("redirect:/coach/brotherhood/list.do");
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
			return result;
		}

		return result;
	}

	// Picture  ------------------------------------------------------------------------------------
	@RequestMapping(value = "/addPicture", method = RequestMethod.GET)
	public ModelAndView addPicture(@RequestParam final int coachId) {
		ModelAndView result;
		final Url url;
		Coach coach;

		try {
			coach = this.coachService.findOne(coachId);
			Assert.notNull(coach);
			Assert.isTrue(this.brotherhoodService.findByPrincipal() == this.brotherhoodService.findByCoach(coach));
			url = new Url();
			url.setTargetId(coachId);
			result = new ModelAndView("coach/brotherhood/addPicture");
			result.addObject("url", url);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	@RequestMapping(value = "/deletePicture", method = RequestMethod.GET)
	public ModelAndView deletePicture(@RequestParam final String link, @RequestParam final int coachId) {
		ModelAndView result;
		try {
			final Coach c = this.coachService.findOne(coachId);
			for (final Url picture : c.getPictures()) {
				if (picture.getLink().equals(link)) {
					c.getPictures().remove(picture);
					break;
				}
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

	// SAVE ------------------------------------------------------------------------------------
	@RequestMapping(value = "/addPicture", method = RequestMethod.POST, params = "save")
	public ModelAndView savePicture(@Valid final Url url, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}

			result = new ModelAndView("coach/brotherhood/addPicture");
			result.addObject("url", url);
		} else {
			try {
				Coach c = this.coachService.findOne(url.getTargetId());
				c.getPictures().add(url);
				c = this.coachService.save(c);
				result = this.createEditModelAndView(c);
			} catch (final Throwable oops) {
				System.out.println(url);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = new ModelAndView("coach/brotherhood/addPicture");
				result.addObject("url", url);
			}
		}
		return result;
	}

	// Ancillary methods -----------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Coach coach) {
		ModelAndView result;

		result = this.createEditModelAndView(coach, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Coach coach, final String message) {
		ModelAndView result;

		result = new ModelAndView("coach/brotherhood/edit");
		result.addObject("coach", coach);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

	/************************************************************************************************/
}
