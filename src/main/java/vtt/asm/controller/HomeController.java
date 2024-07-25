package vtt.asm.controller;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vtt.asm.dao.CategoryRepository;
import vtt.asm.dao.ProductImageColorRepository;
import vtt.asm.dao.ProductRepository;
import vtt.asm.entity.Category;
import vtt.asm.entity.Product;
import vtt.asm.entity.ProductImageColor;
import vtt.asm.service.ProductDetailService;
import vtt.asm.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryRepository cateRepo;
	@Autowired
	private ProductDetailService productDetailService;

	private void addCategoriesToModel(Model model) {
		List<Category> listCate = cateRepo.findAll();
		model.addAttribute("listCate", listCate);
	}

	@GetMapping("/user/index")
	public String index(Model model) {
		addCategoriesToModel(model);
		List<Product> products = productService.getProductsWithFormattedPrices();
		model.addAttribute("products", products);
		return "user/index";
	}

	@GetMapping("/chi-tiet-san-pham/{productId}")
	public String detail1(Model model,
	                      @PathVariable("productId") Integer productId,
	                      @RequestParam(value = "dungLuong", required = false) String dungLuong,
	                      @RequestParam(value = "color", required = false) String color) {
	    addCategoriesToModel(model);
	    Product product = productDetailService.getProductById(productId);
	    if (product != null) {
	        model.addAttribute("product", product);

	        List<String> listDungLuong = productDetailService.getDungLuongByProductId(productId);
	        model.addAttribute("listDungLuong", listDungLuong);

	        // Đảm bảo giá không bị null và xử lý các giá trị null
	        String lowestPriceDungLuong = listDungLuong.stream()
	                .min(Comparator.comparing(d -> {
	                    Double price = productDetailService.getPriceByColorDungLuongProductId(productId, d, color);
	                    return price != null ? price : Double.MAX_VALUE;
	                }))
	                .orElse(null);

	        List<String> listColor = (dungLuong != null)
	                ? productDetailService.getColorByDungLuong(productId, dungLuong)
	                : productDetailService.getColorByProductId(productId);

	        model.addAttribute("listColor", listColor);

	        String lowestPriceColor = (lowestPriceDungLuong != null) ? listColor.stream()
	                .min(Comparator.comparing(c -> {
	                    Double price = productDetailService.getPriceByColorDungLuongProductId(productId, lowestPriceDungLuong, c);
	                    return price != null ? price : Double.MAX_VALUE;
	                }))
	                .orElse(null) : null;

	        model.addAttribute("selectedDungLuong", dungLuong != null ? dungLuong : lowestPriceDungLuong);
	        model.addAttribute("selectedColor", color != null ? color : lowestPriceColor);

	        // Xác định giá dựa trên dungLuong và color đã cung cấp hoặc mặc định
	        Double price = (dungLuong != null && color != null)
	                ? productDetailService.getPriceByColorDungLuongProductId(productId, dungLuong, color)
	                : productDetailService.getPriceByColorDungLuongProductId(productId, lowestPriceDungLuong, lowestPriceColor);

	        String formattedPrice = (price != null)
	                ? NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(price)
	                : "";

	        model.addAttribute("price", formattedPrice);
	    } else {
	        model.addAttribute("errorMessage", "Sản phẩm không tìm thấy");
	        return "error";
	    }
	    return "user/product/detail_product";
	}


	@GetMapping("/chi-tiet-san-pham/{productId}/{dungLuong}")
	public String detai_dungluong(Model model, @PathVariable("productId") Integer productId,
			@PathVariable("dungLuong") String dungLuong) {
		addCategoriesToModel(model);
		Product product = productDetailService.getProductById(productId);
		if (product != null) {
			model.addAttribute("product", product);
			model.addAttribute("listDungLuong", productDetailService.getDungLuongByProductId(productId));
			model.addAttribute("listColor", productDetailService.getColorByDungLuong(productId, dungLuong));
			model.addAttribute("listImg", productDetailService.getImagesByProductIdDungLuong(productId, dungLuong));
			model.addAttribute("selectedDungLuong", dungLuong);
		} else {
			model.addAttribute("errorMessage", "Product not found");
			return "error";
		}
		return "user/product/detail_product";
	}

	@GetMapping("/chi-tiet-san-pham/{productId}/{dungLuong}/{color}")
	public String detail(Model model, @PathVariable("productId") Integer productId,
			@PathVariable("dungLuong") String dungLuong, @PathVariable("color") String color) {
		addCategoriesToModel(model);
		Product product = productDetailService.getProductById(productId);
		if (product != null) {
			model.addAttribute("product", product);
			model.addAttribute("listDungLuong", productDetailService.getDungLuongByProductId(productId));
			model.addAttribute("listColor", productDetailService.getColorByDungLuong(productId, dungLuong));
			model.addAttribute("listImg", productDetailService.getImagesByProductId(productId));

			// Lấy giá từ dịch vụ
			Double price = productDetailService.getPriceByColorDungLuongProductId(productId, dungLuong, color);

			// Kiểm tra giá và định dạng
			String formattedPrice = price != null
					? NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(price)
					: "Giá không có sẵn";

			model.addAttribute("price", formattedPrice); // Đảm bảo giá đã được định dạng
			model.addAttribute("selectedDungLuong", dungLuong);
			model.addAttribute("selectedColor", color);
		} else {
			model.addAttribute("errorMessage", "Product not found");
			return "error";
		}
		return "user/product/detail_product";
	}

	@GetMapping("/loai-san-pham/{cateId}")
	public String category(Model model, @PathVariable("cateId") int cateId) {
		addCategoriesToModel(model);
		List<Product> products = productService.findProductByCategory(cateId);
		model.addAttribute("products", products);
		return "/user/index";
	}

	@GetMapping("/gio-hang")
	public String cart(Model model) {
		addCategoriesToModel(model);
		return "user/product/shopping_cart";
	}

	@GetMapping("/thanh-toan")
	public String checkout(Model model) {
		addCategoriesToModel(model);
		return "user/product/checkout";
	}
}
