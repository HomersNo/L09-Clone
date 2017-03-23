
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Commentable;

public interface CommentableRepository extends JpaRepository<Commentable, Integer> {

}
