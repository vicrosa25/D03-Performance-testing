package controllers;

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
import services.PathService;
import services.SegmentService;
import domain.Path;
import domain.Segment;

@Controller
@RequestMapping("/segment/brotherhood")
public class SegmentController extends AbstractController {

	@Autowired
	private PathService			pathService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private SegmentService		segmentService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return this.forbiddenOpperation();
	}

	// Create ------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int pathId) {
		ModelAndView result;

		try {
			Path path = this.pathService.findOne(pathId);
			Assert.isTrue(this.brotherhoodService.findByPrincipal().getProcessions().contains(path.getProcession()));
			final Segment segment = this.segmentService.create();

			Segment previous = this.segmentService.getLastSegment(path);

			segment.setOriginLatitude(previous.getDestinationLatitude());
			segment.setOriginLongitude(previous.getDestinationLongitude());
			segment.setPath(path);
			segment.setNumber(previous.getNumber() + 1);

			result = this.createEditModelAndView(segment);

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// edit ------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int segmentId) {
		ModelAndView result;

		try {
			Segment segment = this.segmentService.findOne(segmentId);
			Assert.isTrue(this.brotherhoodService.findByPrincipal().getProcessions().contains(segment.getPath().getProcession()));

			result = this.createEditModelAndView(segment);

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// Save path ------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Segment segment, final BindingResult binding) {
		ModelAndView result;
		if (segment.getOriginTime() != null && segment.getDestinationTime() != null) {
			if (segment.getOriginTime().after(segment.getDestinationTime())) {
				binding.rejectValue("destinationTime", "segment.error.destinationTime", "Arrival must be after departure");
			}
			if (segment.getNumber() > 0) {
				if (segment.getOriginTime().before(this.segmentService.findByNumber(segment.getPath(), segment.getNumber() - 1).getDestinationTime())) {
					binding.rejectValue("originTime", "segment.error.originTime", "Departure must be after the previous segment end");
				}
			}
		}

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}
			result = this.createEditModelAndView(segment);

		} else {
			try {
				segment = this.segmentService.save(segment);
				segment.getPath().getSegments().add(segment);

				result = new ModelAndView("redirect:/path/brotherhood/display.do?processionId=" + segment.getPath().getProcession().getId());
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = this.createEditModelAndView(segment);
			}
		}
		return result;
	}

	// Delete ------------------------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int segmentId) {
		ModelAndView result;

		try {
			final Segment segment = this.segmentService.findOne(segmentId);
			Path path = segment.getPath();

			Assert.isTrue(this.brotherhoodService.findByPrincipal().getProcessions().contains(segment.getPath().getProcession()));

			if (path.getSegments().size() > 1) {
				this.segmentService.delete(segment);
				result = new ModelAndView("redirect:/path/brotherhood/display.do?processionId=" + path.getProcession().getId());
			} else {
				this.pathService.delete(path);
				result = new ModelAndView("redirect:/procession/list.do");
			}



		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			oops.printStackTrace();
			result = this.forbiddenOpperation();

		}

		return result;
	}

	// Ancillary Methods  -----------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Segment segment) {
		ModelAndView result;

		result = this.createEditModelAndView(segment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Segment segment, final String message) {
		ModelAndView result;

		result = new ModelAndView("segment/brotherhood/edit");
		result.addObject("segment", segment);
		result.addObject("message", message);

		if (segment.getNumber() > 0) {
			result.addObject("readOnly", true);
		}
		return result;
	}
	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}
}
