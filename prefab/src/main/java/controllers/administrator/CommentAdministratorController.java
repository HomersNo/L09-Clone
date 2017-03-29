
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CommentableService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Comment;

@Controller
@RequestMapping("/comment/administrator")
public class CommentAdministratorController extends AbstractController {

	public CommentAdministratorController() {
		super();
	}


	@Autowired
	private CommentService		commentService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private CommentableService	commentableService;


	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView commentOffer(@RequestParam final int commentId) {
		ModelAndView result;

		Comment comment;

		try {

			comment = this.commentService.findOne(commentId);
			comment = this.commentService.banComment(comment);
			result = new ModelAndView("redirect:/commentable/actor/display.do?commentableId=" + comment.getCommentable().getId());
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/welcome/index.do");

		}

		return result;
	}
}
