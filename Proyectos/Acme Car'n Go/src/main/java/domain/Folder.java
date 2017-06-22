
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "actor_id")
})
public class Folder extends DomainEntity {

	// Constructor

	public Folder() {
		super();
	}


	//Attributes

	private String	name;


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}


	//Relationships
	private Actor	actor;


	@Valid
	@NotNull
	@ManyToOne()
	//	@NotFound(action = NotFoundAction.IGNORE)
	public Actor getActor() {
		return this.actor;
	}
	public void setActor(final Actor actor) {
		this.actor = actor;
	}
}
