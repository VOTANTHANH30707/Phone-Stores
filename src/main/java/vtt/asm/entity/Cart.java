package vtt.asm.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private int cartId;

	@Column(name = "Quality", nullable = false)
	private int quality;

	@ManyToOne
	@JoinColumn(name = "product_img_color_id", nullable = false)
	private ProductImageColor productImageColor;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	@Temporal(TemporalType.DATE)
	@Column(name = "Date", nullable = false)
	private Date date;

	@Column(name = "Email", nullable = false, length = 255)
	private String email;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
