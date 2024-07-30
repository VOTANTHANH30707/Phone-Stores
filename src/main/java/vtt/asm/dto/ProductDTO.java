package vtt.asm.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private int productId;
    private String productName;
    private String thuongHieu;
    private String category;
    private Date ngayNhap;
    private String imgProduct;
    private String description;

    // Getters and Setters
}

