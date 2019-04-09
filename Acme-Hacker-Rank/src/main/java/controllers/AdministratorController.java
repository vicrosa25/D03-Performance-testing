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

import org.hibernate.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import domain.Company;
import domain.Hacker;
import domain.Position;
import services.ActorService;
import services.AdministratorService;
import services.ConfigurationsService;
import utilities.Md5;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationsService	configurationsService;


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
			//admin.setMessageBoxes(this.messageBoxService.createSystemMessageBox());
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

	/*******************************************************************
	 * 
	 * Admin Dashboard Queries 
	 * 
	 *******************************************************************/
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		final ModelAndView result;

		// Queries level C
		Object[] query1 				= this.administratorService.query1();
		Object[] query2 				= this.administratorService.query2();
		Collection<Company> query3 		= this.administratorService.query3();
		Collection<Hacker> query4 		= this.administratorService.query4();
		Object[] query5 				= this.administratorService.query5();
		Collection<Position> query6		= this.administratorService.query6();


		result = new ModelAndView("administrator/dashboard");

		result.addObject("query1", query1);
		result.addObject("query2", query2);
		result.addObject("query3", query3);
		result.addObject("query4", query4);
		result.addObject("query5", query5);
		result.addObject("query6", query6);
//		result.addObject("query8", query8);
//		result.addObject("query9", query9);
//		result.addObject("query10", query10);
//		result.addObject("query11", query11);


//		int spammers = this.administratorService.queryGetSpammers();
//		int notSpammers = this.administratorService.queryGetNotSpammers();
//		Double averagePolarity = this.administratorService.getAveragePolarity();

//		result.addObject("spammers", spammers);
//		result.addObject("notSpammers", notSpammers);
//		result.addObject("averagePolarity", averagePolarity);

		return result;
	}

}
