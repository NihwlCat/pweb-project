package iftm.pedro.aproject.dtos;

import iftm.pedro.aproject.entities.Order;
import iftm.pedro.aproject.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private String nickname;
    private String email;
    private List<Order> orders = new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.orders = user.getOrders();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
