
package controllers;

import org.hamcrest.Matchers;
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

import services.FolderService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@WebAppConfiguration
public class FolderActorControllerTest extends AbstractTest {

	private MockMvc			mockMvc;

	@Autowired
	private FolderService	folderServiceMock;


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
}
