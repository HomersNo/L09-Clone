
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;
import domain.Commentable;

public class CommentService {

	//managed repository-------------------
	@Autowired
	private CommentRepository	commentRepository;

	//supporting services-------------------
	@Autowired
	private ActorService		actorService;


	//Basic CRUD methods-------------------

	public Comment create(Commentable commentable) {

		Comment created;
		created = new Comment();
		Date moment = new Date(System.currentTimeMillis() - 100);
		Actor actor = actorService.findByPrincipal();
		created.setActor(actor);
		created.setMoment(moment);
		created.setCommentable(commentable);
		return created;
	}

	public Comment findOne(int commentId) {

		Comment retrieved;
		retrieved = commentRepository.findOne(commentId);
		return retrieved;
	}

	public Collection<Comment> findAllByCommentableId(int commentableId) {

		Collection<Comment> result;
		result = commentRepository.findAllByCommentableId(commentableId);
		return result;
	}

	public Collection<Comment> findAll() {

		return commentRepository.findAll();
	}

	public Comment save(Comment comment) {

		Comment saved;
		Date moment = new Date(System.currentTimeMillis() - 100);
		comment.setMoment(moment);
		saved = commentRepository.save(comment);
		return saved;

	}

	public void delete(Comment comment) {

		commentRepository.delete(comment);

	}

	//Auxiliary methods

	//Our other bussiness methods

}
