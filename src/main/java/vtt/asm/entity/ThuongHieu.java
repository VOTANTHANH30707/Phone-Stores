package vtt.asm.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "thuong_hieu")
public class ThuongHieu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "thuong_hieu_id")
	private int thuongHieuId;

	@Column(name = "ten_thuong_hieu", nullable = false, length = 100)
	private String tenThuongHieu;

	@Column(name = "image_name", length = 255)
	private String imageName;

	@OneToMany(mappedBy = "thuongHieu", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Product> products;
}
