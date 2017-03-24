
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
import domain.Customer;
import domain.Post;

@Service
@Transactional
public class ApplicationService {

	public ApplicationService() {
		super();
	}


	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	adminService;


	public Application create(final Post post) {
		Application result;

		result = new Application();
		result.setCustomer(this.customerService.findByPrincipal());
		result.setPost(post);

		return result;

	}

	public Application findOne(final int appId) {
		Application result;

		result = this.applicationRepository.findOne(appId);

		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();

		return result;
	}

	public Collection<Application> findAllReceived(final int customerId) {
		Collection<Application> result;

		result = this.applicationRepository.findAllReceived(customerId);

		return result;
	}

	public Collection<Application> findAllByCustomerId(final Integer customerId) {
		Collection<Application> result;

		result = this.applicationRepository.findAllByCustomer(customerId);

		return result;
	}

	public Collection<Application> findAllByPostId(final Integer postId) {
		Collection<Application> result;

		result = this.applicationRepository.findAllByPost(postId);

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getCustomer().equals(this.customerService.findByPrincipal()));
		Application result;

		result = this.applicationRepository.save(application);

		return result;
	}

	public void delete(final Application application) {
		Assert.notNull(application);
	}

	public void accept(final Application application) {
		Assert.isTrue(application.getPost().getCustomer().equals(this.customerService.findByPrincipal()));
		application.setStatus("ACCEPTED");
		this.applicationRepository.save(application);
	}

	public void deny(final Application application) {
		Assert.isTrue(application.getPost().getCustomer().equals(this.customerService.findByPrincipal()));
		application.setStatus("DENIED");
		this.applicationRepository.save(application);
	}

	public Application apply(final Post post) {
		Application result;

		result = this.create(post);
		result.setStatus("PENDING");

		result = this.applicationRepository.save(result);

		return result;
	}

	public Double findAvgApplicationsPerPost() {
		Assert.notNull(this.adminService.findByPrincipal());
		Double result = 0.0;
		result = this.applicationRepository.avgApplicationsPerPost();
		return result;
	}

	public Collection<Customer> findCustomersWithMoreAccepted() {
		Assert.notNull(this.adminService.findByPrincipal());
		Collection<Customer> result = null;
		result = this.applicationRepository.customersWithMoreAccepted();
		return result;
	}

	public Collection<Customer> findCustomersWithMoreDenied() {
		Assert.notNull(this.adminService.findByPrincipal());
		Collection<Customer> result = null;
		result = this.applicationRepository.customersWithMoreDenied();
		return result;
	}
}
