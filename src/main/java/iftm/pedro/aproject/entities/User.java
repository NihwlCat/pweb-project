package iftm.pedro.aproject.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_USER")
public class User {
    private Long id;
    private String nickname;
    private String email;
    private String password;

    private String role;

    private List<Order> orders = new ArrayList<>(); // LAZY

    public User() {
    }

    public User(Long id, String nickname, String email, String password, String role) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @OneToMany(mappedBy = "user")
    public List<Order> getOrders() {
        return orders;
    }

    @Column (unique = true)
    public String getEmail() {
        return email;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
