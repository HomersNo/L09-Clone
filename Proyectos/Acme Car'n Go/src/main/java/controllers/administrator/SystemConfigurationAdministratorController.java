
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SystemConfigurationService;
import controllers.AbstractController;
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
		systemConfiguration.setBanner(banner);
		try {
			this.systemConfigurationService.save(systemConfiguration);
			result = new ModelAndView("redirect:edit.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(systemConfiguration, "systemConfiguration.negative");
		}
		return result;
	}

	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("systemConfiguration/dashboard");

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
