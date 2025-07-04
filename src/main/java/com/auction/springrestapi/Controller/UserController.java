package com.auction.springrestapi.Controller;

import com.auction.springrestapi.Api.WeatherAPI;
import com.auction.springrestapi.DTO.UserDTO;
import com.auction.springrestapi.Service.UserService;
import com.auction.springrestapi.Service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userservice;
    private final WeatherService weatherService;


    @Autowired
    public UserController(UserService userservice, WeatherService weatherService) {
        this.userservice = userservice;
        this.weatherService = weatherService;
    }


    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<UserDTO> users = userservice.getAllUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/GetAPI")
    public ResponseEntity<String> greeting(){
        WeatherAPI response = weatherService.getWeather("Mumbai");
        String greeting = " ";
        if (response != null) {
            greeting = " weather feels like " + response.getCurrent().getTemperature();
        }
        return new ResponseEntity<>("Hii harsh" + greeting,HttpStatus.OK);
    }

    @GetMapping("/Get/{userName}")
    public ResponseEntity<?> getUserByID(@PathVariable String userName){
        try {
            /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();*/
            UserDTO user = userservice.getUserByName(userName);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>("User Not found",HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userdto) {
        try {
            /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();*/
            userservice.saveUser(userdto);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Created Successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO UserDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            UserDTO updateUser = userservice.updateUser(userName,UserDTO);
            return new ResponseEntity<>(updateUser,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<UserDTO> deleteUserByName(@PathVariable String userName){
           UserDTO userDTO = userservice.deleteByName(userName);
           return ResponseEntity.ok(userDTO);
    }
}
