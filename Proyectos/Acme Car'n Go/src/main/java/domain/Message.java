package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import security.UserAccount;


@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {
	
	// Constructor
	
	public Message(){
		super();
	}
	
	// Attributes
	
	private String title;
	private String text;
	private Date moment;
	private String attachment;
	
	@NotBlank
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Past
	@NotNull
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	// Relationships
	
	private Folder folder;
	private Actor sender;
	private Actor recipient;

	@Valid
	@NotNull
	@ManyToOne(optional =false)
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional =false)
//	@NotFound(action = NotFoundAction.IGNORE)
	public Actor getSender() {
		return sender;
	}
	public void setSender(Actor sender) {
		this.sender = sender;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional =false)
//	@NotFound(action = NotFoundAction.IGNORE)
	public Actor getRecipient() {
		return recipient;
	}
	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}
	

}