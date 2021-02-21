package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Folder;
import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repository.FolderRepository;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/folders")
public class FolderController {
	private UserRepository userRepository;
	private FolderRepository folderRepository;
	
	@Autowired
	public FolderController(UserRepository userRepository, FolderRepository folderRepository) {
		this.userRepository = userRepository;
		this.folderRepository = folderRepository;
	}
	
	@GetMapping("/all")
	public String showFolders(Model model, Principal principal) {
		if(principal != null) {
			User user = userRepository.findByUsername(principal.getName());
			model.addAttribute("folders", user.getFolders());
		} else {
			model.addAttribute("folders", new ArrayList<>());			
		}
		return "folders";
	}
	
	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") int id, Model model, Principal principal) {
		if(principal != null) {
			User user = userRepository.findByUsername(principal.getName());
			List<Note> notes = user.getNotes();
			notes.removeIf(n->((n.getFolder())!=null)?n.getFolder().getId()!=id:true);
			model.addAttribute("notes", notes);
		} else {
			model.addAttribute("notes", new ArrayList<>());			
		}
		return "notes";
	}
	
	@GetMapping("/create")
	public String create(Model model, Principal principal) {
		return "folder_form";
	}
	
	@PostMapping("/add")
	public String add(@ModelAttribute("folder") Folder folder, Principal principal) {
		if(principal != null) {
			User user = userRepository.findByUsername(principal.getName());
			folder.setUser(user);
			folderRepository.save(folder);
		}
		return "redirect:/folders/all";
	}
}
