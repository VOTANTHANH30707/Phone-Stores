package vtt.asm.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int productId;

	@Column(name = "Product_Name")
	private String productName;

	@ManyToOne
	@JoinColumn(name = "thuong_hieu_id")
	ThuongHieu thuongHieu;

	@ManyToOne
	@JoinColumn(name = "cate_id")
	Category category;

	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_nhap")
	private Date ngayNhap;

	@Column(name = "Img_Product")
	private String imgProduct;

	@Column(name = "Description")
	private String description;

	@JsonManagedReference
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductImageColor> productImageColors;

	@Transient
	String lowestPriceFormatted;
}
