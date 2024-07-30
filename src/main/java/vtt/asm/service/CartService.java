package vtt.asm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vtt.asm.dao.CartRepository;
import vtt.asm.dao.ProductImageColorRepository;
import vtt.asm.dao.ProductRepository;
import vtt.asm.dao.UserRepository;
import vtt.asm.dto.CartDTO;
import vtt.asm.entity.Cart;
import vtt.asm.entity.Product;
import vtt.asm.entity.ProductImageColor;
import vtt.asm.entity.User;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductImageColorRepository productImageColorRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	HttpSession session;

//	public int getCount(int userId) {
//		User user = (User) session.getAttribute("userSession");
//		if (user == null) {
//			return 0; // Hoặc xử lý lỗi phù hợp
//		}
//		return cartRepository.countByUser(user);
//	}
//
//	@Transactional
//	public void addToCart(CartDTO cartDto) {
//		Product product = productRepository.findById(cartDto.getProductId()).orElseThrow();
//		ProductImageColor productImageColor = productImageColorRepository
//				.findByProductIdAndColorAndDungLuong(cartDto.getProductId(), cartDto.getDungLuong(), cartDto.getColor())
//				.orElseThrow();
//		User user = userRepository.findById(cartDto.getUserId()).orElseThrow();
//		Cart cart = new Cart();
//		cart.setProduct(product);
//		cart.setProductImageColor(productImageColor);
//		cart.setQuality(cartDto.getQuantity());
//		cart.setDate(new Date());
//		cart.setUser(user);
//
//		cartRepository.save(cart);
//	}

	public List<Cart> getCartById(Integer cartId) {
		return cartRepository.findByUserId(cartId);
	}
}
