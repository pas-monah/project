package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	public List<User> findByNameContainingOrGender(String name, String gender);
	public User findByUsername(String username);
	public User findByEmail(String email);
}
