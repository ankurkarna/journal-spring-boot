package com.karna.ankur.Journal.Controller;

import com.karna.ankur.Journal.Entity.UserEntity;
import com.karna.ankur.Journal.Service.UserService;
import com.karna.ankur.Journal.cache.AppCache;
import com.karna.ankur.Journal.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> allEntries = userService.getAllEntries();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdminUser(@RequestBody UserEntity userEntity) {
        userService.saveAdmin(userEntity);
    }

    @GetMapping("/clear-app-cache")
    public void clearCache() {
        appCache.init();
    }

    // Endpoint to promote a user to admin (only accessible by admins)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/promote")
    public ResponseEntity<?> promoteToAdmin(@RequestParam String username) {
        UserEntity user = userRepository.findByUserName(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<String> roles = user.getRoles();
        if (!roles.contains("ADMIN")) {
            roles.add("ADMIN");
            user.setRoles(roles);
            userRepository.save(user);
        }
        return ResponseEntity.ok("User promoted to admin");
    }

     // Endpoint to demote a admin to user (only accessible by admins)
     @PreAuthorize("hasRole('ADMIN')")
     @PostMapping("/demote")
     public ResponseEntity<?> demoteToUser(@RequestParam String username) {
         UserEntity user = userRepository.findByUserName(username);
         if (user == null) {
             return ResponseEntity.notFound().build();
         }
         List<String> roles = user.getRoles();
         if (roles.contains("ADMIN")) {
             roles.remove("ADMIN");
             user.setRoles(roles);
             userRepository.save(user);
         }
         return ResponseEntity.ok("Admin demoted to user");
     }
}
