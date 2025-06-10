package com.karna.ankur.Journal.Controller;

import com.karna.ankur.Journal.Entity.UserEntity;
import com.karna.ankur.Journal.Repository.UserRepository;
import com.karna.ankur.Journal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

//    @GetMapping
//    public ResponseEntity<?> getUser() {
//        List<UserEntity> userEntities = userService.getAllEntries();
//        if (userEntities != null && !userEntities.isEmpty()) {
//            return new ResponseEntity<>(userEntities, HttpStatus.OK);
//        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping("/id/{userId}")
//    public ResponseEntity<?> getUserById(@PathVariable ObjectId userId) {
//        if (userService.getEntryById(userId).isPresent()) {
//            return new ResponseEntity<>(userService.getEntryById(userId).get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

// moved to public controller
//    @PostMapping
//    public ResponseEntity<?> newUser(@RequestBody UserEntity userEntity) {
//        try {
//            userService.saveNewUser(userEntity);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity userInDb = userService.findByUserName(userName);
        userInDb.setUserName(userEntity.getUserName());
        userInDb.setPassword(userEntity.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}

