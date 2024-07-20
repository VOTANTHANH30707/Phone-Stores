package vtt.asm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vtt.asm.entity.ProductImageColor;

public interface ProductImageColorRepository extends JpaRepository<ProductImageColor, Integer> {

}
