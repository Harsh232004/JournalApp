package com.auction.springrestapi.Service;

import com.auction.springrestapi.DTO.UserDTO;
import com.auction.springrestapi.Entity.Order;
import com.auction.springrestapi.Entity.User;
import com.auction.springrestapi.Repo.OrderRepo;
import com.auction.springrestapi.Repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final UserService userService;
    private final UserRepo userRepo;


    @Autowired
    public OrderService(OrderRepo orderRepo, UserService userService, UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    public List<Order> findAll(){
        return orderRepo.findAll();
    }

    @Transactional
    public UserDTO saveOrder(Order order, String userName) {
        User user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        order.setDate(LocalDateTime.now());
        order.setUser(user); // Important: link order to user

        Order savedOrder = orderRepo.save(order);

        user.getOrder().add(savedOrder); // optional: if mappedBy is set on Order
        User updatedUser = userRepo.save(user); // persist association

        return userService.convertToDTO(updatedUser);
    }

    @Transactional
    public Order updateOrder(Order order, Long id) {

        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order details are not found"));

        Long newUserId = (order.getUser() != null) ? order.getUser().getId() : null;

        if (newUserId == null) {
            throw new RuntimeException("User ID must not be null");
        }

        User user = userRepo.findById(newUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (order.getModel() != null) {
            existingOrder.setModel(order.getModel());
        }
        if (order.getProduct() != null && !order.getProduct().isEmpty()) {
            existingOrder.setProduct(order.getProduct());
        }

        existingOrder.setUser(user);

        return orderRepo.save(existingOrder);
    }

    public void deleteById(Long id){
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order Not Found"));
        orderRepo.delete(order);
    }


}
