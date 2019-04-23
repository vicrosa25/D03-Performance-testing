
package controllers;

import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

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
import services.PositionService;
import services.ProblemService;
import domain.Position;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController {

	@Autowired
	private PositionService positionService;

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private ProblemService	problemService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		JOptionPane.showMessageDialog(null, "Forbidden operation");
		return new ModelAndView("redirect:/");
	}

	// List -------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Position> positions;
		try {

			positions = this.positionService.findAllFinal();

			result = new ModelAndView("position/list");
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/list.do");

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// Company List -------------------------------------------------------------
	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public ModelAndView companyList(@RequestParam final int companyId) {
		ModelAndView result;
		Collection<Position> positions;
		try {

			positions = this.companyService.findOne(companyId).getPositions();

			result = new ModelAndView("position/list");
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/list.do");

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// principal List -------------------------------------------------------------
	@RequestMapping(value = "/company/list", method = RequestMethod.GET)
	public ModelAndView principalList() {
		ModelAndView result;
		Collection<Position> positions;
		try {

			positions = this.companyService.findByPrincipal().getPositions();

			result = new ModelAndView("position/list");
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/company/list.do");

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// Company display -------------------------------------------------------------
	@RequestMapping(value = "/company/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;
		try {

			position = this.positionService.findOne(positionId);

			result = new ModelAndView("position/company/display");
			result.addObject("position", position);

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
		Position position;
		try {

			position = this.positionService.create();

			result = new ModelAndView("position/company/create");
			result.addObject("position", position);
			result.addObject("problems", this.problemService.getPrincipalFinalMode());

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
	public ModelAndView edit(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;
		Position pruned = new Position();

		try {
			position = this.positionService.findOne(positionId);
			Assert.isTrue(!position.getFinalMode());
			Assert.isTrue(position.getCompany() == this.companyService.findByPrincipal());

			pruned.setId(position.getId());
			pruned.setDeadline(position.getDeadline());
			pruned.setDescription(position.getDescription());
			pruned.setFinalMode(false);
			pruned.setProfile(position.getProfile());
			pruned.setSalary(position.getSalary());
			pruned.setSkills(position.getSkills());
			pruned.setTechnologies(position.getTechnologies());
			pruned.setTitle(position.getTitle());
			pruned.setProblems(position.getProblems());

			result = this.createEditModelAndView(pruned);

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
	public ModelAndView saveEdit(final Position prune, final BindingResult binding) {
		ModelAndView result;
		final Position position;

		position = this.positionService.reconstruct(prune, binding);
		if (prune.getFinalMode() != null) {
			if (prune.getFinalMode()) {
				if (position.getProblems() == null) {
					binding.rejectValue("finalMode", "position.error.finalMode", "A position must have, at least, two problems to be in final mode");
				} else if (position.getProblems().size() < 2) {
					binding.rejectValue("finalMode", "position.error.finalMode", "A position must have, at least, two problems to be in final mode");
				}
			}
		}

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());

			result = this.createEditModelAndView(prune);
		}

		else
			try {
				this.positionService.save(position);
				result = new ModelAndView("redirect:/position/company/list.do");
			} catch (final Throwable oops) {
				System.out.println(prune);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = this.createEditModelAndView(prune, "company.registration.error");
			}
		return result;
	}

	// delete ------------------------------------------------------------------------------------
	@RequestMapping(value = "company/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;

		try {
			position = this.positionService.findOne(positionId);
			Assert.isTrue(!position.getFinalMode());
			Assert.isTrue(position.getCompany() == this.companyService.findByPrincipal());

			this.positionService.delete(position);

			result = new ModelAndView("redirect:/position/company/list.do");

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// cancel ------------------------------------------------------------------------------------
	@RequestMapping(value = "company/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;

		try {
			position = this.positionService.findOne(positionId);
			Assert.isTrue(position.getFinalMode());
			Assert.isTrue(position.getCompany() == this.companyService.findByPrincipal());

			this.positionService.cancel(position);

			result = new ModelAndView("redirect:/position/company/list.do");

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// Ancillary methods -----------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Position position) {
		ModelAndView result;

		result = this.createEditModelAndView(position, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Position position, final String message) {
		ModelAndView result;

		result = new ModelAndView("position/company/edit");
		result.addObject("position", position);
		result.addObject("problems", this.problemService.getPrincipalFinalMode());
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

}
