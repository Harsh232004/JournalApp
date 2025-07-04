package com.auction.springrestapi.Controller;

import com.auction.springrestapi.DTO.UserDTO;
import com.auction.springrestapi.Entity.Order;
import com.auction.springrestapi.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {


    private final OrderService orderservice;

    @Autowired
    public OrderController(OrderService orderservice) {
        this.orderservice = orderservice;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Order> order = orderservice.findAll();
        if (order.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(order);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Order order, @RequestParam String userName) {
        UserDTO order1 = orderservice.saveOrder(order, userName);
        if (order1 != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(order1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not FOUND");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order, @PathVariable Long id) {
        Order updatedOrder = orderservice.updateOrder(order, id);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){
        orderservice.deleteById(id);
        return ResponseEntity.ok("Order deleted successfully");
    }


}
