
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;
import domain.Commentable;

@Service
public class CommentService {

	//managed repository-------------------
	@Autowired
	private CommentRepository		commentRepository;

	//supporting services-------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private CustomerService			customerService;


	//Basic CRUD methods-------------------

	public Comment create(final Commentable commentable) {

		Comment created;
		created = new Comment();
		created.setBanned(false);
		final Date moment = new Date(System.currentTimeMillis() - 100);
		final Actor actor = this.actorService.findByPrincipal();
		created.setActor(actor);
		created.setMoment(moment);
		created.setCommentable(commentable);
		created.setBanned(false);
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

	public Collection<Comment> findNotBannedCommentsCustomer(final int commentableId) {

		final Actor principal;
		final Set<Comment> result = new HashSet<Comment>();

		principal = this.actorService.findByPrincipal();
		result.addAll(this.findNotBannedComments(commentableId));
		result.addAll(this.commentRepository.findCommentsByCustomer(principal.getId(), commentableId));

		return result;

	}
	public Collection<Comment> findNotBannedComments(final int commentableId) {

		final Collection<Comment> result;

		result = this.commentRepository.findNotBannedComments(commentableId);

		return result;
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

	public Comment banComment(final Comment comment) {

		this.adminService.checkAdministrator();
		Comment result;

		comment.setBanned(true);
		result = this.save(comment);

		return result;
	}

	public void flush() {
		this.commentRepository.flush();

	}
}
