package com.karna.ankur.Journal.Controller;

import com.karna.ankur.Journal.Entity.UserEntity;
import com.karna.ankur.Journal.Service.UserService;
import com.karna.ankur.Journal.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<UserEntity> allEntries = userService.getAllEntries();
        if ( allEntries != null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdminUser(@RequestBody UserEntity userEntity){
        userService.saveAdmin(userEntity);
    }

    @GetMapping("/clear-app-cache")
    public void clearCache(){
        appCache.init();
    }
}
