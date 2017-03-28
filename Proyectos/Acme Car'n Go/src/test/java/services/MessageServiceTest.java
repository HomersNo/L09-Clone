package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Customer;
import domain.Folder;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private CustomerService customerService;

	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	//An actor who is authenticated must be able to:
	//	o Exchange messages with other actors.
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Message.
				"customer1", "correcto", "correcto", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", new Date(System.currentTimeMillis() - 100), null
			}, {	// Creación correcta de un Message.
				"admin1", "incorrecto", "incorrecto", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", new Date(System.currentTimeMillis() - 100), null
			}, {	// Creación errónea de un Message: title vacío.
				"customer1", "", "incorrecto", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", new Date(System.currentTimeMillis() - 100), ConstraintViolationException.class
			}, {	// Creación errónea de un Message: text vacío.
				"customer1", "incorrecto", "", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", new Date(System.currentTimeMillis() - 100), ConstraintViolationException.class
			}, {	// Creación errónea de un Message: moment vacío.
				"customer1", "incorrecto", "incorrecto", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", null, ConstraintViolationException.class
			}, {	// Creación errónea de un Message: attachment que no es URL
				"customer1", "incorrecto", "incorrecto", "blae", new Date(System.currentTimeMillis() - 100), ConstraintViolationException.class
			}, {	// Creación errónea de un Message: moment futuro.
				"customer1", "incorrecto", "incorrecto", "http://www.bouncepen.com/wp-content/themes/twentyfifteen/uploads/user-photo/dummy-image.png", new Date(System.currentTimeMillis() + 10000000), ConstraintViolationException.class
			}
			
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Date) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	//- An actor who is authenticated must be able to:
	//	o List the messages that he or she’s got and reply to them.
	//	o List the messages that he or she’s got and forward them
	@Test
	public void driverFindAllByFolderId() {
		final Object testingData[][] = {
			{		
				"admin1", "Inbox", null
			}, {		
				"admin1", "Outbox", null
			}, {		
				"admin2", "Inbox", null
			}, {		
				"admin2", "Outbox", null
			}, {		
				"customer1", "Inbox", null
			}, {		
				"customer1", "Outbox", null
			}, {		
				"customer2", "Inbox", null
			}, {		
				"customer2", "Outbox", null
			}, {		
				"customer3", "Inbox", null
			}, {		
				"customer3", "Outbox", null
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindAllByFolderId((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String title, final String text, final String attachment, final Date moment, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			authenticate(username);
			Customer cus = customerService.findOne(87);
			final Message m = this.messageService.create();
			m.setMoment(moment);
			m.setAttachment(attachment);
			m.setText(text);
			m.setTitle(title);
			m.setRecipient(cus);
			
			
			this.messageService.send(m);
			
			Actor sender = this.actorService.findByPrincipal();
			Actor recipient = m.getRecipient();
			
			Assert.isTrue(m.getSender().equals(sender) && m.getRecipient().equals(recipient));
			
			Folder recipientFolder = this.folderService.findSystemFolder(recipient, "Inbox");
			Folder senderFolder = this.folderService.findSystemFolder(sender, "Outbox");
			
			Collection<Message> recipientMessages = messageService.findAllByFolderWithNoCheck(recipientFolder.getId());
			Collection<Message> senderMessages = messageService.findAllByFolderWithNoCheck(senderFolder.getId());
			
			for(Message r : recipientMessages){
				for(Message s : senderMessages){
					if(r.getTitle() == r.getTitle() && r.getMoment().equals(s.getMoment())
							&& r.getAttachment() == s.getAttachment() && r.getRecipient().equals(s.getRecipient())
							&& r.getText() == s.getText()){
						Assert.isTrue(r.getTitle() == r.getTitle() && r.getMoment().equals(s.getMoment())
								&& r.getAttachment() == s.getAttachment() && r.getRecipient().equals(s.getRecipient())
								&& r.getText() == s.getText());
					}
				}
			}
			messageService.delete(m);
			
			this.messageService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateFindAllByFolderId(final String username, final String folderName, final Class<?> expected){
		Class<?> caught;
		caught = null;
		
		try {
		authenticate(username);
		Actor actor = this.actorService.findByPrincipal();
		Folder folder = folderService.findSystemFolder(actor, folderName);
		
		for(Message m : messageService.findAllByFolder(folder.getId())){
			Assert.isTrue(m.getSender().equals(actor) || m.getRecipient().equals(actor));
		}
		
		unauthenticate();
		
		this.messageService.flush();
		
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}