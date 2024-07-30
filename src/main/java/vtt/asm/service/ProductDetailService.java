package vtt.asm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vtt.asm.dao.ProductImageColorRepository;
import vtt.asm.dao.ProductRepository;
import vtt.asm.entity.Product;
import vtt.asm.entity.ProductImageColor;

@Service
public class ProductDetailService {
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private ProductImageColorRepository productImgColorRepo;

	public List<ProductImageColor> getAllProductImageColors() {
		return productImgColorRepo.findAll();
	}

	public Product getProductById(int productId) {
		return productRepo.findById(productId).orElse(null);
	}

	public List<String> getDungLuongByProductId(int productId) {
		return productImgColorRepo.findDungLuongByProductId(productId);
	}

	public List<String> getColorByProductId(int productId) {
		return productImgColorRepo.findColorByProductId(productId);
	}

	public List<String> getColorByDungLuong(int productId, String dungLuong) {
		return productImgColorRepo.findColorByDungLuong(productId, dungLuong);
	}

	public List<String> getImagesByProductId(int productId) {
		return productImgColorRepo.findImageByProductId(productId);
	}

	public List<String> getImagesByProductIdDungLuong(int productId, String dungLuong) {
		return productImgColorRepo.findImageByProductIdDungLuong(productId, dungLuong);
	}

	public Double getPriceByColorDungLuongProductId(int productId, String dungLuong, String color) {
		return productImgColorRepo.findPriceByProductIdDungLuongColor(productId, dungLuong, color);
	}
	/*
	 * public List<String> getImagesByDungLuong(int productId) { return
	 * productImgColorRepo.findImageByProductId(productId); }
	 */

}
