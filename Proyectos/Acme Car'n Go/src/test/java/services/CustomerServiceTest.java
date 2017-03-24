
package services;

import java.util.ArrayList;
import java.util.Collection;

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
			{
				"correcto", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", null
			}, {
				"", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", IllegalArgumentException.class
			}, {
				"correcto", "", "correcto", "correcto", "correcto@bien.com", "1234", IllegalArgumentException.class
			}, {
				"correcto", "correcto", "", "correcto", "correcto@bien.com", "1234", IllegalArgumentException.class
			}, {
				"correcto", "correcto", "correcto", "", "correcto@bien.com", "1234", IllegalArgumentException.class
			}, {
				"correcto", "correcto", "correcto", "correcto", "", "1234", IllegalArgumentException.class
			}, {
				"correcto", "correcto", "correcto", "correcto", "correcto@bien.com", "", IllegalArgumentException.class
			}, {
				"cor", "correcto", "correcto", "correcto", "correcto@bien.com", "1234", IllegalArgumentException.class
			}, {
				"correcto", "cor", "correcto", "correcto", "correcto@bien.com", "1234", IllegalArgumentException.class
			}, {
				"correcto", "correcto", "correcto", "correcto", "correcto.com", "1234", IllegalArgumentException.class
			}, {
				"correcto", "correcto", "correcto", "correcto", "correcto@bien.com", "AA", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreation((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
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
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//	protected void templateDelete(String username, int customerId, Class<?> expected) {
	//		Class<?> caught;
	//		caught = null;
	//		try {
	//			
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//		checkExceptions(expected, caught);
	//	}

}
