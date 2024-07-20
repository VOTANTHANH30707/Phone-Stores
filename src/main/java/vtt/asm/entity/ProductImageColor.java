package vtt.asm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product_Image_Color")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductImageColor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productImgColorId;

	@Column(name = "dung_luong", length = 255)
	private String dungLuong;

	@Column(name = "Name_Img", length = 255)
	private String nameImg;

	@Column(name = "Gia")
	private Double price;

	@Column(name = "Color", length = 255)
	private String color;

	@ManyToOne
	@JoinColumn(name = "ProductId", nullable = false)
	private Product product;

}
