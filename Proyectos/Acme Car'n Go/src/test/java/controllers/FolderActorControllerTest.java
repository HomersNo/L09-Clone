
package controllers;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import utilities.AbstractTest;
import controllers.actor.FolderActorController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
public class FolderActorControllerTest extends AbstractTest {

	private MockMvc					mockMvc;

	@Autowired
	private FolderActorController	folderActorController;


	@Override
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.folderActorController).build();
	}

	@Test
	public void findFolderByPrincipal() throws Exception {
		this.authenticate("customer1");

		this.mockMvc.perform(MockMvcRequestBuilders.get("/folder/actor/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("folder/list")).andExpect(MockMvcResultMatchers.forwardedUrl("folder/list"))
			.andExpect(MockMvcResultMatchers.model().attribute("folders", Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers.model().attribute("folders", Matchers.hasItem(Matchers.allOf(Matchers.hasProperty("id", Matchers.is(93)), Matchers.hasProperty("name", Matchers.is("Inbox"))))))
			.andExpect(MockMvcResultMatchers.model().attribute("folders", Matchers.hasItem(Matchers.allOf(Matchers.hasProperty("id", Matchers.is(94)), Matchers.hasProperty("name", Matchers.is("Outbox"))))));

		this.unauthenticate();
	}

}
