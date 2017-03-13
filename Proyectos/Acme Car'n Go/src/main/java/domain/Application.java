
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	public Application() {
		super();
	}


	private String	status;


	@NotBlank
	@Pattern(regexp = "^(ACCEPTED|PENDING|DENIED)$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}


	//Relationships

	private Post		post;
	private Customer	customer;


	@Valid
	@ManyToOne()
	public Post getPost() {
		return this.post;
	}

	public void setPost(final Post post) {
		this.post = post;
	}

	@Valid
	@ManyToOne()
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

}
