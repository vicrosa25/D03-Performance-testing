/*
 * WelcomeController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import services.ActorService;
import services.ConfigurationsService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	
	@Autowired
	private ActorService			actorService;
	
	@Autowired
	private ConfigurationsService	configurationsService;

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@CookieValue(value = "language", defaultValue = "") String language) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		String name;
		Actor actor;
		String englishMessage;
		String spanishMessage;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		
		englishMessage = this.configurationsService.getConfiguration().getEnglishMessage();
		spanishMessage = this.configurationsService.getConfiguration().getSpanishMessage();

		try {
			actor = this.actorService.findByPrincipal();
			name = actor.getName() + " " + actor.getSurname();
			result = new ModelAndView("welcome/index");
			result.addObject("moment", moment);
			result.addObject("name", name);
			result.addObject("englishMessage", englishMessage);
			result.addObject("spanishMessage", spanishMessage);
			result.addObject("language", language);
			return result;
		} catch (final Exception e) {
			result = new ModelAndView("welcome/index");
			result.addObject("moment", moment);
			result.addObject("englishMessage", englishMessage);
			result.addObject("spanishMessage", spanishMessage);
			result.addObject("language", language);
		}

		return result;
	}
	
	@RequestMapping(value = "/legal")
	public ModelAndView legal(){
		ModelAndView result;
		result = new ModelAndView("welcome/legal");
		return result;
	}
}
