
package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.PlaceService;
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
	private PlaceService	placeService;

	@Autowired
	private CustomerService	customerService;


	@RequestMapping(value = "/listRequests", method = RequestMethod.GET)
	public ModelAndView listRequests() {
		ModelAndView result;

		Collection<Post> requests;
		final FilterString filter = new FilterString();

		requests = this.postService.findAllRequests();

		result = new ModelAndView("post/list");
		result.addObject("requestURI", "post/customer/listRequests.do");
		result.addObject("posts", requests);
		result.addObject("filterString", filter);

		return result;
	}

	@RequestMapping(value = "/listOffers", method = RequestMethod.GET)
	public ModelAndView listOfferss() {
		ModelAndView result;

		Collection<Post> offers;
		final FilterString filter = new FilterString();

		offers = this.postService.findAllOffers();

		result = new ModelAndView("post/list");
		result.addObject("requestURI", "post/customer/listOffers.do");
		result.addObject("posts", offers);
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
		final ModelAndView result;

		Post post;

		post = this.postService.create();
		post.setType("REQUEST");

		result = this.createEditModelAndView(post);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int postId) {
		ModelAndView result;

		Post post;

		post = this.postService.findOne(postId);

		result = this.createEditModelAndView(post);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Post post, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(post);
		else
			try {
				post = this.postService.reconstruct(post, binding);
				post = this.postService.save(post);
				result = new ModelAndView("redirect:/post/display.do?postId=" + post.getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(post, "post.commit.error");
				result.addObject("post", post);
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

	@RequestMapping(value = "/filter", method = RequestMethod.POST, params = "filterButton")
	public ModelAndView filter(@Valid final FilterString filterString, final BindingResult binding) {

		ModelAndView result;
		Collection<Post> posts;
		final String filter = filterString.getFilter();
		if (binding.hasErrors())
			result = new ModelAndView("redirect:list.do");
		else
			try {
				posts = this.postService.findAllFiltered(filter);
				result = new ModelAndView("post/list");
				result.addObject("requestURI", "post/list.do");
				result.addObject("posts", posts);
				result.addObject("filterString", filterString);

			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:list.do");
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
