
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Constructor

	public MessageService() {
		super();
	}


	//Managed Repository

	@Autowired
	private MessageRepository		messageRepository;

	//Auxiliary Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private FolderService			folderService;


	//CRUD

	public Message create() {
		final Message result = new Message();
		Actor sender;
		sender = this.actorService.findByPrincipal();
		final Folder senderFolder = this.folderService.findSystemFolder(sender, "Outbox");
		result.setFolder(senderFolder);
		result.setMoment(new Date());
		result.setSender(sender);
		return result;
	}

	public Message findOne(final int messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);

		return result;
	}
	
	public Collection<Message> findAll() {
		return messageRepository.findAll();
	}

	public Collection<Message> findAllByFolder(final int folderId) {
		Collection<Message> result;
		this.folderService.checkPrincipal(folderId);
		result = this.messageRepository.findAllByFolderId(folderId);
		return result;
	}
	
	public Collection<Message> findAllByFolderWithNoCheck(final int folderId) {
		Collection<Message> result;
		result = this.messageRepository.findAllByFolderId(folderId);
		return result;
	}

	//	public Message save(Message message){
	//		Message result;
	//		folderService.checkPrincipal(message.getFolder());
	//		result = messageRepository.save(message);
	//		return result;
	//	}

	public void delete(final Message message) {

		this.checkPrincipal(message);

		this.messageRepository.delete(message);

	}

	//Business methods

	public Message send(final Message message) {

		Actor recipient;
		Folder recipientFolder;
		Folder senderFolder;
		Actor sender;

		sender = this.actorService.findByPrincipal();
		recipient = message.getRecipient();

		recipientFolder = this.folderService.findSystemFolder(recipient, "Inbox");
		senderFolder = this.folderService.findSystemFolder(sender, "Outbox");

		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setFolder(senderFolder);

		this.messageRepository.save(message);

		message.setFolder(recipientFolder);

		this.messageRepository.save(message);

		return message;
	}
	public Message move(final Message message, final Folder folder) {
		Message result;
		this.checkPrincipal(message);
		this.folderService.checkPrincipal(folder);
		message.setFolder(folder);
		result = this.messageRepository.save(message);
		return result;
	}
	
	public void flush() {
		messageRepository.flush();
		
	}

	// Principal Checkers

	public void checkPrincipalSender(final Message message) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(message.getSender()));
	}

	public void checkPrincipalRecipient(final Message message) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(message.getRecipient()));
	}

	public void checkPrincipal(final Message message) {
		final Actor actor = this.actorService.findByPrincipal();

		Assert.isTrue(actor.equals(message.getSender()) || actor.equals(message.getRecipient()));
	}

	public Double findMinSentMessagesPerActor() {
		Assert.notNull(this.adminService.findByPrincipal());
		Double result = 0.0;
		result = this.messageRepository.minSentMessagesPerActor().iterator().next();
		return result;
	}

	public Double findAvgSentMessagesPerActor() {
		Assert.notNull(this.adminService.findByPrincipal());
		Double result = 0.0;
		result = this.messageRepository.avgSentMessagesPerActor();
		return result;
	}

	public Double findMaxSentMessagesPerActor() {
		Assert.notNull(this.adminService.findByPrincipal());
		Double result = 0.0;
		result = this.messageRepository.maxSentMessagesPerActor().iterator().next();
		return result;
	}

	public Double findMinReceivedMessagesPerActor() {
		Assert.notNull(this.adminService.findByPrincipal());
		Double result = 0.0;
		result = this.messageRepository.minReceivedMessagesPerActor().iterator().next();
		return result;
	}

	public Double findAvgReceivedMessagesPerActor() {
		Assert.notNull(this.adminService.findByPrincipal());
		Double result = 0.0;
		result = this.messageRepository.avgReceivedMessagesPerActor();
		return result;
	}

	public Double findMaxReceivedMessagesPerActor() {
		Assert.notNull(this.adminService.findByPrincipal());
		Double result = 0.0;
		result = this.messageRepository.maxReceivedMessagesPerActor().iterator().next();
		return result;
	}

	public Collection<Actor> findActorWithMoreSentMessages() {
		Assert.notNull(this.adminService.findByPrincipal());
		Collection<Actor> result = null;
		result = this.messageRepository.actorWithMoreSentMessages();
		return result;
	}

	public Collection<Actor> findActorWithMoreReceivedMessages() {
		Assert.notNull(this.adminService.findByPrincipal());
		Collection<Actor> result = null;
		result = this.messageRepository.actorWithMoreReceivedMessages();
		return result;
	}
}
