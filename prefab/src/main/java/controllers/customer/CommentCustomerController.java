
package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CommentableService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Comment;
import domain.Commentable;
import domain.Customer;

@Controller
@RequestMapping("/comment/customer")
public class CommentCustomerController extends AbstractController {

	public CommentCustomerController() {
		super();
	}


	@Autowired
	private CommentService		commentService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private CommentableService	commentableService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView commentOffer(@RequestParam final int commentableId) {
		ModelAndView result;

		Comment comment;
		Customer principal;
		Commentable commentable;

		commentable = this.commentableService.findOne(commentableId);
		principal = this.customerService.findByPrincipal();
		comment = this.commentService.create(commentable);

		result = this.createEditModelAndView(comment);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Comment comment, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(comment);
		else
			try {
				comment = this.commentService.save(comment);
				result = new ModelAndView("redirect:/commentable/actor/display.do?commentableId=" + comment.getCommentable().getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
				result.addObject("comment", comment);
			}

		return result;
	}

	// Ancillary Methods

	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		ModelAndView result;

		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("errorMessage", message);

		return result;
	}

}
