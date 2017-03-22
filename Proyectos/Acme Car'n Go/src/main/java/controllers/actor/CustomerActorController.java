
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comment;

@Controller
@RequestMapping("/customer/actor")
public class CustomerActorController extends AbstractController {

	// Services -----------------------------------------------------------------------
	@Autowired
	private ActorService	actorService;

	@Autowired
	private CommentService	commentService;

	@Autowired
	private CustomerService	customerService;


	// Constructor --------------------------------------------------------------------
	public CustomerActorController() {
		super();
	}

	// Listing ------------------------------------------------------------------------	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final int customerId) {

		ModelAndView result;
		Actor actor;

		Collection<Comment> adminComments;
		Collection<Comment> customerComments;

		if (customerId == 0)
			actor = this.customerService.findByPrincipal();

		else
			actor = this.customerService.findOne(customerId);

		adminComments = this.commentService.findAllByCommentableId(actor.getId());
		customerComments = this.commentService.findNotBannedCommentsCustomer(actor.getId());
		result = new ModelAndView("customer/display");
		result.addObject("customer", actor);
		result.addObject("requestURI", "/customer/actor/display.do?customerId=" + actor.getId());
		result.addObject("adminComments", adminComments);
		result.addObject("customerComments", customerComments);

		return result;
	}
	// Creation -----------------------------------------------------------------------

	// Edition ------------------------------------------------------------------------

	// Ancillary methods --------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String errorMessage) {
		ModelAndView result;

		result = new ModelAndView("message/edit");
		result.addObject("errorMessage", errorMessage);
		result.addObject("actor", actor);

		return result;
	}

}
