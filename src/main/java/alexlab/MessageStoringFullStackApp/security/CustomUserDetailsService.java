/*
 * Service class to implement UserDetailsService interface from Spring Security and its method
 * loadUserByUsername(String username) to lookup UserDetails for a given username.
 */

package alexlab.MessageStoringFullStackApp.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import alexlab.MessageStoringFullStackApp.entities.User;
import alexlab.MessageStoringFullStackApp.repositories.UserRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService{
	
	
	//Inject instance of UserRepository
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Find the user-object into the repository and assign it to the variable 
		User user = userRepository.findByEmail(username)
				// Throw exception of no users found
				.orElseThrow(()-> new UsernameNotFoundException("Email " + username + " not found"));
		
		// return Spring Data Access Object with the details of User-object, obtained via repository
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthorities(user));


	}

	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		
		// Build a list of strings out of rolls associated with the user-object
		String[]userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		
		// Build a list of authorities
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		
		return authorities;
	}
	
	

}
