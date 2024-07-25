package vtt.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vtt.asm.dto.ProductDTO;
import vtt.asm.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p JOIN p.productImageColors pic")
	List<Product> findProductsWithImageColors();

	@Query("SELECT p from Product p where p.category.cateId = :cateId")
	List<Product> findProductByCategory(@Param("cateId") int cateId);
}
