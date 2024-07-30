package vtt.asm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vtt.asm.dao.CategoryRepository;
import vtt.asm.dao.ProductRepository;
import vtt.asm.dao.ThuongHieuRepository;
import vtt.asm.entity.Category;
import vtt.asm.entity.Product;
import vtt.asm.entity.ThuongHieu;
import vtt.asm.service.ProductService;

@Controller
public class AdminController {

	@GetMapping("/admin/product")
	public String admin_product(Model model) {
	    return "admin/productAdmin";
	}


}
