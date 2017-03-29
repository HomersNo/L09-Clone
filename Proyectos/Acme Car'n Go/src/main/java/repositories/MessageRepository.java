
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m where m.folder.id = ?1")
	Collection<Message> findAllByFolderId(int id);

	@Query("select count(m)*1.0 from Message m group by m.sender order by count(m) ASC")
	Collection<Double> minSentMessagesPerActor();

	@Query("select count(m)*1.0 from Message m group by m.sender order by count(m) DESC")
	Collection<Double> maxSentMessagesPerActor();

	@Query("select count(m)*1.0/(select count(m.sender)*1.0 from Message m) from Message m")
	Double avgSentMessagesPerActor();

	@Query("select count(m)*1.0 from Message m group by m.recipient order by count(m) ASC")
	Collection<Double> minReceivedMessagesPerActor();

	@Query("select count(m)*1.0 from Message m group by m.recipient order by count(m) DESC")
	Collection<Double> maxReceivedMessagesPerActor();

	@Query("select count(m)*1.0/(select count(m.recipient)*1.0 from Message m) from Message m")
	Double avgReceivedMessagesPerActor();

	@Query("select m.sender from Message m group by m.sender order by count(m)")
	Collection<Actor> actorWithMoreSentMessages();

	@Query("select m.recipient from Message m group by m.recipient order by count(m)")
	Collection<Actor> actorWithMoreReceivedMessages();
}
