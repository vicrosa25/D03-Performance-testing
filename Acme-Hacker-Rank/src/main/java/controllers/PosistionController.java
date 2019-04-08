
package controllers;

import java.util.Collection;

import javax.swing.JOptionPane;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Position;
import services.CompanyService;
import services.PositionService;

@Controller
@RequestMapping("/position")
public class PosistionController extends AbstractController {

	@Autowired
	private PositionService positionService;

	@Autowired
	private CompanyService	companyService;


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

			positions = this.positionService.findAll();

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

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

}
