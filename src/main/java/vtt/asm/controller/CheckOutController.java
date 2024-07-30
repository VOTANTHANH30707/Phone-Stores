package vtt.asm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vtt.asm.dao.CategoryRepository;
import vtt.asm.entity.Category;

@Controller
public class CheckOutController {
	@Autowired
	private CategoryRepository cateRepo;

	private void addCategoriesToModel(Model model) {
		List<Category> listCate = cateRepo.findAll();
		model.addAttribute("listCate", listCate);
	}

	@GetMapping("/thanh-toan")
	public String checkout(Model model) {
		addCategoriesToModel(model);
		return "user/product/checkout";
	}
}
