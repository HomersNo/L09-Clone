
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query("select p from Post p where p.customer.id = ?1")
	Collection<Post> findAllByCustomerId(int id);

	@Query("select p from Post p where p.type = ?1 and p.banned = false")
	Collection<Post> findByType(String string);

	@Query("select p from Post p where p.type = ?1")
	Collection<Post> findByTypeNotBanned(String string);

	@Query("select p from Post p where p.banned = false and (p.title like %?1% or p.description like %?1% or p.destination.address like %?1% or p.origin.address like %?1%)")
	Collection<Post> findAllFiltered(String filter);

	@Query("select count(p)*1.0/(select count(p)*1.0 from Post p where p.type = 'OFFER') from Post p where p.type = 'REQUEST'")
	Double ratioOffersRequests();

	@Query("select count(p)*1.0/(select count(c)*1.0 from Customer c) from Post p")
	Double avgPostsPerCustomer();

	@Query("select p from Post p where p.customer.id = ?1 and p.type='REQUEST'")
	Collection<Post> findAllRequestByCustomerId(int id);

	@Query("select p from Post p where p.customer.id = ?1 and p.type='OFFER'")
	Collection<Post> findAllOfferByCustomerId(int id);

}
