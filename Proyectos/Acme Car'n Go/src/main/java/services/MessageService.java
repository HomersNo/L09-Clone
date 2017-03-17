
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
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
	private MessageRepository	messageRepository;

	//Auxiliary Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;


	//CRUD

	public Message create() {
		Message result = new Message();
		Actor sender;
		sender = actorService.findByPrincipal();
		result.setMoment(new Date());
		result.setSender(sender);
		return result;
	}

	public Message findOne(int messageId) {
		Message result;

		result = messageRepository.findOne(messageId);

		return result;
	}

	public Collection<Message> findAllByFolder(int folderId) {
		Collection<Message> result;
		folderService.checkPrincipal(folderId);
		result = messageRepository.findAllByFolderId(folderId);
		return result;
	}

	//	public Message save(Message message){
	//		Message result;
	//		folderService.checkPrincipal(message.getFolder());
	//		result = messageRepository.save(message);
	//		return result;
	//	}

	public void delete(Message message) {

		checkPrincipal(message);

		messageRepository.delete(message);
	}

	//Business methods

	public Message send(Message message) {

		Actor recipient;
		Folder recipientFolder;
		Folder senderFolder;
		Actor sender;

		sender = actorService.findByPrincipal();
		recipient = message.getRecipient();

		recipientFolder = folderService.findSystemFolder(recipient, "Inbox");
		senderFolder = folderService.findSystemFolder(sender, "Outbox");

		message.setMoment(new Date(System.currentTimeMillis() - 1));
		message.setFolder(senderFolder);

		messageRepository.save(message);

		message.setFolder(recipientFolder);

		messageRepository.save(message);
		messageRepository.save(message);

		return message;
	}
	public Message move(Message message, Folder folder) {
		Message result;
		checkPrincipal(message);
		folderService.checkPrincipal(folder);
		message.setFolder(folder);
		result = messageRepository.save(message);
		return result;
	}

	// Principal Checkers

	public void checkPrincipalSender(Message message) {
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(message.getSender()));
	}

	public void checkPrincipalRecipient(Message message) {
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(message.getRecipient()));
	}

	public void checkPrincipal(Message message) {
		UserAccount actor = LoginService.getPrincipal();

		Assert.isTrue(actor.equals(message.getSender()) || actor.equals(message.getRecipient()));
	}
}
