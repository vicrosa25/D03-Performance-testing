
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

import services.ActorService;
import services.BrotherhoodService;
import services.InceptionRecordService;
import services.LegalRecordService;
import services.LinkRecordService;
import services.MiscellaneousRecordService;
import services.PeriodRecordService;
import domain.Actor;
import domain.Brotherhood;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;
import domain.Url;

@Controller
@RequestMapping("/history")
public class HistoryController extends AbstractController {

	@Autowired
	private ActorService				actorService;

	@Autowired
	private BrotherhoodService			brotherhoodService;

	@Autowired
	private InceptionRecordService		inceptionService;

	@Autowired
	private PeriodRecordService			periodService;

	@Autowired
	private LinkRecordService			linkService;

	@Autowired
	private LegalRecordService			legalService;

	@Autowired
	private MiscellaneousRecordService	miscService;
	// EXEPTIONS
	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}
	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}
	
	/** UNAUTHENTICATED METHODS **/
	// Display ------------------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findOne(brotherhoodId);
			result = new ModelAndView("history/display");
			result.addObject("history", brotherhood.getHistory());
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	/** BROTHERHOOD METHODS **/
	// Display ------------------------------------------------------------------------------------
	@RequestMapping(value = "/brotherhood/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			result = new ModelAndView("history/display");
			result.addObject("history", brotherhood.getHistory());
			result.addObject("bro", 1);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	/*** INCEPTION METHODS ***/
	// Edit inception record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/inceptionRecord/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView inceptionRecord() {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			result = new ModelAndView("history/inceptionRecord/brotherhood/edit");
			result.addObject("inceptionRecord", brotherhood.getHistory().getInceptionRecord());
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	// Save inception ------------------------------------------------------------------------------------
	@RequestMapping(value = "/inceptionRecord/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveInception(@Valid final InceptionRecord inception, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}
			result = new ModelAndView("history/inceptionRecord/brotherhood/edit");
			result.addObject("inceptionRecord", inception);
		
		} else {
			try {
				this.inceptionService.save(inception);
				result = new ModelAndView("redirect:/history/brotherhood/display.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = new ModelAndView("history/inceptionRecord/brotherhood/edit");
				result.addObject("inceptionRecord", inception);
			}
		}
		return result;
	}
	
	// addPicture inception ------------------------------------------------------------------------------------
	@RequestMapping(value = "/inceptionRecord/brotherhood/addPicture", method = RequestMethod.GET)
	public ModelAndView addPictureInception() {
		ModelAndView result;
		final Url url;
		InceptionRecord inception;

		try {
			inception = this.brotherhoodService.findByPrincipal().getHistory().getInceptionRecord();
			Assert.notNull(inception);
			url = new Url();
			result = new ModelAndView("history/inceptionRecord/brotherhood/addPicture");
			result.addObject("url", url);
			result.addObject("uri", "history/inceptionRecord/brotherhood/addPicture.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// save picture inception----------------------------------------------------------------------
	@RequestMapping(value = "/inceptionRecord/brotherhood/addPicture", method = RequestMethod.POST, params = "save")
	public ModelAndView savePictureInception(@Valid final Url url, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}

			result = new ModelAndView("history/inceptionRecord/brotherhood/addPicture");
			result.addObject("url", url);
		} else {
			try {
				InceptionRecord inception = this.brotherhoodService.findByPrincipal().getHistory().getInceptionRecord();
				inception.getPictures().add(url);
				this.inceptionService.save(inception);
				result = new ModelAndView("redirect:/history/brotherhood/display.do");
			} catch (final Throwable oops) {
				System.out.println(url);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = new ModelAndView("history/inceptionRecord/brotherhood/addPicture");
				result.addObject("url", url);
			}
		}
		return result;
	}
	
	// Delete inception picture
	@RequestMapping(value = "/inceptionRecord/brotherhood/deletePicture", method = RequestMethod.GET)
	public ModelAndView deleteInceptionPicture(@RequestParam final String link) {
		ModelAndView result;
		try {
			final InceptionRecord inception = this.brotherhoodService.findByPrincipal().getHistory().getInceptionRecord();
			for (final Url picture : inception.getPictures()) {
				if (picture.getLink().equals(link)) {
					inception.getPictures().remove(picture);
					this.inceptionService.save(inception);
					break;
				}
			}
			result = new ModelAndView("redirect:/history/brotherhood/display.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}
	
	/*** PERIOD METHODS ***/
	// Edit period record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/periodRecord/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView createPeriodRecord() {
		ModelAndView result;

		try {
			PeriodRecord period = this.periodService.create();
			
			result = new ModelAndView("history/periodRecord/brotherhood/edit");
			result.addObject("periodRecord", period);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	
	// Edit period record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/periodRecord/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView periodRecord(@RequestParam final int periodId) {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			PeriodRecord period = this.periodService.findOne(periodId);
			Assert.isTrue(brotherhood.getHistory().getPeriodRecords().contains(period));
			
			result = new ModelAndView("history/periodRecord/brotherhood/edit");
			result.addObject("periodRecord", period);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	// Save period ------------------------------------------------------------------------------------
	@RequestMapping(value = "/periodRecord/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView savePeriod(@Valid final PeriodRecord period, final BindingResult binding) {
		ModelAndView result;
		if(!binding.hasErrors()){
			if(period.getEndYear()<period.getStartYear()){
				binding.rejectValue("endYear", "periodRecord.error.year", "The end year must be after the start year");
			}
		}
		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}
			result = new ModelAndView("history/periodRecord/brotherhood/edit");
			result.addObject("periodRecord", period);
		
		} else {
			try {
				this.periodService.save(period);
				result = new ModelAndView("redirect:/history/brotherhood/display.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = new ModelAndView("history/periodRecord/brotherhood/edit");
				result.addObject("periodRecord", period);
			}
		}
		return result;
	}
	// Period pictures ------------------------------------------------------------------------------------
	@RequestMapping(value = "/periodRecord/pictures", method = RequestMethod.GET)
	public ModelAndView pictures(@RequestParam final int periodId) {
		ModelAndView result;
		Brotherhood brotherhood;
		Actor principal;

		try {
			PeriodRecord period = this.periodService.findOne(periodId);
			result = new ModelAndView("history/periodRecord/pictures");
			result.addObject("periodRecord", period);
			
			principal = this.actorService.findByPrincipal();
			if(principal instanceof Brotherhood){
				brotherhood = (Brotherhood) principal;
				if(brotherhood.getHistory().getPeriodRecords().contains(period)){
					result.addObject("bro", 1);
				}
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
	
	
	// Delete period record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/periodRecord/brotherhood/delete", method = RequestMethod.GET)
	public ModelAndView periodRecordDelete(@RequestParam final int periodId) {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			PeriodRecord period = this.periodService.findOne(periodId);
			Assert.isTrue(brotherhood.getHistory().getPeriodRecords().contains(period));
			
			this.periodService.delete(period);

			result = new ModelAndView("redirect:/history/brotherhood/display.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	// addPicture period ------------------------------------------------------------------------------------
	@RequestMapping(value = "/periodRecord/brotherhood/addPicture", method = RequestMethod.GET)
	public ModelAndView addPicturePeriod(@RequestParam final int periodId) {
		ModelAndView result;
		final Url url;
		PeriodRecord period;

		try {
			Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
			period = this.periodService.findOne(periodId);
			Assert.isTrue(brotherhood.getHistory().getPeriodRecords().contains(period));
			
			url = new Url();
			url.setTargetId(period.getId());
			result = new ModelAndView("history/periodRecord/brotherhood/addPicture");
			result.addObject("url", url);
			result.addObject("uri", "history/periodRecord/brotherhood/addPicture.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// save picture period----------------------------------------------------------------------
	@RequestMapping(value = "/periodRecord/brotherhood/addPicture", method = RequestMethod.POST, params = "save")
	public ModelAndView savePicturePeriod(@Valid final Url url, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}

			result = new ModelAndView("history/periodRecord/brotherhood/addPicture");
			result.addObject("url", url);
		} else {
			try {
				PeriodRecord period = this.periodService.findOne(url.getTargetId());
				
				Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
				Assert.isTrue(brotherhood.getHistory().getPeriodRecords().contains(period));
				
				period.getPictures().add(url);
				this.periodService.save(period);
				result = new ModelAndView("redirect:/history/periodRecord/pictures.do?periodId="+url.getTargetId());
			} catch (final Throwable oops) {
				System.out.println(url);
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = new ModelAndView("history/periodRecord/brotherhood/addPicture");
				result.addObject("url", url);
			}
		}
		return result;
	}
	
	// Delete period picture
	@RequestMapping(value = "/periodRecord/brotherhood/deletePicture", method = RequestMethod.GET)
	public ModelAndView deletePeriodPicture(@RequestParam final String link, @RequestParam final int periodId) {
		ModelAndView result;
		try {
			PeriodRecord period = this.periodService.findOne(periodId);
			
			Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
			Assert.isTrue(brotherhood.getHistory().getPeriodRecords().contains(period));
			for (final Url picture : period.getPictures()) {
				if (picture.getLink().equals(link)) {
					period.getPictures().remove(picture);
					this.periodService.save(period);
					break;
				}
			}
			result = new ModelAndView("redirect:/history/periodRecord/pictures.do?periodId="+periodId);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}
	
	/*** LEGAL METHODS ***/
	// Edit legal record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/legalRecord/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView createLegalRecord() {
		ModelAndView result;

		try {
			LegalRecord legal = this.legalService.create();
			
			result = new ModelAndView("history/legalRecord/brotherhood/edit");
			result.addObject("legalRecord", legal);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	
	// Edit legal record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/legalRecord/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView legalRecord(@RequestParam final int legalId) {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			LegalRecord legal = this.legalService.findOne(legalId);
			Assert.isTrue(brotherhood.getHistory().getLegalRecords().contains(legal));
			
			result = new ModelAndView("history/legalRecord/brotherhood/edit");
			result.addObject("legalRecord", legal);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	// Save legal ------------------------------------------------------------------------------------
	@RequestMapping(value = "/legalRecord/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveLegal(@Valid final LegalRecord legal, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}
			result = new ModelAndView("history/legalRecord/brotherhood/edit");
			result.addObject("legalRecord", legal);
		
		} else {
			try {
				this.legalService.save(legal);
				result = new ModelAndView("redirect:/history/brotherhood/display.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = new ModelAndView("history/legalRecord/brotherhood/edit");
				result.addObject("legalRecord", legal);
			}
		}
		return result;
	}
	
	
	// Delete legal record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/legalRecord/brotherhood/delete", method = RequestMethod.GET)
	public ModelAndView legalRecordDelete(@RequestParam final int legalId) {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			LegalRecord legal = this.legalService.findOne(legalId);
			Assert.isTrue(brotherhood.getHistory().getLegalRecords().contains(legal));
			
			this.legalService.delete(legal);

			result = new ModelAndView("redirect:/history/brotherhood/display.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	/*** LINK METHODS ***/
	// Edit link record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/linkRecord/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView createLinkRecord() {
		ModelAndView result;

		try {
			LinkRecord link = this.linkService.create();
			Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
			Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			brotherhoods.remove(brotherhood);
			
			result = new ModelAndView("history/linkRecord/brotherhood/edit");
			result.addObject("linkRecord", link);
			result.addObject("brotherhoods", brotherhoods);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	
	// Edit link record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/linkRecord/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView linkRecord(@RequestParam final int linkId) {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			brotherhoods.remove(brotherhood);
			LinkRecord link = this.linkService.findOne(linkId);
			
			Assert.isTrue(brotherhood.getHistory().getLinkRecords().contains(link));
			
			result = new ModelAndView("history/linkRecord/brotherhood/edit");
			result.addObject("linkRecord", link);
			result.addObject("brotherhoods", brotherhoods);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	// Save link ------------------------------------------------------------------------------------
	@RequestMapping(value = "/linkRecord/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveLink(@Valid final LinkRecord link, final BindingResult binding) {
		ModelAndView result;
		Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		brotherhoods.remove(brotherhood);

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}
			result = new ModelAndView("history/linkRecord/brotherhood/edit");
			result.addObject("linkRecord", link);
			result.addObject("brotherhoods", brotherhoods);
		
		} else {
			try {
				this.linkService.save(link);
				result = new ModelAndView("redirect:/history/brotherhood/display.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = new ModelAndView("history/linkRecord/brotherhood/edit");
				result.addObject("brotherhoods", brotherhoods);
				result.addObject("linkRecord", link);
			}
		}
		return result;
	}
	
	
	// Delete link record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/linkRecord/brotherhood/delete", method = RequestMethod.GET)
	public ModelAndView linkRecordDelete(@RequestParam final int linkId) {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			LinkRecord link = this.linkService.findOne(linkId);
			Assert.isTrue(brotherhood.getHistory().getLinkRecords().contains(link));
			
			this.linkService.delete(link);

			result = new ModelAndView("redirect:/history/brotherhood/display.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	/*** MISCELLANEOUS METHODS ***/
	// Edit miscellaneous record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/miscellaneousRecord/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView createMiscellaneousRecord() {
		ModelAndView result;

		try {
			MiscellaneousRecord miscellaneous = this.miscService.create();
			
			result = new ModelAndView("history/miscellaneousRecord/brotherhood/edit");
			result.addObject("miscellaneousRecord", miscellaneous);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	
	// Edit miscellaneous record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/miscellaneousRecord/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView miscellaneousRecord(@RequestParam final int miscellaneousId) {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			brotherhoods.remove(brotherhood);
			MiscellaneousRecord miscellaneous = this.miscService.findOne(miscellaneousId);
			
			Assert.isTrue(brotherhood.getHistory().getMiscellaneousRecords().contains(miscellaneous));
			
			result = new ModelAndView("history/miscellaneousRecord/brotherhood/edit");
			result.addObject("miscellaneousRecord", miscellaneous);
			result.addObject("brotherhoods", brotherhoods);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}
	
	// Save miscellaneous ------------------------------------------------------------------------------------
	@RequestMapping(value = "/miscellaneousRecord/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveMiscellaneous(@Valid final MiscellaneousRecord miscellaneous, final BindingResult binding) {
		ModelAndView result;
		Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		brotherhoods.remove(brotherhood);

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}
			result = new ModelAndView("history/miscellaneousRecord/brotherhood/edit");
			result.addObject("miscellaneousRecord", miscellaneous);
			result.addObject("brotherhoods", brotherhoods);
		
		} else {
			try {
				this.miscService.save(miscellaneous);
				result = new ModelAndView("redirect:/history/brotherhood/display.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = new ModelAndView("history/miscellaneousRecord/brotherhood/edit");
				result.addObject("brotherhoods", brotherhoods);
				result.addObject("miscellaneousRecord", miscellaneous);
			}
		}
		return result;
	}
	
	
	// Delete miscellaneous record ------------------------------------------------------------------------------------
	@RequestMapping(value = "/miscellaneousRecord/brotherhood/delete", method = RequestMethod.GET)
	public ModelAndView miscellaneousRecordDelete(@RequestParam final int miscellaneousId) {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			MiscellaneousRecord miscellaneous = this.miscService.findOne(miscellaneousId);
			Assert.isTrue(brotherhood.getHistory().getMiscellaneousRecords().contains(miscellaneous));
			
			this.miscService.delete(miscellaneous);

			result = new ModelAndView("redirect:/history/brotherhood/display.do");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
			
		}

		return result;
	}

}