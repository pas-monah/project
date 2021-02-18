package com.example.demo.controller;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.UsernameExistsException;

@Controller
public class HomeController {
	private UserRepository ur;
	private UserService userService;

	@Autowired
	public HomeController(UserRepository ur, UserService userService) {
		this.ur = ur;
		this.userService = userService;
	}	
	
	@GetMapping("/")
	public String home(Model model, Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			User user = ur.findByUsername(username);
			model.addAttribute("message", "Welcome, " + user.getName());
		} else {
			model.addAttribute("message", "Welcome to my application");
		}
		return "home";
	}



	@GetMapping("/login")
	public String signIn() {
		return "login";
	}
	
	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}
	
	@GetMapping("/signup")
	public String signUp() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String registerNewUser(@ModelAttribute("user") User user, HttpServletRequest request) {
		String password = user.getPassword();
		try {
			userService.registerNewUser(user);
			request.login(user.getUsername(), password);
		} catch(UsernameExistsException e) {
			return "redirect:/signup";
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
}
