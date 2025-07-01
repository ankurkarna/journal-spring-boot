package com.karna.ankur.Journal.Controller;

import com.karna.ankur.Journal.Entity.UserEntity;
import com.karna.ankur.Journal.Repository.UserRepository;
import com.karna.ankur.Journal.Service.UserService;
import com.karna.ankur.Journal.utils.JwtUtil;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> newUser(@RequestBody UserEntity userEntity) {
        try {
            userService.saveNewUser(userEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserEntity signUpRequest) {
        UserEntity existingUser = userRepository.findByUserName(signUpRequest.getUserName());

        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        // Encrypt the password before saving
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encodedPassword);
        signUpRequest.setRoles(Collections.singletonList("USER"));

        // Save new user
        UserEntity savedUser = userRepository.save(signUpRequest);
        System.out.println("Encoded password: " + signUpRequest.getPassword());

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserEntity loginRequest) {
        UserEntity user = userRepository.findByUserName(loginRequest.getUserName());

        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUserName());
            System.out.println("Entered Password: " + loginRequest.getPassword());
            System.out.println("Encoded Password in DB: " + user.getPassword());
            System.out.println(
                    "Password matches: " + passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()));
            return ResponseEntity.ok().body("JWT Token: " + token);

        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

    }

}
