/*
 * Custom interface, extends JpaRepository.
 * Defines custom method to find the user by email - to be implemented inside Service-class
 */
package alexlab.MessageStoringFullStackApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import alexlab.MessageStoringFullStackApp.entities.User;

public interface UserRepository extends  JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);

}
