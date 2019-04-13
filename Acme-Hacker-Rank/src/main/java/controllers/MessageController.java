
package controllers;

import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.validation.Valid;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Message;
import services.ActorService;
import services.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		JOptionPane.showMessageDialog(null, "Forbidden operation");
		return new ModelAndView("redirect:/");
	}

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.registerCustomEditor(Collection.class, "recipients", new CustomCollectionEditor(Collection.class) {

			@Override
			protected Object convertElement(final Object element) {
				Integer id = null;

				if (element instanceof String && !((String) element).equals(""))
					// From the JSP 'element' will be a String
					try {
						id = Integer.parseInt((String) element);
					} catch (final NumberFormatException e) {
						System.out.println("Element was " + ((String) element));
						e.printStackTrace();
					}
				else if (element instanceof Integer)
					// From the database 'element' will be a Long
					id = (Integer) element;

				return id != null ? MessageController.this.actorService.findOne(id) : null;
			}
		});
	}

	// List -------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Message> messages;
		Actor principal;

		try {
			principal = this.actorService.findByPrincipal();
			messages = principal.getMessages();
			Assert.isTrue(principal.getMessages().equals(messages));
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/list.do");

		return result;
	}

	// Create -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message mesage;

		mesage = this.messageService.create();
		
		mesage.setIsNotification(false);
		result = this.createModelAndView(mesage);

		return result;
	}

	// Send -------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("mesage") @Valid Message mesage, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			List<ObjectError> errors = binding.getAllErrors();
			for (ObjectError e : errors)
				System.out.println(e.toString());
			result = this.createModelAndView(mesage);
		} else
			try {
				this.messageService.save(mesage);
				result = this.list();
			} catch (final Throwable oops) {
				result = this.createModelAndView(mesage, "message.commit.error");
			}
		return result;
	}

	// Create Broadcast
	// ------------------------------------------------------------------------
	@RequestMapping(value = "/admin/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast() {
		ModelAndView result;
		Message mesage;

		mesage = this.messageService.create();
		mesage.setIsNotification(true);

		result = new ModelAndView("message/admin/broadcast");
		result.addObject("mesage", mesage);

		return result;
	}

	// Send Broadcast 
	// -------------------------------------------------------------
	@RequestMapping(value = "/admin/broadcast", method = RequestMethod.POST, params = "send")
	public ModelAndView sendBroadcast(@ModelAttribute("mesage") @Valid Message mesage, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			List<ObjectError> errors = binding.getAllErrors();
			for (ObjectError e : errors)
				System.out.println(e.toString());
			result = new ModelAndView("message/broadcast");
			result.addObject("mesage", mesage);
		} else
			try {
				Collection<Actor> recipients = this.actorService.findAll();
				recipients.remove(this.actorService.findByPrincipal());
				mesage.setRecipients(recipients);
				this.messageService.save(mesage);
				result = this.list();
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				oops.printStackTrace();
				result = new ModelAndView("message/admin/broadcast");
				result.addObject("mesage", mesage);
				result.addObject("message", "message.commit.error");
			}
		return result;
	}

	// Display ---------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int messageID) {
		ModelAndView result;
		Message message;

		try {
			Actor principal = this.actorService.findByPrincipal();
			message = this.messageService.findOne(messageID);
			Assert.isTrue(principal.getMessages().contains(message));
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}

		result = new ModelAndView("message/display");
		result.addObject("mesage", message);
		result.addObject("mesageRecipients", message.getRecipients());

		return result;
	}

	// Delete ------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int messageID) {
		ModelAndView result;
		Message message;
		Actor principal;

		try {
			try {
				principal = this.actorService.findByPrincipal();
				message = this.messageService.findOne(messageID);
				Assert.isTrue(principal.getMessages().contains(message));
			} catch (final Exception e) {
				result = this.forbiddenOperation();
				return result;
			}
			this.messageService.delete(message);
			result = this.list();
		} catch (final Throwable oops) {
			message = this.messageService.findOne(messageID);
			result = this.createModelAndView(message, "message.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createModelAndView(Message mesage) {
		ModelAndView result;

		result = this.createModelAndView(mesage, null);

		return result;
	}

	protected ModelAndView createModelAndView(Message mesage, String message) {
		ModelAndView result;

		final Collection<Actor> actorList = this.actorService.findAll();

		result = new ModelAndView("message/create");
		result.addObject("mesage", mesage);
		result.addObject("message", message);
		result.addObject("actorList", actorList);

		return result;
	}

	private ModelAndView forbiddenOperation() {
		return this.list();
	}
}
