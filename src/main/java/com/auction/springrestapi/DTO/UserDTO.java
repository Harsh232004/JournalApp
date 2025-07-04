package com.auction.springrestapi.DTO;

import com.auction.springrestapi.Entity.Order;
import com.auction.springrestapi.enums.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userName;

    private String password;

    private Sentiment sentiment;

    private List<Order> order;
}
