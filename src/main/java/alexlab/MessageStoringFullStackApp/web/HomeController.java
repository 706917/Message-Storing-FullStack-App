/*
 * Class to provide controllers for endpoints and define actions to perform with requests and responses as redirects appropriate views:
 */

package alexlab.MessageStoringFullStackApp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import alexlab.MessageStoringFullStackApp.entities.Message;
import alexlab.MessageStoringFullStackApp.repositories.MessageRepository;

@Controller
public class HomeController {
	
	@Autowired
	private MessageRepository messageRepository;
	
	
	@GetMapping("/home")
	public String home(Model model) {
		// Add the supplied attribute under the supplied name.
		model.addAttribute("msgs", messageRepository.findAll());
		return "userhome";	
	}
	
	
	@PostMapping("/messages")
	public String saveMessage(Message message) {
		
		messageRepository.save(message);
		return "redirect:/home";
		
	}
	

}
