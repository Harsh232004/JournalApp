package com.auction.springrestapi.Service;

import com.auction.springrestapi.DTO.UserDTO;
import com.auction.springrestapi.Entity.Order;
import com.auction.springrestapi.Entity.User;
import com.auction.springrestapi.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user.setUserName(user.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(List.of("USER"));
        User savedUser = userRepository.save(user);
        convertToDTO(savedUser);
    }

    public List<UserDTO> getAllUser(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this :: convertToDTO)
                .toList();
    }

    public UserDTO getUserByName(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    public UserDTO updateUser(String userName, UserDTO userDTO) {
        User existingUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDTO.getUserName() != null) {
            existingUser.setUserName(userDTO.getUserName());
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDTO(updatedUser);
    }

    public UserDTO deleteByName(String userName){
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        UserDTO userDTO = convertToDTO(user);
        userRepository.delete(user);
        return userDTO;
    }

    // Converts User Entity to DTO
    UserDTO convertToDTO(User user) {
        List<Order> orders = null;
        if (user.getOrder() != null) {
            orders = user.getOrder().stream()
                    .map(order -> new Order(
                            order.getId(),
                            order.getProduct(),
                            order.getModel(),
                            order.getDate(),
                            null)) // Exclude back-reference to User
                    .toList();
        }

        return new UserDTO(
                user.getUserName(),
                user.getPassword(), // Ignored via @JsonIgnore in DTO
                user.getSentiment(),
                orders
        );
    }

    // Converts DTO to User Entity
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword()); // Raw password for encoding
        return user;
    }
}
