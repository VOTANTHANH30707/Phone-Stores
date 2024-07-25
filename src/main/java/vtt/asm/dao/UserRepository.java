package vtt.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import vtt.asm.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail(String email);
}
