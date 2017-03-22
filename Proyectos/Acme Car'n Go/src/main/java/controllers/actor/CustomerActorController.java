
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("/customer/actor")
public class CustomerActorController extends AbstractController {

	// Services -----------------------------------------------------------------------
	@Autowired
	private ActorService	actorService;


	// Constructor --------------------------------------------------------------------
	public CustomerActorController() {
		super();
	}

	// Listing ------------------------------------------------------------------------	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final int actorId) {

		ModelAndView result;
		Actor actor;

		if (actorId == 0)
			actor = this.actorService.findByPrincipal();
		else
			actor = this.actorService.findOne(actorId);
		result = new ModelAndView("customer/actor/display");
		result.addObject("actor", actor);

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
