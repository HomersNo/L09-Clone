
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
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	//Constructor
	public CustomerService() {
		super();
	}


	//Managed Repository
	@Autowired
	private CustomerRepository		customerRepository;

	//Auxiliary Services

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private Validator				validator;


	//CRUD

	public Customer create() {

		final Customer result = new Customer();

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result.setUserAccount(userAccount);
		return result;
	}

	public Customer findOneToEdit(final int id) {
		Customer result;
		result = this.customerRepository.findOne(id);
		this.checkPrincipal(result);
		return result;
	}

	public Customer findOne(final int id) {
		Customer result;
		result = this.customerRepository.findOne(id);
		return result;
	}

	public Customer save(final Customer customer) {
		Customer result;

		if (customer.getId() <= 0) {
			this.adminService.checkAdministrator();
			String password = customer.getUserAccount().getPassword();
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			password = encoder.encodePassword(password, null);
			customer.getUserAccount().setPassword(password);
		} else
			this.checkPrincipal(customer);
		result = this.customerRepository.save(customer);
		return result;
	}

	public void delete(final Customer customer) {
		this.adminService.checkAdministrator();
		this.customerRepository.delete(customer);
	}

	public Customer findByUserAccount(final UserAccount userAccount) {
		Customer result;
		result = this.customerRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount);
		return result;
	}

	//Business Methods

	public void checkPrincipal(final Customer customer) {
		Customer prin;
		prin = this.findByPrincipal();
		Assert.isTrue(customer.getId() == prin.getId());
	}

	public Customer reconstruct(final Customer customer, final BindingResult binding) {
		Customer result;

		if (customer.getId() == 0)
			result = customer;
		else {
			result = this.customerRepository.findOne(customer.getId());

			result.setEmail(customer.getEmail());
			result.setName(customer.getName());
			result.setPhoneNumber(customer.getPhoneNumber());
			result.setSurname(customer.getSurname());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public void checkCustomer() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Boolean checker = false;
		userAccount = LoginService.getPrincipal();
		for (final Authority a : userAccount.getAuthorities())
			if (a.getAuthority().equals(Authority.CUSTOMER)) {
				checker = true;
				break;
			}
		Assert.isTrue(checker);
	}

	public Collection<Customer> findAll() {
		Collection<Customer> result;
		result = this.customerRepository.findAll();
		return result;
	}

}
