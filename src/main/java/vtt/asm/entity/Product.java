package vtt.asm.entity;

import java.util.Date;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	@Column(name = "ProductId")
	private int productId;

	@Column(name = "Product_Name", nullable = false, length = 150)
	private String productName;

	@ManyToOne
	@JoinColumn(name = "thuong_hieu_id", nullable = false)
	private ThuongHieu thuongHieu;

	@ManyToOne
	@JoinColumn(name = "CateId", nullable = false)
	private Category category;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_nhap", nullable = false)
	private Date ngayNhap;

	@Column(name = "Img_Product", length = 255)
	private String imgProduct;

	@Column(name = "Description", nullable = false, length = 255)
	private String description;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductImageColor> productImageColors;

	/*
	 * lưu trữ giá đã định dạng để hiển thị trong giao diện người dùng mà không cần
	 * lưu trường này vào cơ sở dữ liệu.
	 */
	/*
	 * Bỏ Qua Lưu Trữ: Trường được đánh dấu bằng @Transient sẽ không được ánh xạ vào
	 * bất kỳ cột nào trong cơ sở dữ liệu. Nó không được lưu trữ trong cơ sở dữ liệu
	 * khi thực hiện các thao tác lưu trữ hoặc truy xuất dữ liệu.
	 * 
	 * Dữ Liệu Tạm Thời:Thường được sử dụng cho các thuộc tính của lớp mà chỉ cần thiết trong thời
	 * gian chạy của ứng dụng hoặc không cần thiết để lưu trữ vào cơ sở dữ liệu.
	 */
	@Transient
	private String lowestPriceFormatted;
}
