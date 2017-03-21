
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Actor;
import domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where c.commentable.id=?1")
	Collection<Comment> findAllByCommentableId(int commentableId);

	@Query("select count(c)*1.0/(select count(co)*1.0 from Commentable co) from Comment c")
	Double avgCommentsPerCommentable();

	@Query("select count(c)*1.0/(select count(a)*1.0 from Actor a) from Comment c")
	Double avgCommentsPerActor();

	@Query("select c.actor from Comment c group by c.actor having count(c)*1.0 >= (select (count(c)*1.0/(select count(a)*1.0 from Actor a))*0.9 from Comment c)	and count(c)*1.0 <=	(select (count(c)*1.0/(select count(a)*1.0 from Actor a))*1.1 from Comment c)")
	Collection<Actor> actorsWith10PercentAvg();
}
