package vtt.asm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import vtt.asm.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
