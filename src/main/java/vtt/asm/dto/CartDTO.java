package vtt.asm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
	private int productId;
	private String color;
	private String dungLuong;
	private int quantity;
	private int userId;
}