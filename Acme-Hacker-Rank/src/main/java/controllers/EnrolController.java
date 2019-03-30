
package controllers;

import java.util.Collection;
import java.util.List;

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

import services.BrotherhoodService;
import services.EnrolService;
import services.MemberService;
import domain.Brotherhood;
import domain.Enrol;
import domain.Member;

@Controller
@RequestMapping("/enrol/member")
public class EnrolController extends AbstractController {

	@Autowired
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private EnrolService		enrolService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}
	/******** MAIN METHODS *********/

	// Create ------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		Collection<Brotherhood> brotherhoods;
		ModelAndView result;
		Member member;

		try {
			member = this.memberService.findByPrincipal();

			final Enrol enrol = this.enrolService.create();
			brotherhoods = this.brotherhoodService.findAllNotEnroller(member);

			enrol.setMember(member);

			result = new ModelAndView("member/enrol");
			result.addObject("enrol", enrol);
			result.addObject("brotherhoods", brotherhoods);

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}
	// Save enrol ------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Enrol enrol, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}

			result = new ModelAndView("redirect:/enrol/member/create.do");
		} else {
			try {
				this.enrolService.save(enrol);
				result = new ModelAndView("redirect:/brotherhood/member/list.do");
			} catch (final Throwable oops) {
				System.out.println(enrol);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = new ModelAndView("redirect:/enrol/member/create.do");
			}
		}
		return result;
	}

	/** ANCILLARY METHODS **/

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

	/************************************************************************************************/
}
