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

import domain.Comment;
import domain.Commentable;
import domain.Customer;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CustomerService customerService;

	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	//- An actor who is authenticated must be able to:
	//	o Post a comment on another actor, on an offer, or a request
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Comment.
				"customer1", "correcto", "correcto", 3, new Date(System.currentTimeMillis() - 100), false, null
			}, {	// Creación errónea de un Comment: title vacío.
				"customer1", "", "correcto", 3, new Date(System.currentTimeMillis() - 100), false, ConstraintViolationException.class
			}, {	// Creación errónea de un Comment: text vacío.
				"customer1", "correcto", "", 3, new Date(System.currentTimeMillis() - 100), true, ConstraintViolationException.class
			}, {	// Creación errónea de un Comment: stars vacío.
				"customer1", "correcto", "correcto", null, new Date(System.currentTimeMillis() - 100), true, ConstraintViolationException.class
			}, {	// Creación errónea de un Comment: moment vacío.
				"customer1", "correcto", "correcto", 3, null, true, ConstraintViolationException.class
			}, {	// Creación errónea de un Comment: stars de 6.
				"customer1", "correcto", "correo", 6, new Date(System.currentTimeMillis() - 100), true, ConstraintViolationException.class
			}, {	// Creación errónea de un Comment: moment futuro.
				"customer1", "correcto", "correo", 3, new Date(System.currentTimeMillis() + 10000000), true, ConstraintViolationException.class
			}
			
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Date) testingData[i][4], (Boolean) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	//An actor who is authenticated as an administrator must be able to:
	//	o Ban a comment that he or she finds inappropriate. Such comments must not be
	//	displayed to a general audience, only to the administrators and the actor who posted
	//	it.
	@Test
	public void driverBan() {
		final Object testingData[][] = {
			{		// alguien intenta banear un comentario
				"customer1", IllegalArgumentException.class
			}, {	//alguien intenta banear un comentario
				null, IllegalArgumentException.class
			}, {	//alguien intenta banear un comentario
				"admin", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateBan((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	//An actor who is authenticated as an administrator must be able to:
		//	o Ban a comment that he or she finds inappropriate. Such comments must not be
		//	displayed to a general audience, only to the administrators and the actor who posted
		//	it.
	@Test
	public void driverFindNotBannedCommentsCustomer() {
		final Object testingData[][] = {
			{		
				"customer1", null
			}, {		
				"customer2", null
			}, {		
				"customer3", null
			}, {		
				"customer4", null
			}, {		
				"customer5", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateFindNotBannedCommentsCustomer((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String title, final String text, final Integer stars, final Date moment, final Boolean banned, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			authenticate(username);
			Customer cus = customerService.findOne(84);
			final Commentable com = cus;
			final Comment c = this.commentService.create(com);
			c.setBanned(banned);
			c.setActor(cus);
			c.setMoment(moment);
			c.setStars(stars);
			c.setText(text);
			c.setTitle(title);
			
			this.commentService.save(c);
			this.commentService.flush();
			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateBan(final String username, final Class<?> expected){
		Class<?> caught;
		caught = null;
		
		try {
		authenticate(username);
		Comment comment = commentService.findOne(129);
		this.commentService.banComment(comment);
		unauthenticate();
		
		this.commentService.flush();
		
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateFindNotBannedCommentsCustomer(final String username, final Class<?> expected){
		
		authenticate(username);
		Customer cus = customerService.findOne(84);
		Commentable commentable = cus;
		Collection<Comment> comments = commentService.findNotBannedCommentsCustomer(commentable.getId());
		for(Comment c : comments){
			Assert.isTrue(c.getActor().equals(cus) || c.getBanned() == false);
		}
		
	}

}