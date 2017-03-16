
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

}
