//package vtt.asm.rest.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.servlet.http.HttpSession;
//import vtt.asm.dto.CartDTO;
//import vtt.asm.entity.User;
//import vtt.asm.service.CartService;
//
//@RestController
//@RequestMapping("/api/cart")
//public class CartRestController {
//	@Autowired
//	CartService cartService;
//
//	@PostMapping("/add-to-cart")
//	public ResponseEntity<String> addToCart(@RequestBody CartDTO cartDto, HttpSession session) {
//	    User user = (User) session.getAttribute("userSession");
//	    if (user == null) {
//	        return ResponseEntity.status(401).body("User not logged in");
//	    } else {
//	        try {
//	            cartDto.setUserId(user.getUserId());
//	            cartService.addToCart(cartDto);
//	            return ResponseEntity.ok("Product added to cart");
//	        } catch (EntityNotFoundException e) {
//	            return ResponseEntity.status(404).body(e.getMessage());
//	        } catch (Exception e) {
//	            return ResponseEntity.status(500).body("An error occurred");
//	        }
//	    }
//	}
//
//
//	@GetMapping("/count")
//	public ResponseEntity<?> getCountItem(HttpSession session) {
//	    User user = (User) session.getAttribute("userSession");
//	    if (user == null) {
//	        return ResponseEntity.status(401).body("User not logged in"); // Trả về mã lỗi 401 nếu người dùng chưa đăng nhập
//	    }
//
//	    try {
//	        int itemCount = cartService.getCount(user.getUserId()); // Đảm bảo getCount nhận đúng userId
//	        return ResponseEntity.ok(itemCount);
//	    } catch (Exception e) {
//	        return ResponseEntity.status(500).body("An error occurred while fetching cart count");
//	    }
//	}
//
//}
