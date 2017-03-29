package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	// Constructor
	
	public Folder (){
		super();
	}
	
	//Attributes
	
	private String name;
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//Relationships
	private Actor actor;


	@Valid
	@NotNull
	@ManyToOne()
//	@NotFound(action = NotFoundAction.IGNORE)
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}
}