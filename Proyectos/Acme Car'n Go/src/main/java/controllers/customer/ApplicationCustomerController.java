
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.PostService;
import controllers.AbstractController;
import domain.Application;
import domain.Post;

@Controller
@RequestMapping("/application/customer")
public class ApplicationCustomerController extends AbstractController {

	public ApplicationCustomerController() {
		super();
	}


	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private PostService			postService;


	@RequestMapping(value = "/listReceived", method = RequestMethod.GET)
	public ModelAndView listApplications() {
		ModelAndView result;

		Collection<Application> applications;

		// If there isnt a PostId, it will list all the applications that the customer has received in each of his posts

		applications = this.applicationService.findAllReceived(this.customerService.findByPrincipal().getId());

		result = new ModelAndView("application/list");
		result.addObject("requestURI", "application/customer/listReceived.do");
		result.addObject("posts", applications);

		return result;
	}

	@RequestMapping(value = "/listSent", method = RequestMethod.GET)
	public ModelAndView listSentApplications() {
		ModelAndView result;

		Collection<Application> applications;

		applications = this.applicationService.findAllByCustomerId(this.customerService.findByPrincipal().getId());

		result = new ModelAndView("application/list");
		result.addObject("requestURI", "application/customer/listSent.do");
		result.addObject("posts", applications);

		return result;
	}

	@RequestMapping(value = "/apply", method = RequestMethod.GET)
	public ModelAndView apply(@RequestParam final int postId) {
		final ModelAndView result;
		Post post;

		post = this.postService.findOne(postId);

		this.applicationService.apply(post);

		result = this.listSentApplications();

		return result;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);

		this.applicationService.accept(application);

		result = this.listApplications();

		return result;
	}

	@RequestMapping(value = "/deny", method = RequestMethod.GET)
	public ModelAndView deny(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);

		this.applicationService.deny(application);

		result = this.listApplications();

		return result;
	}
}
