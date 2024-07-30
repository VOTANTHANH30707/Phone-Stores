package vtt.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vtt.asm.entity.Cart;
import vtt.asm.entity.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	@Query("Select cart from Cart cart where cart.user.userId = :userId")
	List<Cart> findByUserId(@Param("userId") Integer userId);

	int countByUser(User user);
}
