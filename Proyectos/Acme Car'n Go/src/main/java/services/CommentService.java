
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;
import domain.Commentable;

public class CommentService {

	//managed repository-------------------
	@Autowired
	private CommentRepository		commentRepository;

	//supporting services-------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	adminService;


	//Basic CRUD methods-------------------

	public Comment create(final Commentable commentable) {

		Comment created;
		created = new Comment();
		final Date moment = new Date(System.currentTimeMillis() - 100);
		final Actor actor = this.actorService.findByPrincipal();
		created.setActor(actor);
		created.setMoment(moment);
		created.setCommentable(commentable);
		return created;
	}

	public Comment findOne(final int commentId) {

		Comment retrieved;
		retrieved = this.commentRepository.findOne(commentId);
		return retrieved;
	}

	public Collection<Comment> findAllByCommentableId(final int commentableId) {

		Collection<Comment> result;
		result = this.commentRepository.findAllByCommentableId(commentableId);
		return result;
	}

	public Collection<Comment> findAll() {

		return this.commentRepository.findAll();
	}

	public Comment save(final Comment comment) {

		Comment saved;
		final Date moment = new Date(System.currentTimeMillis() - 100);
		comment.setMoment(moment);
		saved = this.commentRepository.save(comment);
		return saved;

	}

	public void delete(final Comment comment) {

		this.commentRepository.delete(comment);

	}

	//Auxiliary methods

	//Our other bussiness methods

	public Double findAvgCommentsPerCommentable() {
		Assert.notNull(this.adminService.findByPrincipal());
		Double result = 0.0;
		result = this.commentRepository.avgCommentsPerCommentable();
		return result;
	}

	public Double findAvgCommentsPerActor() {
		Assert.notNull(this.adminService.findByPrincipal());
		Double result = 0.0;
		result = this.commentRepository.avgCommentsPerActor();
		return result;
	}

	public Collection<Actor> findActorsWith10PercentAvg() {
		Assert.notNull(this.adminService.findByPrincipal());
		Collection<Actor> result = null;
		result = this.commentRepository.actorsWith10PercentAvg();
		return result;
	}

}
