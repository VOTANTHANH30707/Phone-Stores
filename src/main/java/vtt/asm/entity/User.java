package vtt.asm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "ho_va_ten", nullable = false, length = 50)
    private String hoVaTen;

    @Column(name = "Enable", nullable = false)
    private boolean enable;

    @Column(name = "Gender", nullable = false)
    private int gender;

    @Column(name = "Email", nullable = false, length = 255)
    private String email;

    @Column(name = "Phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "Avatar", length = 255)
    private String avatar;

    @Column(name = "Password", nullable = false, length = 50)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoleUser> roleUsers;
}
