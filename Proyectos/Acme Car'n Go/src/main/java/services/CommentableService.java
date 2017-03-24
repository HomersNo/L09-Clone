
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CommentableRepository;
import domain.Commentable;

@Service
public class CommentableService {

	//managed repository-------------------
	@Autowired
	private CommentableRepository	commentableRepository;


	//Basic CRUD methods-------------------

	public Commentable findOne(final int commentableId) {

		Commentable retrieved;
		retrieved = this.commentableRepository.findOne(commentableId);
		return retrieved;
	}

}
