
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
import domain.Administrator;
import domain.Customer;

@Controller
@RequestMapping("/actor/actor")
public class ActorActorController extends AbstractController {

	//Services

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ActorActorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int actorId) {

		ModelAndView result;
		Actor actor;

		actor = this.actorService.findOne(actorId);

		result = new ModelAndView("redirect:/welcome/index.do");

		if (actor instanceof Customer)
			result = new ModelAndView("redirect:/customer/actor/display.do?customerId=" + actor.getId());
		else if (actor instanceof Administrator)
			result = new ModelAndView("redirect:/folder/actor/list.do");
		return result;
	}
}
