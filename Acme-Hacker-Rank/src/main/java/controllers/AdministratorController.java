/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.validation.Valid;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ConfigurationsService;
import services.MessageBoxService;
import services.SponsorshipService;
import utilities.Md5;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Chapter;
import domain.Configurations;
import domain.Procession;
import domain.Sponsorship;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private ConfigurationsService	configurationsService;

	@Autowired
	private SponsorshipService		sponsorshipService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		JOptionPane.showMessageDialog(null, "Forbidden operation");
		return new ModelAndView("redirect:/");
	}

	// List -------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Administrator> admins;

		admins = this.administratorService.findAll();

		result = new ModelAndView("administrator/list");
		result.addObject("administrators", admins);
		result.addObject("requestURI", "administrator/list.do");

		return result;
	}

	// Create -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Administrator admin;

		admin = this.administratorService.create();

		result = new ModelAndView("administrator/create");
		result.addObject("administrator", admin);

		return result;
	}

	// Save -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator admin, final BindingResult binding) {
		ModelAndView result;
		String password;
		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());
			admin.setMessageBoxes(this.messageBoxService.createSystemMessageBox());
			result = new ModelAndView("administrator/create");
			result.addObject("administrator", admin);
		} else
			try {
				password = Md5.encodeMd5(admin.getUserAccount().getPassword());
				admin.getUserAccount().setPassword(password);
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("administrator/create");
				result.addObject("administrator", admin);
				if (oops instanceof DataIntegrityViolationException)
					result.addObject("message", "admin.duplicated.username");
				else {
					System.out.println(oops.getCause().toString());
					result.addObject("message", "admin.registration.error");
				}
			}
		return result;
	}

	// Update -----------------------------------------------------------
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		result = new ModelAndView("administrator/update");
		result.addObject("administrator", admin);

		return result;
	}

	// Save Update ----------------------------------------------------------
	@RequestMapping(value = "/update", method = RequestMethod.POST, params = "update")
	public ModelAndView edit(@Valid final Administrator admin, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());
			result = new ModelAndView("administrator/update");
			result.addObject("administrator", admin);
		} else
			try {
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("administrator/update");
				result.addObject("administrator", admin);
				result.addObject("message", "administrator.commit.error");
			}
		return result;
	}

	// Dashboard -----------------------------------------------------------
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		final ModelAndView result;

		// Queries level C
		Object[] query1 = this.administratorService.query1();
		Collection<Brotherhood> query2 = this.administratorService.query2();
		Collection<Brotherhood> query3 = this.administratorService.query3();
		Collection<Double> query4 = this.administratorService.query4();
		Collection<Procession> query5 = this.administratorService.query5();
		Collection<Object> query7 = this.administratorService.query7();
		Collection<Object> query8 = this.administratorService.query8();

		// Queries level B
		Object[] query9 = this.administratorService.query9();
		Object[] query10 = this.administratorService.query10();
		Double query11 = this.administratorService.query11();

		// ACME PARADE level C
		Object[] query12 = this.administratorService.query12();
		Brotherhood query13 = this.administratorService.query13();
		Collection<Brotherhood> query14 = this.administratorService.query14();

		// ACME PARADE level B
		Double query15 = this.administratorService.query15();
		Object[] query16 = this.administratorService.query16();
		Collection<Chapter> query17 = this.administratorService.query17();
		Double query18 = this.administratorService.query18();
		Collection<Object> query19 = this.administratorService.query19();
		
		// ACME PARADE level A
		Double query20 = this.administratorService.query20();
		Object[] query21 = this.administratorService.query21();
		Collection<Object> query22 = this.administratorService.query22(5);

		result = new ModelAndView("administrator/dashboard");

		result.addObject("query1", query1);
		result.addObject("query2", query2);
		result.addObject("query3", query3);
		result.addObject("query4", query4);
		result.addObject("query5", query5);
		result.addObject("query7", query7);
		result.addObject("query8", query8);
		result.addObject("query9", query9);
		result.addObject("query10", query10);
		result.addObject("query11", query11);
		result.addObject("query12", query12);
		result.addObject("query13", query13);
		result.addObject("query14", query14);
		result.addObject("query15", query15);
		result.addObject("query16", query16);
		result.addObject("query17", query17);
		result.addObject("query18", query18);
		result.addObject("query19", query19);
		result.addObject("query20", query20);
		result.addObject("query21", query21);
		result.addObject("query22", query22);
		

		int spammers = this.administratorService.queryGetSpammers();
		int notSpammers = this.administratorService.queryGetNotSpammers();
		Double averagePolarity = this.administratorService.getAveragePolarity();

		result.addObject("spammers", spammers);
		result.addObject("notSpammers", notSpammers);
		result.addObject("averagePolarity", averagePolarity);

		return result;
	}

	/**
	 * 
	 * SPAM
	 * ****************************************************************************
	 */

	// Spammer list ---------------------------------------------------------------------
	@RequestMapping(value = "/spammers", method = RequestMethod.GET)
	public ModelAndView suspiciousList() {
		ModelAndView result;
		Collection<Actor> suspicious;

		suspicious = this.administratorService.getSpammers();

		result = new ModelAndView("administrator/spammers");
		result.addObject("suspicious", suspicious);
		result.addObject("requestURI", "administrator/spammers.do");

		return result;
	}

	// Compute Spammers -------------------------------------------------------------------
	@RequestMapping(value = "/computeSpammers", method = RequestMethod.GET)
	public ModelAndView computeSpammers() {

		this.administratorService.computeSpammers();

		return this.suspiciousList();
	}

	// Ban
	// -----------------------------------------------------------------------------------
	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor = null;

		try {
			actor = this.actorService.findOne(actorId);
		} catch (final Exception e) {
			result = this.forbiddenOpperation();
			return result;
		}

		this.administratorService.banAnActor(actor);

		result = this.suspiciousList();
		return result;
	}

	// Unban
	// -----------------------------------------------------------------------------------
	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor = null;

		try {
			actor = this.actorService.findOne(actorId);
		} catch (final Exception e) {
			result = this.forbiddenOpperation();
			return result;
		}
		this.administratorService.unBanAnActor(actor);

		result = this.suspiciousList();

		return result;
	}

	// Ancillary methods
	// -----------------------------------------------------------------------
	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

	/**
	 * 
	 * POLARITY SCORE
	 * ****************************************************************************
	 */

	// Score list
	// -------------------------------------------------------------------
	@RequestMapping(value = "/score", method = RequestMethod.GET)
	public ModelAndView scoreList() {
		ModelAndView result;
		Collection<Actor> users;

		users = this.actorService.findAll();
		users.remove(this.administratorService.findByPrincipal());

		result = new ModelAndView("administrator/score");
		result.addObject("users", users);
		result.addObject("requestURI", "administrator/score.do");

		return result;
	}

	// Score Compute
	// -------------------------------------------------------------------
	@RequestMapping(value = "/computeScore", method = RequestMethod.GET)
	public ModelAndView computeScoreList() {
		ModelAndView result;
		Collection<Actor> users;

		this.administratorService.computeAllScores();

		users = this.actorService.findAll();
		users.remove(this.administratorService.findByPrincipal());

		result = new ModelAndView("administrator/score");
		result.addObject("users", users);
		// result.addObject("requestURI", "administrator/score.do");

		return result;
	}

	/**
	 * 
	 * Manage polarity Words
	 * ****************************************************************************
	 */

	// List Words-------------------------------------------------------------
	@RequestMapping(value = "/config/polarityWords/list", method = RequestMethod.GET)
	public ModelAndView wordList() {
		ModelAndView result;
		Collection<String> positiveWords;
		Collection<String> negativeWords;

		positiveWords = this.configurationsService.getConfiguration().getPositiveWords();
		negativeWords = this.configurationsService.getConfiguration().getNegativeWords();

		result = new ModelAndView("administrator/config/polarityWords/list");
		result.addObject("requestURI", "administrator/config/polarityWords/list.do");
		result.addObject("positiveWords", positiveWords);
		result.addObject("negativeWords", negativeWords);

		return result;
	}

	// Add + Words
	// GET-------------------------------------------------------------
	@RequestMapping(value = "/config/polarityWords/addPositiveWord", method = RequestMethod.GET)
	public ModelAndView addPosWord() {
		ModelAndView result;

		result = new ModelAndView("administrator/config/polarityWords/add");
		result.addObject("action", "administrator/config/polarityWords/addPositiveWord.do");
		return result;
	}

	// Add + Word SAVE
	// ------------------------------------------------------------------
	@RequestMapping(value = "/config/polarityWords/addPositiveWord", method = RequestMethod.POST, params = "save")
	public ModelAndView addPosWord(@RequestParam("word") final String word) {
		ModelAndView result;

		try {
			// Add the word and update configurations
			this.administratorService.addPositiveWord(word);
		} catch (final Exception e) {
			result = new ModelAndView("administrator/config/polarityWords/add");
			result.addObject("action", "administrator/config/polarityWords/addPositiveWord.do");
			result.addObject("message", "config.field.error");
			return result;
		}

		result = this.wordList();

		return result;
	}

	// Edit word GET
	// ------------------------------------------------------------------
	@RequestMapping(value = "/config/polarityWords/editPositiveWord", method = RequestMethod.GET)
	public ModelAndView editPosWord(@RequestParam("word") final String word, @RequestParam("index") final int index) {
		ModelAndView result;

		result = new ModelAndView("administrator/config/polarityWords/edit");
		result.addObject("action", "administrator/config/polarityWords/editPositiveWord.do");

		result.addObject("word", word);
		result.addObject("index", index);

		return result;
	}

	// Edit word SAVE
	// ------------------------------------------------------------------
	@RequestMapping(value = "/config/polarityWords/editPositiveWord", method = RequestMethod.POST, params = "save")
	public ModelAndView editPosWordPost(@RequestParam("word") final String word, @RequestParam("index") final int index) {
		ModelAndView result;

		try {
			// Add the word and update configurations
			this.administratorService.editPositiveWord(word, index - 1);
		} catch (final Exception e) {
			e.printStackTrace();
			result = new ModelAndView("administrator/config/polarityWords/edit");
			result.addObject("action", "administrator/config/polarityWords/editPositiveWord.do");

			result.addObject("word", word);
			result.addObject("index", index);

			result.addObject("message", "config.field.error");

			return result;
		}

		result = this.wordList();

		return result;
	}

	// Remove positive word
	// ------------------------------------------------------------------
	@RequestMapping(value = "/config/polarityWords/removePositiveWord", method = RequestMethod.GET)
	public ModelAndView removePosWord(@RequestParam("word") final String word) {

		this.administratorService.removePositiveWord(word);

		return this.wordList();
	}

	// Add - Words
	// GET-------------------------------------------------------------
	@RequestMapping(value = "/config/polarityWords/addNegativeWord", method = RequestMethod.GET)
	public ModelAndView addNegativeWord() {
		ModelAndView result;

		result = new ModelAndView("administrator/config/polarityWords/add");
		result.addObject("action", "administrator/config/polarityWords/addNegativeWord.do");
		return result;
	}

	// Add - Words
	// POS-------------------------------------------------------------
	@RequestMapping(value = "/config/polarityWords/addNegativeWord", method = RequestMethod.POST, params = "save")
	public ModelAndView addNegativeWord(@RequestParam("word") final String word) {
		ModelAndView result;

		try {
			// Add the word and update configurations
			this.administratorService.addNegativeWord(word);
		} catch (final Exception e) {
			result = new ModelAndView("administrator/config/polarityWords/add");
			result.addObject("action", "administrator/config/polarityWords/addNegativeWord.do");
			result.addObject("message", "config.field.error");
			return result;
		}

		result = this.wordList();

		return result;
	}

	// Edit word GET
	// ------------------------------------------------------------------
	@RequestMapping(value = "/config/polarityWords/editNegativeWord", method = RequestMethod.GET)
	public ModelAndView editNegWord(@RequestParam("word") final String word, @RequestParam("index") final int index) {
		ModelAndView result;

		result = new ModelAndView("administrator/config/polarityWords/edit");
		result.addObject("action", "administrator/config/polarityWords/editNegativeWord.do");

		result.addObject("word", word);
		result.addObject("index", index);

		return result;
	}

	// Edit word SAVE
	// ------------------------------------------------------------------
	@RequestMapping(value = "/config/polarityWords/editNegativeWord", method = RequestMethod.POST, params = "save")
	public ModelAndView editNegWordPost(@RequestParam("word") final String word, @RequestParam("index") final int index) {
		ModelAndView result;

		try {
			// Add the word and update configurations
			this.administratorService.editNegativeWord(word, index - 1);
		} catch (final Exception e) {
			e.printStackTrace();
			result = new ModelAndView("administrator/config/polarityWords/edit");
			result.addObject("action", "administrator/config/polarityWords/editNegativeWord.do");

			result.addObject("word", word);
			result.addObject("index", index);

			result.addObject("message", "config.field.error");

			return result;
		}

		result = this.wordList();

		return result;
	}

	// Remove negative word
	// ------------------------------------------------------------------
	@RequestMapping(value = "/config/polarityWords/removeNegativeWord", method = RequestMethod.GET)
	public ModelAndView removeNegWord(@RequestParam("word") final String word) {

		this.administratorService.removeNegativeWord(word);
		return this.wordList();
	}

	/**
	 * 
	 * Manage CACHE ****************************************************************************
	 */

	// Configurations cache -------------------------------------------------------------
	@RequestMapping(value = "/config/cache/edit", method = RequestMethod.GET)
	public ModelAndView cache() {
		ModelAndView result;
		Configurations configurations;

		configurations = this.configurationsService.getConfiguration();
		result = new ModelAndView("administrator/config/cache/edit");
		result.addObject("configurations", configurations);

		return result;
	}

	@RequestMapping(value = "/config/cache/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView cache(@Valid final Configurations configurations, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());
			result = new ModelAndView("administrator/config/cache/edit");
			result.addObject("configurations", configurations);
		} else
			try {
				this.configurationsService.update(configurations);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = new ModelAndView("administrator/config/cache/edit");
				result.addObject("configurations", configurations);
				result.addObject("message", "administrator.commit.error");
			}

		return result;
	}

	/**
	 * 
	 * Settings ****************************************************************************
	 */

	@RequestMapping(value = "/config/aliveConfig/edit", method = RequestMethod.GET)
	public ModelAndView config() {
		ModelAndView result;
		Configurations configurations;

		configurations = this.configurationsService.getConfiguration();
		result = new ModelAndView("administrator/config/aliveConfig/edit");
		result.addObject("configurations", configurations);

		return result;
	}

	@RequestMapping(value = "/config/aliveConfig/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView config(@Valid Configurations configurations, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());
			result = new ModelAndView("administrator/config/aliveConfig/edit");
			result.addObject("configurations", configurations);
		} else
			try {
				this.configurationsService.update(configurations);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = new ModelAndView("administrator/config/aliveConfig/edit");
				result.addObject("configurations", configurations);
				result.addObject("message", "administrator.commit.error");
			}

		return result;
	}

	/**
	 * 
	 * Manage Spam Word ****************************************************************************
	 */

	// List SPAM Words-------------------------------------------------------------
	@RequestMapping(value = "config/spam/list", method = RequestMethod.GET)
	public ModelAndView spamWordList() {
		ModelAndView result;
		Collection<String> spamWords;

		spamWords = this.configurationsService.getConfiguration().getSpamWords();

		result = new ModelAndView("administrator/config/spam/list");
		result.addObject("requestURI", "administrator/config/spam/list.do");
		result.addObject("spamWords", spamWords);

		return result;
	}

	// Add  - SPAM Words GET-------------------------------------------------------------
	@RequestMapping(value = "/config/spam/add", method = RequestMethod.GET)
	public ModelAndView addSpamWord() {
		ModelAndView result;

		result = new ModelAndView("administrator/config/spam/add");
		result.addObject("action", "administrator/config/spam/add.do");
		return result;
	}

	// Add  - SPAM Words POS-------------------------------------------------------------
	@RequestMapping(value = "/config/spam/add", method = RequestMethod.POST, params = "save")
	public ModelAndView addSpamWord(@RequestParam("word") final String word) {
		ModelAndView result;

		try {
			// Add the word and update configurations
			this.administratorService.addSpamWord(word);
		} catch (final Exception e) {
			result = new ModelAndView("administrator/config/spam/add");
			result.addObject("action", "administrator/config/spam/add.do");
			result.addObject("message", "config.field.error");
			return result;
		}

		result = this.spamWordList();

		return result;
	}

	// Edit SPAM Word GET ------------------------------------------------------------------
	@RequestMapping(value = "/config/spam/edit", method = RequestMethod.GET)
	public ModelAndView editSpamWord(@RequestParam("word") final String word, @RequestParam("index") final int index) {
		ModelAndView result;

		result = new ModelAndView("administrator/config/spam/edit");
		result.addObject("action", "administrator/config/spam/edit.do");

		result.addObject("word", word);
		result.addObject("index", index);

		return result;
	}

	// Edit SPAM word SAVE ------------------------------------------------------------------
	@RequestMapping(value = "/config/spam/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSpamWordPost(@RequestParam("word") final String word, @RequestParam("index") final int index) {
		ModelAndView result;

		try {
			// Add the word and update configurations
			this.administratorService.editSpamWord(word, index - 1);
		} catch (final Exception e) {
			e.printStackTrace();
			result = new ModelAndView("administrator/config/spam/edit");
			result.addObject("action", "administrator/config/spam/edit.do");

			result.addObject("word", word);
			result.addObject("index", index);

			result.addObject("message", "config.field.error");

			return result;
		}

		result = this.spamWordList();

		return result;
	}

	// Remove SPAM word ------------------------------------------------------------------
	@RequestMapping(value = "/removeSpamWord", method = RequestMethod.GET)
	public ModelAndView removeSpamWord(@RequestParam("word") final String word) {

		this.administratorService.removeSpamWord(word);
		return this.spamWordList();
	}

	/**
	 * 
	 * Process to deactivate Sponsorships **********************************************************************
	 */

	// List -------------------------------------------------------------
	@RequestMapping(value = "/sponsorship/list", method = RequestMethod.GET)
	public ModelAndView sponsorList() {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		try {
			sponsorships = this.sponsorshipService.findAll();

			result = new ModelAndView("administrator/sponsorship/list");
			result.addObject("sponsorships", sponsorships);
		} catch (final Throwable oops) {
			result = this.forbiddenOpperation();
		}
		return result;
	}
	
	
	// Launch process
	@RequestMapping(value = "/computeSponsorship", method = RequestMethod.GET)
	public ModelAndView computeSponsorship() {
		ModelAndView result;

		this.administratorService.deactivateSponsorships();

		result = this.sponsorList();
		return result;
	}
	
	/**
	 * 
	 * BRANDS ****************************************************************************
	 */

	// List Brands-------------------------------------------------------------
	@RequestMapping(value = "/config/brand/list", method = RequestMethod.GET)
	public ModelAndView brandList() {
		ModelAndView result;
		Collection<String> brands;

		brands = this.configurationsService.getConfiguration().getBrandName();

		result = new ModelAndView("administrator/config/brand/list");
		result.addObject("requestURI", "administrator/config/brand/list.do");
		result.addObject("brands", brands);

		return result;
	}

	// Add  Brand GET-------------------------------------------------------------
	@RequestMapping(value = "/config/brand/add", method = RequestMethod.GET)
	public ModelAndView addBrand() {
		ModelAndView result;

		result = new ModelAndView("administrator/config/brand/add");
		result.addObject("action", "administrator/config/brand/add.do");
		return result;
	}

	// Add  Brand POST-------------------------------------------------------------
	@RequestMapping(value = "/config/brand/add", method = RequestMethod.POST, params = "save")
	public ModelAndView addBrand(@RequestParam("brand") final String brand) {
		ModelAndView result;

		try {
			// Add the word and update configurations
			this.administratorService.addBrand(brand);
		} catch (final Exception e) {
			result = new ModelAndView("administrator/config/brand/add");
			result.addObject("action", "administrator/config/brand/add.do");
			result.addObject("message", "config.field.error");
			return result;
		}

		result = this.brandList();

		return result;
	}

	// Edit Brand GET ------------------------------------------------------------------
	@RequestMapping(value = "/config/brand/edit", method = RequestMethod.GET)
	public ModelAndView editBrand(@RequestParam("brand") final String brand, @RequestParam("index") final int index) {
		ModelAndView result;

		result = new ModelAndView("administrator/config/brand/edit");
		result.addObject("action", "administrator/config/brand/edit.do");

		result.addObject("brand", brand);
		result.addObject("index", index);

		return result;
	}

	// Edit Brand SAVE ------------------------------------------------------------------
	@RequestMapping(value = "/config/brand/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editBrandPost(@RequestParam("brand") final String brand, @RequestParam("index") final int index) {
		ModelAndView result;

		try {
			// Add the word and update configurations
			this.administratorService.editBrand(brand, index - 1);
		} catch (final Exception e) {
			e.printStackTrace();
			result = new ModelAndView("administrator/config/brand/edit");
			result.addObject("action", "administrator/config/brand/edit.do");

			result.addObject("brand", brand);
			result.addObject("index", index);

			result.addObject("message", "config.field.error");

			return result;
		}

		return this.brandList();
	}

	// Remove Brand ------------------------------------------------------------------
	@RequestMapping(value = "/config/brand/remove", method = RequestMethod.GET)
	public ModelAndView removeBrand(@RequestParam("word") final String word) {

		this.administratorService.removeBrand(word);

		return this.brandList();
	}

	// Inform security breach -------------------------------------------------------------
	@RequestMapping(value = "/securityBreach", method = RequestMethod.GET)
	public ModelAndView informBreach() {
		ModelAndView result;

		try {
		this.administratorService.informSecurityBreach();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		result = new ModelAndView("redirect:/");
		return result;
	}

}
