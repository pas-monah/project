package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	private UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/info")
	public String getInfo(@RequestParam(value = "param") String param, Model model) {
		model.addAttribute("parametr", param);
		return "info";
	}
	
	@GetMapping("/create")
	public String createUser() {
		return "user_form";
	}

	@PostMapping("/add")
	public String addUser(@ModelAttribute(name = "user") User user) {
		userRepository.save(user);
		return "redirect:/users/all";
	}

	@GetMapping("/all")
	public String users(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "users";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name = "id") int id) {
		userRepository.deleteById(id);
		return "redirect:/users/all";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam(name = "word") String word, Model model) {
		List<User> users = userRepository.findByNameContainingOrGender(word, word);
		model.addAttribute("users", users);
		return "users";
	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable(name = "id") int id, Model model) {
		model.addAttribute("user", userRepository.findById(id).get());
		return "user_update";
	}
	
	@PostMapping("/update")
	public String userUpdate(@ModelAttribute(name="user") User user) {
		userRepository.save(user);
		return "redirect:/users/all";
	}
}
