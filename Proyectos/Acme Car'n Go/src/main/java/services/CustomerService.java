
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;

@Service
@Transactional
public class CustomerService {

	// Managed repository -------------------------------------------
	@Autowired
	private CustomerRepository	customerRepository;

	// Auxiliary Services -------------------------------------------
	@Autowired
	private Validator			validator;


	// Constructors -------------------------------------------------
	public CustomerService() {
		super();
	}

	// Basic CRUD methods -------------------------------------------

	// Other business methods ---------------------------------------

}
