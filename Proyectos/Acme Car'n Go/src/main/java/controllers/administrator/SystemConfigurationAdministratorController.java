
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SystemConfigurationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Customer;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/systemConfiguration/administrator")
public class SystemConfigurationAdministratorController extends AbstractController {

	@Autowired
	SystemConfigurationService	systemConfigurationService;

	@Autowired
	ActorService				actorService;


	// Constructors -----------------------------------------------------------

	public SystemConfigurationAdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		SystemConfiguration systemConfiguration;

		systemConfiguration = this.systemConfigurationService.findMain();
		result = this.createEditModelAndView(systemConfiguration);

		return result;
	}

	@RequestMapping("/setBanner")
	public ModelAndView setBanner(@RequestParam(required = true) final String banner) {
		ModelAndView result;
		final SystemConfiguration systemConfiguration = this.systemConfigurationService.findMain();
		try {
			systemConfiguration.setBanner(banner);
			this.systemConfigurationService.save(systemConfiguration);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(systemConfiguration, "systemConfiguration.negative");
		}
		return result;
	}

	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("systemConfiguration/dashboard");

		final Double ratioOffersVsRequests = this.systemConfigurationService.findRatioOffersVsRequests();
		final Double avgApplicationsPerPost = this.systemConfigurationService.findAvgApplicationsPerPost();
		final Double avgPostsPerCustomer = this.systemConfigurationService.findAvgPostsPerCustomer();
		final Double avgCommentsPerCommentable = this.systemConfigurationService.findAvgCommentsPerCommentable();
		final Double avgCommentsPerActor = this.systemConfigurationService.findAvgCommentsPerActor();
		final Double minSentMessagesPerActor = this.systemConfigurationService.findMinSentMessagesPerActor();
		final Double avgSentMessagesPerActor = this.systemConfigurationService.findAvgSentMessagesPerActor();
		final Double maxSentMessagesPerActor = this.systemConfigurationService.findMaxSentMessagesPerActor();
		final Double minReceivedMessagesPerActor = this.systemConfigurationService.findMinReceivedMessagesPerActor();
		final Double avgReceivedMessagesPerActor = this.systemConfigurationService.findAvgReceivedMessagesPerActor();
		final Double maxReceivedMessagesPerActor = this.systemConfigurationService.findMaxReceivedMessagesPerActor();
		final Collection<Customer> customersWithMoreAccepted = this.systemConfigurationService.findCustomersWithMoreAccepted();
		final Collection<Customer> customersWithMoreDenied = this.systemConfigurationService.findCustomersWithMoreDenied();
		final Collection<Actor> actorsWith10PercentAvg = this.systemConfigurationService.findActorsWith10PercentAvg();
		final Collection<Actor> actorWithMoreReceivedMessages = this.systemConfigurationService.findActorWithMoreReceivedMessages();
		final Collection<Actor> actorWithMoreSentMessages = this.systemConfigurationService.findActorWithMoreSentMessages();

		result.addObject("ratioOffersVsRequests", ratioOffersVsRequests);
		result.addObject("avgApplicationsPerPost", avgApplicationsPerPost);
		result.addObject("avgPostsPerCustomer", avgPostsPerCustomer);
		result.addObject("avgCommentsPerCommentable", avgCommentsPerCommentable);
		result.addObject("avgCommentsPerActor", avgCommentsPerActor);
		result.addObject("minSentMessagesPerActor", minSentMessagesPerActor);
		result.addObject("avgSentMessagesPerActor", avgSentMessagesPerActor);
		result.addObject("maxSentMessagesPerActor", maxSentMessagesPerActor);
		result.addObject("minReceivedMessagesPerActor", minReceivedMessagesPerActor);
		result.addObject("avgReceivedMessagesPerActor", avgReceivedMessagesPerActor);
		result.addObject("maxReceivedMessagesPerActor", maxReceivedMessagesPerActor);
		result.addObject("customersWithMoreAccepted", customersWithMoreAccepted);
		result.addObject("customersWithMoreDenied", customersWithMoreDenied);
		result.addObject("actorsWith10PercentAvg", actorsWith10PercentAvg);
		result.addObject("actorWithMoreReceivedMessages", actorWithMoreReceivedMessages);
		result.addObject("actorWithMoreSentMessages", actorWithMoreSentMessages);

		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);

		result.addObject("requestURI", "systemConfiguration/dashboard.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final SystemConfiguration systemConfiguration) {
		ModelAndView result;

		result = this.createEditModelAndView(systemConfiguration, null);
		result.addObject("requestURI", "systemConfiguration/administrator/edit.do");
		result.addObject("cancelURI", "welcome/index.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final SystemConfiguration systemConfiguration, final String message) {
		ModelAndView result;

		result = new ModelAndView("systemConfiguration/edit");
		result.addObject("systemConfiguration", systemConfiguration);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", "systemConfiguration/administrator/edit.do");
		result.addObject("cancelURI", "welcome/index.do");

		return result;
	}

}
