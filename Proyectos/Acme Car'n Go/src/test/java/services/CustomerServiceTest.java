
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// The SUT -------------------------------------------------------------
	@Autowired
	private CustomerService	customerService;


	// Tests ---------------------------------------------------------------
	@Test
	public void testCreateCustomer() {

	}

	// Teoria pagina 107 y 108
	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				"customer1",
			}
		};
	}

}
