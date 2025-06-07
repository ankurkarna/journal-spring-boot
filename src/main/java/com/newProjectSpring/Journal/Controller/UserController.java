package com.newProjectSpring.Journal.Controller;

import com.newProjectSpring.Journal.Entity.UserEntity;
import com.newProjectSpring.Journal.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getUser() {
        List<UserEntity> userEntities = userService.getAllEntries();
        if (userEntities != null && !userEntities.isEmpty()) {
            return new ResponseEntity<>(userEntities, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId userId) {
        if (userService.getEntryById(userId).isPresent()) {
            return new ResponseEntity<>(userService.getEntryById(userId).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> newUser(@RequestBody UserEntity userEntity) {
        try {
            userService.saveEntries(userEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable String userName, @RequestBody UserEntity userEntity) {
        UserEntity userInDb = userService.findByUserName(userName);
        if (userInDb != null) {
            userInDb.setUserName(userEntity.getUserName());
            userInDb.setPassword(userEntity.getPassword());
            userService.saveEntries(userInDb);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
