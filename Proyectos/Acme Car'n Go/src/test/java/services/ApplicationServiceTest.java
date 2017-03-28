
package services;


import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Application;
import domain.Post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private ApplicationService	applicationService;
	
	@Autowired
	private PostService	postService;


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	@Test
	public void driverApplication() {
		final Object testingData[][] = {
			{	// Creación correcta de una Application.
				"customer1", 121, null,
				// Creación incorrecta de una Application: sin loguear
				null, 121, NullPointerException.class,
				// Creación incorrecta de una Application: admin logueado
				"admin1", 121, NullPointerException.class,
				// Creación incorrecta de una Application: customer ya ha aplicado
				"customer2", 122, ConstraintViolationException.class,
				// Creación incorrecta de una Application: postID nulo
				"customer2", null, NullPointerException.class,
				// Listar correctamente Applications: postId erroneo
				"customer2", 83, NullPointerException.class,
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateApplication((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void driverAcceptation() {
		final Object testingData[][] = {
			{	// Aceptación correcta de una Application.
				"customer2", 137, null,
				// Aceptación incorrecta de una Application: no es creador del post
				"customer1", 137, NullPointerException.class,
				// Aceptación incorrecta de una Application: usuario nulo
				null, 137, NullPointerException.class,
				// Aceptación incorrecta de una Application: usuario administrador
				"admin1", 137, NullPointerException.class,
				// Aceptación incorrecta de una Application: applicationId nula
				"customer1", null, NullPointerException.class,
				// Aceptación incorrecta de una Application: application ya aceptada
				"customer4", 139, NullPointerException.class,
				// Listar correctamente Applications: applicationID erroneo
				"customer2", 83, NullPointerException.class,
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateAcceptation((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void driverDenegation() {
		final Object testingData[][] = {
			{	// Denegación correcta de una Application.
				"customer2", 137, null,
				// Denegación incorrecta de una Application: no es creador del post
				"customer1", 137, NullPointerException.class,
				// Denegación incorrecta de una Application: usuario nulo
				null, 137, NullPointerException.class,
				// Denegación incorrecta de una Application: usuario administrador
				"admin1", 137, NullPointerException.class,
				// Denegación incorrecta de una Application: applicationId nula
				"customer1", null, NullPointerException.class,
				// Denegación incorrecta de una Application: application ya denegada
				"customer3", 138, NullPointerException.class,
				// Denegación incorrecta de una Application: application ya denegada
				"customer3", 138, null,
				// Listar correctamente Applications: applicationID erroneo
				"customer2", 83, NullPointerException.class,
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDenegation((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void driverListing() {
		final Object testingData[][] = {
			{	// Listar correctamente Applications.
				"customer1", 83, null,
				// Listar correctamente Applications: no son suyas
				"customer2", 83, NullPointerException.class,
				// Listar correctamente Applications: no logueado
				null, 83, NullPointerException.class,
				// Listar correctamente Applications: sin id customer
				"customer2", null, NullPointerException.class,
				// Listar correctamente Applications: customerId erroneo
				"customer2", 137, NullPointerException.class,
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListing((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void driverListingOwn() {
		final Object testingData[][] = {
			{	// Listar correctamente Applications.
				"customer1", 83, null,
				// Listar correctamente Applications: no son suyas
				"customer2", 83, NullPointerException.class,
				// Listar correctamente Applications: no logueado
				null, 83, NullPointerException.class,
				// Listar correctamente Applications: sin id customer
				"customer2", null, NullPointerException.class,
				// Listar correctamente Applications: customerId erroneo
				"customer2", 137, NullPointerException.class,
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListingOwn((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void driverListingByPost() {
		final Object testingData[][] = {
			{	// Listar correctamente Applications.
				"customer1", 121, null,
				// Listar correctamente Applications: postId erroneo
				"customer2", 83, NullPointerException.class,
				// Listar correctamente Applications: no logueado
				null, 121, NullPointerException.class,
				// Listar correctamente Applications: sin postId
				"customer2", null, NullPointerException.class,
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListingByPost((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	// Templates ----------------------------------------------------------
	protected void templateApplication(final String username,final int postId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			authenticate(username);
			Post post = postService.findOne(postId);
			applicationService.apply(post);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateAcceptation(final String username,final int applicationId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			authenticate(username);
			Application application = applicationService.findOne(applicationId);
			applicationService.accept(application);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateDenegation(final String username,final int applicationId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			authenticate(username);
			Application application = applicationService.findOne(applicationId);
			applicationService.deny(application);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateListing(final String username,final int customerId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			authenticate(username);
			Collection<Application> applications = applicationService.findAllByCustomerId(customerId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateListingOwn(final String username,final int customerId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			authenticate(username);
			Collection<Application> applications = applicationService.findAllReceived(customerId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void templateListingByPost(final String username,final int postId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			authenticate(username);
			Collection<Application> applications = applicationService.findAllByPostId(postId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
