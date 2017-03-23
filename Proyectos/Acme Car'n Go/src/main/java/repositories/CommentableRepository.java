
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Commentable;

public interface CommentableRepository extends JpaRepository<Commentable, Integer> {

	@Query("select c from Commentable c where c.id=?1")
	Commentable findOne(int commentableId);

}
