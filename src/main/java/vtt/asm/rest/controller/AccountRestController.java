package vtt.asm.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import vtt.asm.entity.User;
import vtt.asm.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/account")
public class AccountRestController {
	@Autowired
	UserService userService;

	@PostMapping("/login")
	public Map<String, String> login(@RequestBody User loginUser, HttpSession session) {
		User user = userService.getUserByEmail(loginUser.getEmail());
		Map<String, String> response = new HashMap();
		if (user != null && user.getPassword().equals(loginUser.getPassword())) {
			session.setAttribute("userSession", user);
			response.put("status", "success");
			response.put("redirectUrl", "/user/index");

		} else {
			response.put("status", "fail");
			response.put("message", "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.");
		}
		return response;
	}

	@GetMapping("/logout")
	public Map<String, String> logout(HttpSession session) {
		session.invalidate();
		Map<String, String> response = new HashMap<>();
		response.put("Status", "Success"); // Đảm bảo chữ cái hoa/nhỏ đúng
		response.put("message", "Đăng xuất thành công");
		response.put("redirectUrl", "/user/index");
		return response;
	}

	@PostMapping("/sign-up")
	public Map<String, String> signUp(@RequestBody User user, HttpSession session) {
		Map<String, String> response = new HashMap<>();
		if (userService.getUserByEmail(user.getEmail()) != null) {
			response.put("status", "fail");
			response.put("message", "Email đã tồn tại");
		} else {
			user.setEnable(true);
			userService.saveUser(user);
			session.setAttribute("userSession", user);
			response.put("redirectUrl", "/user/index");
			response.put("status", "success");
			response.put("message", "Đăng ký thành công");
		}
		return response;
	}

}
