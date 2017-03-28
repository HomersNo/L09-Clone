
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
import services.CommentService;
import services.CommentableService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comment;
import domain.Commentable;

@Controller
@RequestMapping("/comment/actor")
public class CommentActorController extends AbstractController {

	//Services

	@Autowired
	private CommentService		commentService;

	@Autowired
	private CommentableService	commentableService;

	@Autowired
	private ActorService		actorService;


	//Contructor

	public CommentActorController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int commentableId) {

		ModelAndView result;
		final Commentable commentable;

		commentable = this.commentableService.findOne(commentableId);
		final Comment comment = this.commentService.create(commentable);

		result = this.createEditModelAndView(comment);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Comment comment, final BindingResult binding) {
		ModelAndView result;
		Comment saved;

		if (binding.hasErrors())
			result = this.createEditModelAndView(comment);
		else
			try {
				saved = this.commentService.save(comment);
				result = new ModelAndView("redirect:/commentable/actor/display.do?commentableId=" + comment.getCommentable().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}

		return result;
	}

	//TODO Cuando lanza la excepción a dónde lo mando?

	//TODO lo mismo que arriba

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String errorComment) {
		ModelAndView result;
		Collection<Actor> actors;

		actors = this.actorService.findAll();

		result = new ModelAndView("comment/edit");
		result.addObject("errorComment", errorComment);
		result.addObject("comment", comment);
		result.addObject("actors", actors);

		return result;
	}
}
