
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;

@Service
@Transactional
public class FolderService {

	//Constructor
	public FolderService() {
		super();
	}


	//Managed Repository

	@Autowired
	private FolderRepository	folderRepository;

	//Auxiliary Services

	@Autowired
	private ActorService		actorService;


	//CRUD

	public Folder create(Actor actor) {
		Folder result = new Folder();
		result.setActor(actor);
		return result;
	}

	public Folder save(Folder folder) {
		Folder result;
		result = folderRepository.save(folder);
		return result;
	}

	public Collection<Folder> findAllByPrincipal() {
		Actor actor;
		Collection<Folder> result;
		actor = actorService.findByPrincipal();
		result = folderRepository.findAllByActor(actor.getId());
		return result;
	}

	//Business Methods

	public Collection<Folder> initFolders(Actor actor) {
		Collection<Folder> result = new ArrayList<Folder>();
		Collection<Folder> aux = new ArrayList<Folder>();
		Folder inbox;
		Folder outbox;
		inbox = create(actor);
		inbox.setName("inbox");
		outbox = create(actor);
		outbox.setName("outbox");
		aux.add(outbox);
		aux.add(inbox);
		result = folderRepository.save(aux);

		return result;

	}

	public void checkPrincipal(Folder folder) {
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(actor.equals(folder.getActor()), "Dear User, you can't edit a folder that doesn't belong to you");
	}

	public void checkPrincipal(int folderId) {
		Folder folder;
		folder = folderRepository.findOne(folderId);
		checkPrincipal(folder);
	}

}
