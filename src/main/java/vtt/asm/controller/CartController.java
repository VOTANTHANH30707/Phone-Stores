package vtt.asm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import vtt.asm.dao.CategoryRepository;
import vtt.asm.entity.Cart;
import vtt.asm.entity.Category;
import vtt.asm.entity.User;
import vtt.asm.service.CartService;

@Controller
public class CartController {
	@Autowired
	private CategoryRepository cateRepo;
	@Autowired
	HttpSession session;
	@Autowired
	CartService cartService;

	private void addCategoriesToModel(Model model) {
		List<Category> listCate = cateRepo.findAll();
		model.addAttribute("listCate", listCate);
	}

	@GetMapping("/gio-hang")
	public String cart(Model model, HttpSession session) {
	    addCategoriesToModel(model);
	    User user = (User) session.getAttribute("userSession");
	    if (user == null) {
	        // Xử lý nếu người dùng chưa đăng nhập
	        return "redirect:/user/login"; // Chuyển hướng đến trang đăng nhập
	    }
	    List<Cart> carts = cartService.getCartById(user.getUserId());
	    
	    model.addAttribute("carts", carts); // Thêm giỏ hàng vào model
	    return "user/product/shopping_cart";
	}

}
