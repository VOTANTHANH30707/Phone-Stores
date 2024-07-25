package vtt.asm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import vtt.asm.dao.UserRepository;
import vtt.asm.entity.User;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;

	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);

	}

	public void saveUser(User user) {
        // user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));;
		userRepo.save(user);
    }

}
