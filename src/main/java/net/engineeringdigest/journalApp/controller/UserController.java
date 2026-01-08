package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private WeatherService weatherService;

    @PutMapping()
    public ResponseEntity<?> updateUserName(@RequestBody User user)
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        String userName= authentication.getName();
        User userWithUpdate=   userService.findByUserName(userName);

            userWithUpdate.setUserName(user.getUserName());
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            userWithUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        }
//            userWithUpdate.setPassword(user.getPassword());
            userService.saveUser(userWithUpdate);
            //return  new ResponseEntity<>(userWithUpdate, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser()
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        userService.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings()
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse response= weatherService.getWeather("Mumbai");
        String greeting="";
        if(response !=null)
        {
            greeting="Weather feels like..."+response.getCurrent().feelslike;
        }
        return new ResponseEntity<> ("Hi "+ authentication.getName()+ greeting ,HttpStatus.OK);
    }

//        @PutMapping("/{userName}")
//        public ResponseEntity<?> updateUserName(@RequestBody User user, @PathVariable String userName)
//        {
//            User userWithUpdate=   userService.findByUserName(userName);
//            if(userWithUpdate !=null) // && !userWithUpdate.equals(""))
//            {
//                userWithUpdate.setUserName(user.getUserName());
//                userWithUpdate.setPassword(user.getPassword());
//                userService.saveUser(userWithUpdate);
//                //return  new ResponseEntity<>(userWithUpdate, HttpStatus.OK);
//            }
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

//    @GetMapping
//    public List<User> getAlluser()
//    {
//        return userService.getAllUser();
//    }
//
//    @PostMapping
//    public void createUser(@RequestBody User user)
//    {
//        System.out.println("user:"+user);
//        userService.saveNewUser(user);
//    }

//    @GetMapping("id/{id}")
//    public Optional<User> findByUserId(@PathVariable ObjectId id)
//    {
//      return userService.findByUserId(id);
//    }


//
//    @PutMapping("id/{id}/{password}")
//    public void updateUser(@PathVariable ObjectId id, String password)
//    {
//        userService.updatepasswordById(id, password);
//    }
}
