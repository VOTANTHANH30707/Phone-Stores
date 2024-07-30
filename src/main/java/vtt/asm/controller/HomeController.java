package vtt.asm.controller;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
	public String index(Model model, @RequestParam(value = "dungLuong", required = false) String dungLuong,
			@RequestParam(value = "color", required = false) String color) {
		addCategoriesToModel(model);
		List<Product> products = productService.getProductsWithFormattedPrices();
		model.addAttribute("products", products);

		// Tạo danh sách để lưu các giá trị mặc định cho dung lượng và màu sắc
		Map<Integer, String> defaultDungLuongMap = new HashMap<>();
		Map<Integer, String> defaultColorMap = new HashMap<>();

		for (Product product : products) {
			List<String> dungLuongs = productDetailService.getDungLuongByProductId(product.getProductId());
			if (dungLuongs != null && !dungLuongs.isEmpty()) {
				String lowestDungLuong = dungLuongs.stream().min(Comparator.comparing(dl -> {
					Double price = productDetailService.getPriceByColorDungLuongProductId(product.getProductId(), dl,
							null);
					return price != null ? price : Double.MAX_VALUE;
				})).orElse(null);

				if (lowestDungLuong != null) {
					List<String> colors = productDetailService.getColorByDungLuong(product.getProductId(),
							lowestDungLuong);
					if (colors != null && !colors.isEmpty()) {
						String lowestColor = colors.stream().min(Comparator.comparing(c -> {
							Double price = productDetailService
									.getPriceByColorDungLuongProductId(product.getProductId(), lowestDungLuong, c);
							return price != null ? price : Double.MAX_VALUE;
						})).orElse(null);

						defaultDungLuongMap.put(product.getProductId(), lowestDungLuong);
						defaultColorMap.put(product.getProductId(), lowestColor);
					}
				}
			}
		}

		model.addAttribute("defaultDungLuongMap", defaultDungLuongMap);
		model.addAttribute("defaultColorMap", defaultColorMap);

		return "user/index";
	}

	@GetMapping("/chi-tiet-san-pham/{productId}")
	public String detail1(Model model, @PathVariable("productId") Integer productId,
			@RequestParam(value = "dungLuong", required = false) String dungLuong,
			@RequestParam(value = "color", required = false) String color) {
		addCategoriesToModel(model);
		Product product = productDetailService.getProductById(productId);
		List<ProductImageColor> pic = productDetailService.getProductById(productId).getProductImageColors(); // Adjusted
		model.addAttribute("pic", pic);
		if (product != null) {
			model.addAttribute("product", product);

			List<String> listDungLuong = productDetailService.getDungLuongByProductId(productId);
			model.addAttribute("listDungLuong", listDungLuong);

			String lowestPriceDungLuong = listDungLuong.stream().min(Comparator.comparing(d -> {
				Double price = productDetailService.getPriceByColorDungLuongProductId(productId, d, color);
				return price != null ? price : Double.MAX_VALUE;
			})).orElse(null);

			List<String> listColor = (dungLuong != null)
					? productDetailService.getColorByDungLuong(productId, dungLuong)
					: productDetailService.getColorByProductId(productId);

			model.addAttribute("listColor", listColor);

			String lowestPriceColor = (lowestPriceDungLuong != null)
					? listColor.stream().min(Comparator.comparing(c -> {
						Double price = productDetailService.getPriceByColorDungLuongProductId(productId,
								lowestPriceDungLuong, c);
						return price != null ? price : Double.MAX_VALUE;
					})).orElse(null)
					: null;

			model.addAttribute("listImg", productDetailService.getImagesByProductIdDungLuong(productId, dungLuong));

			model.addAttribute("selectedDungLuong", dungLuong != null ? dungLuong : lowestPriceDungLuong);
			model.addAttribute("selectedColor", color != null ? color : lowestPriceColor);

			Double price = (dungLuong != null && color != null)
					? productDetailService.getPriceByColorDungLuongProductId(productId, dungLuong, color)
					: productDetailService.getPriceByColorDungLuongProductId(productId, lowestPriceDungLuong,
							lowestPriceColor);

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

	@GetMapping("/loai-san-pham/{cateId}")
	public String category(Model model, @PathVariable("cateId") int cateId) {
		addCategoriesToModel(model);

		List<Product> products = productService.getProductsWithFormattedPricesCate(cateId);
		model.addAttribute("products", products);

		// Tạo danh sách để lưu các giá trị mặc định cho dung lượng và màu sắc
		Map<Integer, String> defaultDungLuongMap = new HashMap<>();
		Map<Integer, String> defaultColorMap = new HashMap<>();

		for (Product product : products) {
			List<String> dungLuongs = productDetailService.getDungLuongByProductId(product.getProductId());
			if (dungLuongs != null && !dungLuongs.isEmpty()) {
				String lowestDungLuong = dungLuongs.stream().min(Comparator.comparing(dl -> {
					Double price = productDetailService.getPriceByColorDungLuongProductId(product.getProductId(), dl,
							null);
					return price != null ? price : Double.MAX_VALUE;
				})).orElse(null);

				if (lowestDungLuong != null) {
					List<String> colors = productDetailService.getColorByDungLuong(product.getProductId(),
							lowestDungLuong);
					if (colors != null && !colors.isEmpty()) {
						String lowestColor = colors.stream().min(Comparator.comparing(c -> {
							Double price = productDetailService
									.getPriceByColorDungLuongProductId(product.getProductId(), lowestDungLuong, c);
							return price != null ? price : Double.MAX_VALUE;
						})).orElse(null);

						defaultDungLuongMap.put(product.getProductId(), lowestDungLuong);
						defaultColorMap.put(product.getProductId(), lowestColor);
					}
				}
			}
		}

		model.addAttribute("defaultDungLuongMap", defaultDungLuongMap);
		model.addAttribute("defaultColorMap", defaultColorMap);
		return "/user/index";
	}

}
