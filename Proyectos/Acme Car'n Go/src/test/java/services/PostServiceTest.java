
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Place;
import domain.Post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PostServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------

	@Autowired
	private PostService		postService;

	@Autowired
	private PlaceService	placeService;


	// Tests ---------------------------------------------------------------
	@Test
	public void driverPost() {
		final Object testingData[][] = {
			{	// Creación correcta de un Place.
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 45.014, "Grove street", 21.587, 46.789, null
			}, {
				// Creación de un Place: no logueado
				null, "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 45.014, "Grove street", 21.587, 46.789, IllegalArgumentException.class
			}, {
				// Creación de un Place: sin titulo
				"customer1", null, "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 45.014, "Grove street", 21.587, 46.789, ConstraintViolationException.class
			}, {
				// Creación de un Place: sin momento
				"customer1", "Post", "Este es un post", null, "OFFER", "Elm street", 20.035, 45.014, "Grove street", 21.587, 46.789, NullPointerException.class
			}, {
				// Creación de un Place: momento pasado
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), -1), "OFFER", "Elm street", 20.035, 45.014, "Grove street", 21.587, 46.789, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives1() {
		final Object testingData[][] = {
			{
				// Creación de un Place: sin descripcion
				"customer1", "Post", null, DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 45.014, "Grove street", 21.587, 46.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives2() {
		final Object testingData[][] = {
			{
				// Creación de un Place: sin tipo
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), null, "Elm street", 20.035, 45.014, "Grove street", 21.587, 46.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives3() {
		final Object testingData[][] = {
			{
				// Creación de un Place: tipo erroneo
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "efddsf", "Elm street", 20.035, 45.014, "Grove street", 21.587, 46.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives4() {
		final Object testingData[][] = {
			{
				// Creación de un Place: sin direccion origen
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", null, 20.035, 45.014, "Grove street", 21.587, 46.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives5() {
		final Object testingData[][] = {
			{
				// Creación de un Place: sin latitud origen
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", null, 45.014, "Grove street", 21.587, 46.789, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives6() {
		final Object testingData[][] = {
			{
				// Creación de un Place: latitud origen fuera limites arriba
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 86.035, 45.014, "Grove street", 21.587, 46.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives7() {
		final Object testingData[][] = {
			{
				// Creación de un Place: latitud origen fuera limites abajo
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", -86.035, 45.014, "Grove street", 21.587, 46.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives8() {
		final Object testingData[][] = {
			{
				// Creación de un Place: sin longitud origen
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, null, "Grove street", 21.587, 46.789, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives9() {
		final Object testingData[][] = {
			{
				// Creación de un Place: latitud origen fuera limites arriba
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 181.014, "Grove street", 21.587, 46.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives10() {
		final Object testingData[][] = {
			{
				// Creación de un Place: longitud origen fuera limites abajo
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, -181.014, "Grove street", 21.587, 46.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives11() {
		final Object testingData[][] = {
			{
				// Creación de un Place: sin direccion destino
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 45.014, null, 21.587, 46.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives12() {
		final Object testingData[][] = {
			{
				// Creación de un Place: sin latitud destino
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 45.014, "Grove street", null, 46.789, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives13() {
		final Object testingData[][] = {
			{
				// Creación de un Place: latitud destino fuera limites arriba
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 45.014, "Grove street", 86.587, 46.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives14() {
		final Object testingData[][] = {
			{
				// Creación de un Place: latitud destino fuera limites abajo
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 45.014, "Grove street", -86.587, 46.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives15() {
		final Object testingData[][] = {
			{
				// Creación de un Place: sin longitud destino
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 45.014, "Grove street", 21.587, null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives16() {
		final Object testingData[][] = {
			{
				// Creación de un Place: longitud destino fuera limites arriba
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 45.014, "Grove street", 21.587, 181.789, ConstraintViolationException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverNegatives17() {
		final Object testingData[][] = {
			{
				// Creación de un Place: longitud destino fuera limites abajo
				"customer1", "Post", "Este es un post", DateUtils.addHours(new Date(), 1), "OFFER", "Elm street", 20.035, 45.014, "Grove street", 21.587, -181.789, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templatePost((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Double) testingData[i][6], (Double) testingData[i][7],
				(String) testingData[i][8], (Double) testingData[i][9], (Double) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	@Test
	public void driverPostFilter() {
		final Object testingData[][] = {
			{	// Busqueda correcta de Post por palabra
				"customer1", "Necesito", null
			}, {
				// Busqueda de Post por palabra: no logueado
				null, "Necesito", IllegalArgumentException.class
			}, {
				// Busqueda de Post por palabra: sin filter
				"customer1", null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templatePostFilter((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverPostDelete() {
		final Object testingData[][] = {
			{	// Delete Post correcto.
				"customer1", 121, null
			}, {
				// Delete Post: no logueado
				null, 121, IllegalArgumentException.class
			}, {
				// Delete Post: no propietario
				"customer2", 121, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templatePostDelete((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverPostBan() {
		final Object testingData[][] = {
			{	// Ban Post correcto.
				"admin", 121, null
			}, {
				// Ban Post: no logueado
				null, 121, IllegalArgumentException.class
			}, {
				// Ban Post: no admin
				"customer1", 121, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templatePostBan((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	// Templates ----------------------------------------------------------
	protected void templatePost(final String username, final String title, final String description, final Date moment, final String type, final String originAddress, final Double originLatitude, final Double originLongitude,
		final String destinationAddress, final Double destinationLatitude, final Double destinationLongitude, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Post post = this.postService.create();
			final Place origin = this.placeService.create();
			final Place destination = this.placeService.create();

			post.setTitle(title);
			post.setDescription(description);
			post.setMoment(moment);
			post.setType(type);
			origin.setAddress(originAddress);
			origin.setLatitude(originLatitude);
			origin.setLongitude(originLongitude);
			post.setOrigin(origin);
			destination.setAddress(destinationAddress);
			destination.setLatitude(destinationLatitude);
			destination.setLongitude(destinationLongitude);
			post.setDestination(destination);

			this.postService.save(post);
			this.postService.flush();
			this.placeService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templatePostFilter(final String username, final String filter, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Post> posts = this.postService.findAllFiltered(filter);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templatePostDelete(final String username, final int postId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Post post = this.postService.findOne(postId);
			this.postService.delete(post);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templatePostBan(final String username, final int postId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Post post = this.postService.findOne(postId);
			this.postService.ban(post);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
