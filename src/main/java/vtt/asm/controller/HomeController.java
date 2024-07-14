package vtt.asm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/user/index")
	public String index(Model model) {
		return "/user/index";
	}

	@GetMapping("/chi-tiet-san-pham")
	public String detail(Model model) {
		return "/user/product/detail_product";
	}

	@GetMapping("/gio-hang")
	public String cart(Model model) {
		return "/user/product/shopping_cart";
	}

	@GetMapping("/thanh-toan")
	public String checkout(Model model) {
		return "/user/product/checkout";
	}
}
