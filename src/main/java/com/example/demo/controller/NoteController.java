package com.example.demo.controller;

import java.security.Principal;
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

import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/notes")
public class NoteController {
	private NoteRepository noteRepository;
	private UserRepository userRepository;
	
	@Autowired
	public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
		this.noteRepository = noteRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/all")
	public String notes(Model model, Principal principal) {
		if(principal != null) {
			User user = userRepository.findByUsername(principal.getName());
			model.addAttribute("notes", user.getNotes());
		} else {
			model.addAttribute("notes", noteRepository.findAll());			
		}

		return "notes";
	}

	@GetMapping("/create")
	public String create() {
		return "note_form";
	}
	
	@PostMapping("/add")
	public String add(@ModelAttribute("note") Note note, Principal principal) {
		if(principal != null) {
			User user = userRepository.findByUsername(principal.getName());
			user.addNote(note);
			noteRepository.save(note);
			userRepository.save(user);
		}
		
		return "redirect:/notes/all";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id) {
		noteRepository.deleteById(id);
		return "redirect:/notes/all";
	}

	@GetMapping("/search")
	public String search(@RequestParam(name = "word") String word, Model model) {
		List<Note> notes = noteRepository.findByLabelContainingOrMessageContaining(word, word);
		model.addAttribute("notes", notes);
		return "notes";
	}
	
	@GetMapping("/update/{id}")
	public String update(@PathVariable(name = "id") int id, Model model) {
		model.addAttribute("note", noteRepository.findById(id).get());
		return "note_update";
	}
	
	@PostMapping("/update")
	public String userUpdate(@ModelAttribute(name="note") Note note) {
		noteRepository.save(note);
		return "redirect:/notes/all";
	}
}
