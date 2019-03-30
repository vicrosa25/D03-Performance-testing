
package controllers;

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

import domain.Area;
import domain.Url;
import services.AreaService;

@Controller
@RequestMapping("/area/administrator")
public class AreaController extends AbstractController {

	@Autowired
	private AreaService areaService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	// List ------------------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Area> areas;

		try {
			areas = this.areaService.findAll();
			result = new ModelAndView("area/administrator/list");
			result.addObject("requestUri", "area/administrator/list");
			result.addObject("areas", areas);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Create area GET------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Area area;

		area = this.areaService.create();

		result = new ModelAndView("area/administrator/create");
		result.addObject("area", area);

		return result;
	}

	// Create area POST -----------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid Area area, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());

			result = new ModelAndView("area/administrator/create");
			result.addObject("area", area);
		}

		else
			try {
				this.areaService.save(area);
				result = this.list();
			} catch (final Throwable oops) {
				System.out.println(area);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());

				result = new ModelAndView("area/administrator/create");
				result.addObject("area", area);
			}
		return result;
	}

	// Edit -----------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int areaId) {
		ModelAndView result;
		Area area;

		try {
			area = this.areaService.findOne(areaId);
			Assert.notNull(area);
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
			return result;
		}

		result = this.createEditModelAndView(area);

		return result;
	}

	// Save area POST --------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Area area, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());

			result = this.createEditModelAndView(area);
		}

		else
			try {
				this.areaService.save(area);
				result = this.list();
			} catch (final Throwable oops) {
				System.out.println(area);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = this.createEditModelAndView(area);
			}
		return result;
	}

	// Delete ------------------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int areaId) {
		ModelAndView result;

		try {
			Area area = this.areaService.findOne(areaId);
			this.areaService.delete(area);
			result = this.list();
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
			return result;
		}

		return result;
	}

	// Add Picture -------------------------------------------------------------------------
	@RequestMapping(value = "/addPicture", method = RequestMethod.GET)
	public ModelAndView addPicture(@RequestParam int areaId) {
		ModelAndView result;
		Url url;

		try {
			url = new Url();
			result = new ModelAndView("area/administrator/addPicture");
			result.addObject("url", url);
			result.addObject("areaId", areaId);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// SAVE -------------------------------------------------------------------------
	@RequestMapping(value = "/addPicture", method = RequestMethod.POST, params = "save")
	public ModelAndView savePicture(@Valid Url url, BindingResult binding, @RequestParam int areaId) {
		ModelAndView result;
		Area area;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());

			result = new ModelAndView("area/administrator/addPicture");
			result.addObject("url", url);
			result.addObject("areaId", areaId);
		}

		else
			try {
				area = this.areaService.findOne(areaId);
				area.getPictures().add(url);
				area = this.areaService.save(area);
				result = this.createEditModelAndView(area);
			} catch (Throwable oops) {
				System.out.println(url);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = new ModelAndView("area/administrator/addPicture");
				result.addObject("url", url);
				result.addObject("areaId", areaId);
			}
		return result;
	}

	// Delete Picture ---------------------------------------------------------------------
	@RequestMapping(value = "/deletePicture", method = RequestMethod.GET)
	public ModelAndView deletePicture(@RequestParam String link, @RequestParam int areaId) {
		ModelAndView result;
		Area area;
		try {
			area = this.areaService.findOne(areaId);
			for (Url picture : area.getPictures())
				if (picture.getLink().equals(link)) {
					area.getPictures().remove(picture);
					break;
				}
			area = this.areaService.save(area);
			result = this.createEditModelAndView(area);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Ancillary methods------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(Area area) {
		ModelAndView result;

		result = this.createEditModelAndView(area, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Area area, String message) {
		ModelAndView result;

		result = new ModelAndView("area/administrator/edit");
		result.addObject("area", area);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}
}
