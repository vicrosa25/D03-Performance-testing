
package controllers.brotherhood;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import controllers.AbstractController;
import domain.Brotherhood;
import domain.Request;
import services.BrotherhoodService;
import services.RequestService;

@Controller
@RequestMapping("/request/brotherhood")
public class RequestBrotherhoodController extends AbstractController {

	@Autowired
	private RequestService		requestService;

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
		Collection<Request> requests = new ArrayList<Request>();

		try {
			final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
			requests = this.requestService.findRequestByBrotherhood(brotherhood.getId());

			result = new ModelAndView("request/list");
			result.addObject("requests", requests);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			oops.printStackTrace();
			result = this.forbiddenOpperation();
		}

		return result;
	}
	
	// Edit Request GET------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;
		Brotherhood principal;
		
		try {
			principal = this.brotherhoodService.findByPrincipal();
			request = this.requestService.findOne(requestId);
			Assert.isTrue(request.getStatus().equals("PENDING"));
			Assert.isTrue(principal.getProcessions().contains(request.getProcession()));
			
			result = this.editModelAndView(request);
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Edit Request POST------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(final Request request, final BindingResult binding) {
		ModelAndView result;
		
		if(request.getStatus().equals("APPROVED")){
			if(request.getAssignedColumn() == null){
				binding.rejectValue("assignedColumn", "request.error.column", "If the request is approved a valid column number mut be provided");
			}
			if(request.getAssignedRow() == null){
				binding.rejectValue("assignedRow", "request.error.row", "If the request is approved a valid row number mut be provided");
			}
		}else{
			if(request.getReason() == null || request.getReason().isEmpty() || request.getReason().trim().isEmpty()){
				binding.rejectValue("reason", "request.error.reason", "If the request is rejected a reason must be provided");
			}
		}

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}

			result = this.editModelAndView(request);
		} else {
			try {
				this.requestService.save(request);
				this.requestService.automaticNotification(request);
				result = new ModelAndView("redirect:/request/brotherhood/list.do");
			} catch (final Throwable oops) {
				System.out.println(request);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = this.editModelAndView(request);
			}
		}
		return result;
	}

	/****** Other methods *******/

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

	protected ModelAndView editModelAndView(Request request) {
		ModelAndView result;

		result = this.editModelAndView(request, null);

		return result;
	}

	protected ModelAndView editModelAndView(Request request, String message) {
		ModelAndView result;

		result = new ModelAndView("request/brotherhood/edit");
		result.addObject("request", request);
		result.addObject("message", message);

		return result;
	}

	/************************************************************************************************/
}
