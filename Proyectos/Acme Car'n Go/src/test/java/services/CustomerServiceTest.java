
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private CustomerService	customerService;


	// Teoria pagina 107 y 108
	// Tests ---------------------------------------------------------------
	@Test
	public void driverCreation() {
		final Object testingData[][] = {
			{		// Creación correcta de un Customer.
				"correcto", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", null
			}, {	// Creación errónea de un Customer: username vacío.
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: password vacío.
				"correcto", "", "correcto", "correcto", "correcto@bien.com", "1234", ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: name vacío.
				"correcto", "correcto", "", "correcto", "correcto@bien.com", "1234", ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: surname vacío.
				"correcto", "correcto", "correcto", "", "correcto@bien.com", "1234", ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: email vacío.
				"correcto", "correcto", "correcto", "correcto", "", "1234", ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: phoneNumber vacío.
				"correcto", "correcto", "correcto", "correcto", "correcto@bien.com", "", ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: username con pocos carácteres.
				"cor", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: password con pocos carácteres.
				"correcto", "cor", "correcto", "correcto", "correcto@bien.com", "1234", ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: email incorrecto.
				"correcto", "correcto", "correcto", "correcto", "correcto.com", "1234", ConstraintViolationException.class
			}, {	// Creación errónea de un Customer: phoneNumber incorrecto.
				"correcto", "correcto", "correcto", "correcto", "correcto@bien.com", "AA", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	@Test
	public void driverDisplaying() {
		final Object testingData[][] = {
			{		// Display correcto de un Customer ya creado y logueado como tal. 
				"customer1", 83, null
			}, {	// Display correcto de un Customer distinto al que está logueado.
				"customer1", 84, null
			}, {	// Display erróneo de un Customer que no existe con uno logueado.
				"customer1", 100, IllegalArgumentException.class
			}, {	// Display correcto de un Customer, sin estar logueado en el sistema.
				null, 83, null
			}, {	// Display erróneo de un Customer que no existe sin estar logueado.
				null, 100, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDisplaying((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Templates ----------------------------------------------------------
	protected void templateCreation(final String username, final String password, final String name, final String surname, final String email, final String phone, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final Customer c = this.customerService.create();
			final UserAccount user = new UserAccount();
			final Collection<Authority> as = new ArrayList<Authority>();
			final Authority a = new Authority();
			a.setAuthority("CUSTOMER");
			as.add(a);
			user.setAuthorities(as);
			c.setUserAccount(user);
			user.setUsername(username);
			user.setPassword(password);
			c.setUserAccount(user);
			c.setName(name);
			c.setSurname(surname);
			c.setEmail(email);
			c.setPhoneNumber(phone);
			this.customerService.save(c);
			this.customerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void templateDisplaying(final String username, final int customerId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Customer c = this.customerService.findOne(customerId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
