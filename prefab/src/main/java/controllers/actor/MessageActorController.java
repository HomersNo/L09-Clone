
package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Message;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	//Services

	@Autowired
	private MessageService	messageService;

	@Autowired
	private FolderService	folderService;

	@Autowired
	private ActorService	actorService;


	//Contructor

	public MessageActorController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int folderId) {

		ModelAndView result;
		Collection<Message> messages;
		final String requestURI = "message/actor/list.do?folderId=" + folderId;

		try {
			messages = this.messageService.findAllByFolder(folderId);
			result = new ModelAndView("message/list");
			result.addObject("messages", messages);
			result.addObject("requestURI", requestURI);
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/folder/actor/list.do");
			result.addObject("errorMessage", "message.folder.wrong");

		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		final Message message = this.messageService.create();

		result = this.createEditModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Message message, final BindingResult binding) {
		Actor principal;
		ModelAndView result;
		Message sent;

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {
				sent = this.messageService.send(message);
				principal = this.actorService.findByPrincipal();
				result = new ModelAndView("redirect:/message/actor/list.do?folderId=" + this.folderService.findSystemFolder(principal, "Outbox").getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
			}

		return result;
	}

	//TODO Cuando lanza la excepción a dónde lo mando?
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result;

		Message message;

		try {
			message = this.messageService.findOne(messageId);
			this.messageService.delete(message);
			result = new ModelAndView("redirect:/message/actor/list.do?folderId=" + message.getFolder().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("errorMessage", "message.commit.error");
		}

		return result;
	}

	//TODO lo mismo que arriba

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String errorMessage) {
		ModelAndView result;
		Collection<Actor> actors;

		actors = this.actorService.findAll();

		result = new ModelAndView("message/edit");
		result.addObject("errorMessage", errorMessage);
		result.addObject("message", message);
		result.addObject("actors", actors);

		return result;
	}
}
