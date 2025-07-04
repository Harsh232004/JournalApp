package com.auction.springrestapi.Controller;

import com.auction.springrestapi.DTO.UserDTO;
import com.auction.springrestapi.Service.UserService;
import com.auction.springrestapi.UserDetailsImpl.UserDetailsServiceImpl;
import com.auction.springrestapi.utilis.jwtUtilis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/temp")
@Slf4j
public class TempController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private jwtUtilis jwtUtilis;



    @GetMapping("/get")
    public ResponseEntity<?> temp(){
        userService.getAllUser();
        return ResponseEntity.ok().build();
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO)
    {
       userService.saveUser(userDTO);
       return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO user){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String token = jwtUtilis.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        catch (Exception e){ 
            log.error("Exception occurred while createAuthenticationToken {}", String.valueOf(e));
            return new ResponseEntity<>("Incorrect Password or password", HttpStatus.BAD_REQUEST);
        }
    }
}
