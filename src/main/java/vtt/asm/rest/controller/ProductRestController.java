package vtt.asm.rest.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Delegate;
import vtt.asm.service.ProductService;
import vtt.asm.dao.CategoryRepository;
import vtt.asm.dao.ThuongHieuRepository;
import vtt.asm.dto.ProductDTO;
import vtt.asm.entity.Category;
import vtt.asm.entity.Product;
import vtt.asm.entity.ThuongHieu;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
	@Autowired
	private ProductService productService;
	@Autowired
	ThuongHieuRepository thuongHieuRepo;
	@Autowired
	CategoryRepository cateRepo;

	@GetMapping
	public List<ProductDTO> getAll() {
		return productService.findAll();
	}

	@GetMapping("/thuongHieus")
	public List<ThuongHieu> getThuongHieuAll() {

		return thuongHieuRepo.findAll();
	}

	@GetMapping("/categories")
	public List<Category> getCateAll() {
		return cateRepo.findAll();
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Product product) {
		// Kiểm tra xem sản phẩm đã tồn tại chưa
		Product existingProduct = productService.findById(product.getProductId());

		if (existingProduct == null) {
			// Nếu sản phẩm chưa tồn tại, thực hiện tạo sản phẩm mới
			product.setNgayNhap(new Date());
			Product createdProduct = productService.create(product);
			return ResponseEntity.ok(createdProduct); // Trả về sản phẩm mới tạo và mã trạng thái HTTP 200 OK
		} else {
			// Nếu sản phẩm đã tồn tại, trả về thông báo lỗi hoặc mã trạng thái HTTP 400 Bad
			// Request
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sản phẩm đã tồn tại");
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Product> update(@PathVariable Integer id, @RequestBody Product product) {
		// Find the existing product by id
		Product existingProduct = productService.findById(id);
		if (existingProduct == null) {
			return ResponseEntity.notFound().build();
		}

		// Update the fields of the existing product
		existingProduct.setProductName(product.getProductName());
		existingProduct.setThuongHieu(product.getThuongHieu());
		existingProduct.setCategory(product.getCategory());
		existingProduct.setNgayNhap(product.getNgayNhap());
		existingProduct.setImgProduct(product.getImgProduct());
		existingProduct.setDescription(product.getDescription());
		existingProduct.setProductImageColors(product.getProductImageColors());

		// Save the updated product
		Product updatedProduct = productService.update(existingProduct);
		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		productService.delete(id);
	}

}
