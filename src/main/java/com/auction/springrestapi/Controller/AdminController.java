package com.auction.springrestapi.Controller;

import com.auction.springrestapi.DTO.UserDTO;
import com.auction.springrestapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/Get/{userName}")
    public ResponseEntity<?> getAdminByID(@PathVariable String userName){
        try {
            /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();*/
            UserDTO user = userService.getUserByName(userName);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>("User Not found",HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody UserDTO userdto){
       try {
           userService.saveUser(userdto);
           return ResponseEntity.status(HttpStatus.CREATED).body(userdto);
       }
       catch (RuntimeException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not created ");
       }
    }
}
