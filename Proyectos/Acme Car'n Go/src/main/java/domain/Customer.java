/*
 * Administrator.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	// Constructors -----------------------------------------------------------

	public Customer() {
		super();
	}


	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Collection<Application>	applications;
	private Collection<Post>		posts;


	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Application> getApplications() {
		return this.applications;
	}
	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Post> getPosts() {
		return this.posts;
	}
	public void setPosts(final Collection<Post> posts) {
		this.posts = posts;
	}
}
