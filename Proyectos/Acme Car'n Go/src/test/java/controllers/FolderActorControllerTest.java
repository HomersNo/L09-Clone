
package controllers;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import services.FolderService;
import utilities.AbstractTest;
import controllers.actor.FolderActorController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@WebAppConfiguration
public class FolderActorControllerTest extends AbstractTest {

	private MockMvc			mockMvc;

	@Autowired
	private FolderService	folderServiceMock;


	@Override
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new FolderActorController()).setValidator(this.validator()).setViewResolvers(this.viewResolver()).build();
	}

	@Test
	public void findFolderByPrincipal() throws Exception {
		this.authenticate("customer1");

		this.mockMvc.perform(MockMvcRequestBuilders.get("/folder/actor/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("folder/list"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/views/folder/list.jsp")).andExpect(MockMvcResultMatchers.model().attribute("folders", Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers.model().attribute("folders", Matchers.hasItem(Matchers.allOf(Matchers.hasProperty("id", Matchers.is(89)), Matchers.hasProperty("name", Matchers.is("Inbox"))))))
			.andExpect(MockMvcResultMatchers.model().attribute("folders", Matchers.hasItem(Matchers.allOf(Matchers.hasProperty("id", Matchers.is(90)), Matchers.hasProperty("name", Matchers.is("Outbox"))))));
		Mockito.verify(this.folderServiceMock, Mockito.times(1)).findAllByPrincipal();
		Mockito.verifyNoMoreInteractions(this.folderServiceMock);

		this.unauthenticate();
	}

	private LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	private ViewResolver viewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}
}
