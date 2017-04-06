
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentableService;
import controllers.AbstractController;
import domain.Commentable;
import domain.Customer;
import domain.Post;

@Controller
@RequestMapping("/commentable/actor")
public class CommentableActorController extends AbstractController {

	//Services

	@Autowired
	private CommentableService	commentableService;


	// Constructors -----------------------------------------------------------

	public CommentableActorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int commentableId) {

		ModelAndView result;
		Commentable commentable;

		result = new ModelAndView("redirect:/welcome/index.do");
		commentable = this.commentableService.findOne(commentableId);

		if (commentable instanceof Customer)
			result = new ModelAndView("redirect:/customer/actor/display.do?customerId=" + commentable.getId());
		else if (commentable instanceof Post)
			result = new ModelAndView("redirect:/post/display.do?postId=" + commentable.getId());
		return result;
	}
}
