package vtt.asm.controller;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vtt.asm.dao.CategoryRepository;
import vtt.asm.dao.ProductImageColorRepository;
import vtt.asm.dao.ProductRepository;
import vtt.asm.dto.ProductDTO;
import vtt.asm.entity.Category;
import vtt.asm.entity.Product;
import vtt.asm.entity.ProductImageColor;

@Controller
public class HomeController {
	@Autowired
	ProductRepository productRepo;
	@Autowired
	ProductImageColorRepository productImgColorRepo;
	@Autowired
	CategoryRepository cateRepo;

	@GetMapping("/user/index")
	public String index(Model model) {
		List<Category> listCate = cateRepo.findAll();
		model.addAttribute("listCate", listCate);
//		listCate.forEach(category -> System.out.println(category.toString()));
		List<ProductImageColor> listProductImgColor = productImgColorRepo.findAll();
		model.addAttribute("listProductImgColor", listProductImgColor);

		List<Product> products = productRepo.findProductsWithImageColors();
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")); // Định dạng VND
        products.forEach(product -> {
            if (!product.getProductImageColors().isEmpty()) {
            	// Thực hiện định dạng giá nếu có sản phẩm với màu và kích thước
            	// giá thất nhất
                Double lowestPrice = product.getProductImageColors().stream()
                    .min(Comparator.comparing(ProductImageColor::getPrice))
                    .map(ProductImageColor::getPrice)
                    .orElse(0.0); // Giá mặc định nếu không có giá
                product.setLowestPriceFormatted(currencyFormat.format(lowestPrice));
            } else {
            	   // Xử lý trường hợp không có giá sản phẩm
                product.setLowestPriceFormatted(currencyFormat.format(0.0)); // Giá mặc định nếu không có giá
            }
        });
		model.addAttribute("products", products);

		return "user/index";
	}

	@GetMapping("/chi-tiet-san-pham")
	public String detail(Model model) {
		List<Category> listCate = cateRepo.findAll();
		model.addAttribute("listCate", listCate);
//		listCate.forEach(category -> System.out.println(category.toString()));
		List<ProductImageColor> listProductImgColor = productImgColorRepo.findAll();
		model.addAttribute("listProductImgColor", listProductImgColor);
		return "user/product/detail_product";
	}

	@GetMapping("/gio-hang")
	public String cart(Model model) {
		return "user/product/shopping_cart";
	}

	@GetMapping("/thanh-toan")
	public String checkout(Model model) {
		return "user/product/checkout";
	}
}
