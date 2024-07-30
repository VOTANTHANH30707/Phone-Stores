package vtt.asm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vtt.asm.entity.ProductImageColor;
import vtt.asm.entity.User;

public interface ProductImageColorRepository extends JpaRepository<ProductImageColor, Integer> {
	// lấy dung lượng của sản phẩm
	@Query("select DISTINCT pic.dungLuong from ProductImageColor pic join pic.product p "
			+ "where p.productId = :productId")
	List<String> findDungLuongByProductId(@Param("productId") Integer productId);

	// lấy mau của sản phẩm
	@Query("select DISTINCT pic.color from ProductImageColor pic join pic.product p "
			+ "where p.productId = :productId")
	List<String> findColorByProductId(@Param("productId") Integer productId);

	@Query("select DISTINCT pic.nameImg from ProductImageColor pic join pic.product p "
			+ "where p.productId = :productId")
	List<String> findImageByProductId(@Param("productId") Integer productId);

	// tim anh theo productId va dungluong
	@Query("SELECT DISTINCT pic.nameImg FROM ProductImageColor pic JOIN pic.product p "
			+ "WHERE p.productId = :productId AND pic.dungLuong = :dungLuong")
	List<String> findImageByProductIdDungLuong(@Param("productId") Integer productId,
			@Param("dungLuong") String dungLuong);

	@Query("SELECT MAX(pic.price) FROM ProductImageColor pic JOIN pic.product p "
			+ "WHERE p.productId = :productId AND pic.dungLuong = :dungLuong AND pic.color = :color")
	Double findPriceByProductIdDungLuongColor(@Param("productId") Integer productId,
			@Param("dungLuong") String dungLuong, @Param("color") String color);

	@Query("SELECT pic FROM ProductImageColor pic " + "WHERE pic.color = :color AND pic.dungLuong = :dungLuong")
	ProductImageColor findByColorDungLuong(@Param("dungLuong") String dungLuong, @Param("color") String color);

	@Query("SELECT pic.color FROM ProductImageColor pic JOIN pic.product p "
			+ "WHERE p.productId = :productId AND pic.dungLuong = :dungLuong")
	List<String> findColorByDungLuong(@Param("productId") Integer productId, @Param("dungLuong") String dungLuong);

	 @Query("SELECT pic FROM ProductImageColor pic WHERE pic.product.id = :productId AND pic.color = :color AND pic.dungLuong = :dungLuong")
	    Optional<ProductImageColor> findByProductIdAndColorAndDungLuong(@Param("productId") int productId, @Param("dungLuong") String dungLuong, @Param("color") String color);

}
