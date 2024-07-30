package vtt.asm.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vtt.asm.dao.ProductImageColorRepository;
import vtt.asm.dao.ProductRepository;
import vtt.asm.dto.ProductDTO;
import vtt.asm.entity.Product;
import vtt.asm.entity.ProductImageColor;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private ProductImageColorRepository productImgColorRepo;

	public List<ProductDTO> findAll() {
		List<Product> products = productRepo.findAll();
		List<ProductDTO> productDTOs = new ArrayList<>();
		products.stream().forEach(product -> productDTOs.add(convertToDTO(product)));
		return productDTOs;
	}

	private ProductDTO convertToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setProductId(product.getProductId());
		dto.setProductName(product.getProductName());
		dto.setThuongHieu(product.getThuongHieu().getTenThuongHieu());
		dto.setCategory(product.getCategory().getName());
		dto.setNgayNhap(product.getNgayNhap());
		dto.setImgProduct(product.getImgProduct());
		dto.setDescription(product.getDescription());
		return dto;
	}

	public Product create(Product product) {
		return productRepo.save(product);
	}

	public Product update(Product product) {
		return productRepo.save(product); // Save will update the entity if it already exists
	}

	public Product findById(Integer id) {
		return productRepo.findById(id).orElse(null);
	}

	public List<Product> findProductByCategory(Integer cateId) {
		return productRepo.findProductByCategory(cateId);
	}

	public List<Product> getProductsWithFormattedPrices() {
		List<Product> products = productRepo.findProductsWithImageColors();
		formatProductPrices(products);
		return products;
	}

	public List<Product> getProductsWithFormattedPricesCate(int cateId) {
		List<Product> products = productRepo.findProductByCategory(cateId);
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

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		productRepo.deleteById(id);
	}

}
