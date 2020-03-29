package alexlab.MessageStoringFullStackApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import alexlab.MessageStoringFullStackApp.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

}
