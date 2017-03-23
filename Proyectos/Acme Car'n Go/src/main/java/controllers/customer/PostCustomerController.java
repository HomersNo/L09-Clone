
package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.PostService;
import controllers.AbstractController;
import domain.Post;
import forms.FilterString;

@Controller
@RequestMapping("/post/customer")
public class PostCustomerController extends AbstractController {

	public PostCustomerController() {
		super();
	}


	@Autowired
	private PostService		postService;

	@Autowired
	private CustomerService	customerService;


	@RequestMapping(value = "/listOwn", method = RequestMethod.GET)
	public ModelAndView listOwn() {
		ModelAndView result;

		Collection<Post> posts;
		final FilterString filter = new FilterString();

		posts = this.postService.findAllByCustomer(this.customerService.findByPrincipal());

		result = new ModelAndView("post/list");
		result.addObject("requestURI", "post/customer/listOwn.do");
		result.addObject("posts", posts);
		result.addObject("filterString", filter);

		return result;
	}

	@RequestMapping(value = "/postOffer", method = RequestMethod.GET)
	public ModelAndView postOffer() {
		ModelAndView result;

		Post offer;

		offer = this.postService.create();
		offer.setType("OFFER");

		result = new ModelAndView("post/edit");
		result.addObject("post", offer);

		return result;
	}

	@RequestMapping(value = "/postRequest", method = RequestMethod.GET)
	public ModelAndView postRequest() {
		ModelAndView result;

		Post post;

		post = this.postService.create();
		post.setType("REQUEST");

		result = this.createEditModelAndView(post);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Post post, final BindingResult binding) {

		ModelAndView result;

		if (post.getOrigin().getLatitude() == null) {
			if (post.getOrigin().getLongitude() != null)
				binding.rejectValue("origin.latitude", "javax.validation.constraints.NotNull.message");
		} else if (post.getOrigin().getLongitude() == null)
			if (post.getOrigin().getLatitude() != null)
				binding.rejectValue("origin.longitude", "javax.validation.constraints.NotNull.message");
		if (post.getDestination().getLatitude() == null) {
			if (post.getDestination().getLongitude() != null)
				binding.rejectValue("destination.latitude", "javax.validation.constraints.NotNull.message");
		} else if (post.getDestination().getLongitude() == null)
			if (post.getDestination().getLatitude() != null)
				binding.rejectValue("destination.longitude", "javax.validation.constraints.NotNull.message");

		if (binding.hasErrors())
			result = this.createEditModelAndView(post);
		else
			try {

				post = this.postService.reconstruct(post, binding);
				post = this.postService.save(post);
				result = new ModelAndView("redirect:/post/display.do?postId=" + post.getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(post, "post.commit.error");

			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Post post, final BindingResult binding) {
		ModelAndView result;

		try {
			post = this.postService.reconstruct(post, binding);
			this.postService.delete(post);
			result = new ModelAndView("redirect:/post/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(post, "post.commit.error");
		}

		return result;
	}

	// Ancillary Methods

	protected ModelAndView createEditModelAndView(final Post post) {
		ModelAndView result;

		result = this.createEditModelAndView(post, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Post post, final String message) {
		ModelAndView result;

		result = new ModelAndView("post/edit");
		result.addObject("post", post);
		result.addObject("message", message);

		return result;
	}

}
