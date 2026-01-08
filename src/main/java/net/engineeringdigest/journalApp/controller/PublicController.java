package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController
{
    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/signup")
    public void signUp(@RequestBody User user)
    {
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user)
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
        UserDetails userDetails= userDetailsService.loadUserByUsername(user.getUserName());
        String jwt=  jwtUtil.generateToken(userDetails.getUsername());
        return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }




//    @PostMapping("/create-user")
//    public void createUser(@RequestBody User user)
//    {
//        System.out.println("user:"+user);
//        userService.saveNewUser(user);
//    }
}
