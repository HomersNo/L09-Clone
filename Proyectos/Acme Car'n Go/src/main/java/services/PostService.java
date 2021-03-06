
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PostRepository;
import domain.Customer;
import domain.Post;

@Service
@Transactional
public class PostService {

	public PostService() {
		super();
	}


	@Autowired
	private PostRepository			postRepository;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods ----------------------------------------------------

	public Post create() {
		Post result;
		result = new Post();

		result.setBanned(false);
		result.setCustomer(this.customerService.findByPrincipal());

		return result;
	}
	public Collection<Post> findAll() {
		Collection<Post> result;

		result = this.postRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Post findOne(final int postId) {
		Assert.isTrue(postId != 0);

		Post result;

		result = this.postRepository.findOne(postId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Post> findAllRequests() {
		Collection<Post> result;

		result = this.postRepository.findByType("REQUEST");

		return result;
	}

	public Collection<Post> findAllOffers() {
		Collection<Post> result;

		result = this.postRepository.findByType("OFFER");

		return result;
	}

	public Collection<Post> findAllRequestsNotBanned() {
		Collection<Post> result;

		result = this.postRepository.findByTypeNotBanned("REQUEST");

		return result;
	}

	public Collection<Post> findAllOffersNotBanned() {
		Collection<Post> result;

		result = this.postRepository.findByTypeNotBanned("OFFER");

		return result;
	}

	public Collection<Post> findAllFiltered(final String filter) {
		Collection<Post> posts;
		Assert.notNull(filter);
		Assert.notNull(this.customerService.findByPrincipal());

		posts = this.postRepository.findAllFiltered(filter);

		return posts;
	}

	public Post save(final Post post) {
		Assert.notNull(post);
		Assert.isTrue(post.getMoment().after(new Date()));
		Assert.isTrue(((post.getOrigin().getLatitude() == null && post.getOrigin().getLongitude() == null) || (post.getOrigin().getLatitude() != null && post.getOrigin().getLongitude() != null))
			&& ((post.getDestination().getLatitude() == null && post.getDestination().getLongitude() == null) || (post.getDestination().getLatitude() != null && post.getDestination().getLongitude() != null)));
		this.checkPrincipal(post);

		Post result;

		result = this.postRepository.save(post);

		return result;
	}

	public void delete(final Post post) {
		Assert.notNull(post);
		Assert.isTrue(post.getId() != 0);
		Assert.isTrue(this.postRepository.exists(post.getId()));
		this.checkPrincipal(post);
		this.postRepository.save(post);
	}

	// Other business methods -------------------------------------------------

	public Collection<Post> findAllByCustomer(final Customer customer) {
		Collection<Post> result;
		result = this.postRepository.findAllByCustomerId(customer.getId());
		return result;
	}

	public Collection<Post> findAllRequestByCustomer(final Customer customer) {
		Collection<Post> result;
		result = this.postRepository.findAllRequestByCustomerId(customer.getId());
		return result;
	}

	public Collection<Post> findAllOfferByCustomer(final Customer customer) {
		Collection<Post> result;
		result = this.postRepository.findAllOfferByCustomerId(customer.getId());
		return result;
	}

	public void ban(final Post post) {
		post.setBanned(true);
		Assert.isTrue(this.adminService.findByPrincipal() != null);

		this.postRepository.save(post);

	}

	public Post reconstruct(final Post post, final BindingResult binding) {
		Post result;

		if (post.getId() == 0) {
			result = this.create();

			result.setDestination(post.getDestination());
			result.setMoment(post.getMoment());
			result.setDescription(post.getDescription());
			result.setTitle(post.getTitle());
			result.setOrigin(post.getOrigin());
			result.setType(post.getType());
		} else {
			result = this.postRepository.findOne(post.getId());

			result.setDestination(post.getDestination());
			result.setMoment(post.getMoment());
			result.setDescription(post.getDescription());
			result.setTitle(post.getTitle());
			result.setOrigin(post.getOrigin());
			result.setType(post.getType());

			this.validator.validate(result, binding);
		}

		return result;
	}

	public void checkPrincipal(final Post post) {
		Assert.isTrue(post.getCustomer().equals(this.customerService.findByPrincipal()));
	}

	public Double findRatioOffersVsRequests() {
		Assert.notNull(this.adminService.findByPrincipal());
		Double result = 0.0;
		result = this.postRepository.ratioOffersRequests();
		return result;
	}

	public Double findAvgPostsPerCustomer() {
		Assert.notNull(this.adminService.findByPrincipal());
		Double result = 0.0;
		result = this.postRepository.avgPostsPerCustomer();
		return result;
	}

	public void flush() {
		this.postRepository.flush();
	}

}
