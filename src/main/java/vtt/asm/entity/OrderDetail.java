package vtt.asm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "OrderDetail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private int orderDetailId;

    @Column(name = "quality", nullable = false)
    private int quality;

    @ManyToOne
    @JoinColumn(name = "product_img_color_id", nullable = false)
    private ProductImageColor productImageColor;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
