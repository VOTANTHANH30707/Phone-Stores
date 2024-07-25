package vtt.asm.service;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vtt.asm.dao.ProductImageColorRepository;
import vtt.asm.dao.ProductRepository;
import vtt.asm.entity.Product;
import vtt.asm.entity.ProductImageColor;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private ProductImageColorRepository productImgColorRepo;

	public List<Product> findProductByCategory(Integer cateId) {
		return productRepo.findProductByCategory(cateId);
	}

	public List<Product> getProductsWithFormattedPrices() {
		List<Product> products = productRepo.findProductsWithImageColors();
		formatProductPrices(products);
		return products;
	}

	private void formatProductPrices(List<Product> products) {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")); // Định dạng VND
		products.forEach(product -> {
			if (!product.getProductImageColors().isEmpty()) {
				Double lowestPrice = product.getProductImageColors().stream()
						.min(Comparator.comparing(ProductImageColor::getPrice)).map(ProductImageColor::getPrice)
						.orElse(0.0); // Giá mặc định nếu không có giá
				product.setLowestPriceFormatted(currencyFormat.format(lowestPrice));
			} else {
				product.setLowestPriceFormatted(currencyFormat.format(0.0)); // Giá mặc định nếu không có giá
			}
		});
	}
}
