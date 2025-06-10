package com.karna.ankur.Journal.Service;

import com.karna.ankur.Journal.Entity.UserEntity;
import com.karna.ankur.Journal.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {


    @Autowired
    UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);

    }
    public void saveNewUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(List.of("USER"));
        userRepository.save(userEntity);
    }

    public List<UserEntity> getAllEntries() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getEntryById(ObjectId id) {
        return userRepository.findById(id);
    }

    public UserEntity findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void deleteJournalById(ObjectId id) {
        userRepository.deleteById(id);
    }
}