
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

import domain.Area;
import domain.Brotherhood;
import domain.Chapter;
import domain.Procession;
import domain.Proclaim;
import domain.Url;
import forms.ChapterForm;
import services.ActorService;
import services.AreaService;
import services.ChapterService;
import services.ProcessionService;
import utilities.Md5;

@Controller
@RequestMapping("/chapter")
public class ChapterController extends AbstractController {

	@Autowired
	private ChapterService		chapterService;

	@Autowired
	private AreaService			areaService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ProcessionService	processionService;
	
	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		JOptionPane.showMessageDialog(null, "Forbidden operation");
		return new ModelAndView("redirect:/");
	}


	// List ------------------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView chahpterList() {
		ModelAndView result;
		Collection<Chapter> chapters;

		chapters = this.chapterService.findAll();

		result = new ModelAndView("chapter/list");
		result.addObject("chapters", chapters);
		result.addObject("requestURI", "chapter/list.do");

		return result;
	}

	// Register formObject ------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ChapterForm form;

		try {
			form = new ChapterForm();
			result = this.createEditModelAndView(form);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Save the new brotherhood from formObject -------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ChapterForm chapterForm, BindingResult binding) {
		ModelAndView result;
		Chapter chapter;
		String password;

		chapter = this.chapterService.reconstruct(chapterForm, binding);

		if (!chapterForm.isAccepted()) {
			binding.rejectValue("accepted", "register.terms.error", "Service terms must be accepted");
		}
		if (binding.hasErrors()) {
			List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());

			result = this.createEditModelAndView(chapterForm);
		}

		else
			try {
				password = Md5.encodeMd5(chapter.getUserAccount().getPassword());
				chapter.getUserAccount().setPassword(password);
				this.chapterService.save(chapter);
				result = new ModelAndView("redirect:../security/login.do");
			} catch (final Throwable oops) {
				System.out.println(chapter);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = this.createEditModelAndView(chapterForm);
			}
		return result;
	}

	// Edit Pruned Object -------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Chapter chapter;

		try {
			chapter = this.chapterService.findByPrincipal();

			// Set relations to null to use as a prune object
			chapter.setArea(null);

			result = new ModelAndView("chapter/edit");
			result.addObject("chapter", chapter);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// SAVE Pruned Object ---------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(Chapter prune, BindingResult binding) {
		ModelAndView result;
		Chapter chapter;

		chapter = this.chapterService.reconstruct(prune, binding);

		if (binding.hasErrors()) {
			List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());

			result = new ModelAndView("chapter/edit");
			result.addObject("chapter", prune);
		}

		else
			try {
				this.chapterService.save(chapter);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				System.out.println(chapter);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = this.editModelAndView(chapter, "chapter.registration.error");
			}
		return result;
	}

	// Self-assign an area GET ------------------------------------------------------------------------------------
	@RequestMapping(value = "/area/assign", method = RequestMethod.GET)
	public ModelAndView assignArea() {
		ModelAndView result;
		Chapter chapter;

		chapter = this.chapterService.findByPrincipal();

		if (chapter.getArea() != null) {
			result = this.displayArea();
		} else {
			result = new ModelAndView("chapter/area/assign");
			result.addObject("areas", this.areaService.findFreeAreas());
			result.addObject("chapter", chapter);
		}

		return result;
	}

	// Self-assign an area POST ------------------------------------------------------------------------------------
	@RequestMapping(value = "/area/assign", method = RequestMethod.POST, params = "save")
	public ModelAndView assignArea(Chapter prune, BindingResult binding) {
		ModelAndView result;
		Chapter chapter;

		chapter = this.chapterService.addArea(prune, binding);

		if (binding.hasErrors()) {
			List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());

			result = new ModelAndView("chapter/area/assign");
			result.addObject("chapter", prune);
		}

		else
			try {
				Area area = chapter.getArea();
				if (area == null) {
					result = this.assignArea();
					result.addObject("message", "chapter.area.null");
					return result;
				}
				area.setChapter(chapter);
				this.areaService.save(area);
				this.chapterService.save(chapter);
				result = this.displayArea();
			} catch (final Throwable oops) {
				System.out.println(chapter);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = new ModelAndView("chapter/area/assign");
			}
		return result;
	}

	// list area ------------------------------------------------------------------------------------
	@RequestMapping(value = "/area/list", method = RequestMethod.GET)
	public ModelAndView areaList(@RequestParam int areaId) {
		ModelAndView result;
		Area area;
		Collection<Url> pictures;

		area = this.areaService.findOne(areaId);
		//Collection<Area> areas = new ArrayList<Area>();
		//areas.add(area);
		
		pictures = area.getPictures();

		result = new ModelAndView("chapter/area/list");
		result.addObject("requestURI", "chapter/area/list.do");
		result.addObject("pictures", pictures);
		result.addObject("area", area);

		return result;
	}

	// list area/brotherhoods  ------------------------------------------------------------------------
	@RequestMapping(value = "/area/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView brotherhoodList(@RequestParam int areaId) {
		ModelAndView result;
		Area area;

		area = this.areaService.findOne(areaId);
		Collection<Brotherhood> brotherhoods = area.getBrotherhoods();

		result = new ModelAndView("chapter/area/brotherhood/list");
		result.addObject("requestURI", "chapter/area/brotherhood/list.do");
		result.addObject("brotherhoods", brotherhoods);

		return result;
	}

	// Display area ------------------------------------------------------------------------------------
	@RequestMapping(value = "/area/display", method = RequestMethod.GET)
	public ModelAndView displayArea() {
		ModelAndView result;
		Chapter chapter;
		Area area;

		chapter = this.chapterService.findByPrincipal();
		area = chapter.getArea();

		result = new ModelAndView("chapter/area/display");
		result.addObject("area", area);
		result.addObject("brotherhoods", area.getBrotherhoods());
		result.addObject("pictures", area.getPictures());

		return result;
	}
	
	
	// Proclaims list ------------------------------------------------------------------------------------
	@RequestMapping(value = "/proclaim/list", method = RequestMethod.GET)
	public ModelAndView proclaimList(@RequestParam int chapterId) {
		ModelAndView result;
		Chapter chapter;
		Collection<Proclaim> proclaims;
		
		
		chapter = this.chapterService.findOne(chapterId);
		proclaims = chapter.getProclaims();
		

		result = new ModelAndView("chapter/proclaim/list");
		result.addObject("requestURI", "chapter/proclaim/list.do");
		result.addObject("proclaims", proclaims);

		return result;
	}

	// Generate pdf ------------------------------------------------------------------------------------
	@RequestMapping(value = "/generatePDF")
	public void generatePDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Chapter chapter;

		try {
			final ServletContext servletContext = request.getSession().getServletContext();
			final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			final String temperotyFilePath = tempDirectory.getAbsolutePath();
			chapter = this.chapterService.findByPrincipal();

			String fileName = chapter.getName() + ".pdf";
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);

			this.actorService.generatePersonalInformationPDF(chapter, temperotyFilePath + "\\" + fileName);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos = this.convertPDFToByteArrayOutputStream(temperotyFilePath + "\\" + fileName);
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
	
	
	

	/*************************************
	 * Parades methods
	 *************************************/
	// Listing Parades
	@RequestMapping(value = "/parade/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Chapter principal;
		Collection<Procession> parades;

		principal = this.chapterService.findByPrincipal();

		try {
			parades = this.chapterService.findAllProcessionByChapter(principal.getId());
			result = new ModelAndView("chapter/parade/list");
			result.addObject("uri", "chapter/parade/list");
			result.addObject("parades", parades);

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Aprove parade --------------------------------------------------------------------------------------
	@RequestMapping(value = "/parade/aprove", method = RequestMethod.GET)
	public ModelAndView aproveParade(@RequestParam int processionId) {
		ModelAndView result = null;
		Procession procession;
		Chapter principal;

		procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);

		try {
			// Check principal manage Area where is Brotherhood
			principal = this.chapterService.findByPrincipal();
			Assert.isTrue(principal.getArea().getBrotherhoods().contains(procession.getBrotherhood()), "The chapter don't manage this procession");

			this.chapterService.aproveProcession(procession);
			result = this.list();
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			return result;
		}

		return result;
	}


	// Rejecting parade GET ------------------------------------------------------------------------------------
	@RequestMapping(value = "/parade/reject/reasson", method = RequestMethod.GET)
	public ModelAndView reason(@RequestParam int processionId) {
		ModelAndView result;
		Procession procession;

		procession = this.processionService.findOne(processionId);
		Assert.notNull(procession);

		result = new ModelAndView("chapter/parade/reject/reasson");
		result.addObject("procession", procession);
		return result;
	}

	// Rejecting parade POST  --------------------------------------------------------------------------------------
	@RequestMapping(value = "parade/reject/reasson", method = RequestMethod.POST)
	public ModelAndView reason(Procession prune, BindingResult binding) {
		ModelAndView result = null;
		Procession procession;
		Chapter principal;

		try {
			// Reconstruct rejected parade
			procession = this.processionService.reconstructRejected(prune, binding);

			// Check reasson is not empty
			if (procession.getReasson().isEmpty()) {
				result = this.reason(prune.getId());
				result.addObject("message", "chapter.parade.reasson.null");
				return result;
			}

			// Check principal manage Area where is Brotherhood
			principal = this.chapterService.findByPrincipal();
			Assert.isTrue(principal.getArea().getBrotherhoods().contains(procession.getBrotherhood()), "The chapter don't manage this procession");

			// Set status rejected
			this.chapterService.rejectParade(procession);
			result = this.list();
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			return result;
		}

		return result;
	}

	// Delete ------------------------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		Chapter chapter;

		try {
			chapter = this.chapterService.findByPrincipal();
			this.chapterService.delete(chapter);
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
	protected ModelAndView createEditModelAndView(ChapterForm chapterForm) {
		ModelAndView result;

		result = this.createEditModelAndView(chapterForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ChapterForm chapterForm, String message) {
		ModelAndView result;

		result = new ModelAndView("chapter/create");
		result.addObject("area", this.areaService.findAll());
		result.addObject("chapterForm", chapterForm);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView editModelAndView(Chapter chapter) {
		ModelAndView result;

		result = this.editModelAndView(chapter, null);

		return result;
	}

	protected ModelAndView editModelAndView(Chapter chapter, String message) {
		ModelAndView result;

		result = new ModelAndView("chapter/edit");
		result.addObject("chapter", chapter);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}
}
