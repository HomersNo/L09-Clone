
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Comment;
import domain.Customer;
import domain.Folder;
import domain.Post;
import forms.Register;

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
	public Customer create() {
		Customer created;
		created = new Customer();
		created.setPosts(new ArrayList<Post>());
		created.setApplications(new ArrayList<Application>());
		created.setFolders(new ArrayList<Folder>());
		created.setComments(new ArrayList<Comment>());

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		created.setUserAccount(userAccount);

		return created;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = this.customerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Customer findOne(final int customerId) {
		Assert.isTrue(customerId != 0);

		Customer result;

		result = this.customerRepository.findOne(customerId);
		Assert.notNull(result);

		return result;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);

		Customer result;

		result = this.customerRepository.save(customer);

		return result;
	}

	public void delete(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);
		Assert.isTrue(this.customerRepository.exists(customer.getId()));

		this.customerRepository.delete(customer);
	}

	public Customer findByPrincipal() {
		final Customer customer = this.customerRepository.findOneByActor(LoginService.getPrincipal().getId());
		return customer;
	}

	public Customer findByUserAccountId(final int userAccountId) {

		final Customer user = this.customerRepository.findOneByActor(userAccountId);
		return user;

	}

	// Other business methods ---------------------------------------
	public Customer reconstruct(final Customer customer, final BindingResult binding) {
		Customer result;
		if (customer.getId() == 0)
			result = customer;
		else {
			result = this.customerRepository.findOne(customer.getId());

			result.setName(customer.getName());
			result.setSurname(customer.getSurname());
			result.setEmail(customer.getEmail());
			result.setPhoneNumber(customer.getPhoneNumber());

			this.validator.validate(result, binding);
		}
		return result;
	}

	public Customer reconstruct(final Register registerCustomer, final BindingResult binding) {
		Customer result;
		Assert.isTrue(registerCustomer.getAccept());
		result = this.create();

		result.setName(registerCustomer.getName());
		result.setSurname(registerCustomer.getSurname());
		result.setEmail(registerCustomer.getEmail());
		result.setPhoneNumber(registerCustomer.getPhoneNumber());

		result.getUserAccount().setUsername(registerCustomer.getUsername());
		result.getUserAccount().setPassword(registerCustomer.getPassword());

		return result;
	}

	public Customer register(final Customer customer) {
		Customer result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// Convertimos la pass del usuario a hash.
		final String pass = encoder.encodePassword(customer.getUserAccount().getPassword(), null);
		// Creamos una nueva cuenta y le pasamos los parametros.
		customer.getUserAccount().setPassword(pass);

		result = this.customerRepository.save(customer);

		return result;
	}

}
