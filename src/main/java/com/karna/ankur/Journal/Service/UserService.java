package com.karna.ankur.Journal.Service;

import com.karna.ankur.Journal.Entity.UserEntity;
import com.karna.ankur.Journal.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {


    @Autowired
    UserRepository userRepository;

//    Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);

    }

    public boolean saveNewUser(UserEntity userEntity) {
        try {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setRoles(List.of("USER"));
            userRepository.save(userEntity);
            return true;

        } catch (Exception e) {
//            throw new RuntimeException(e);
            log.error("error for user {}",userEntity.getUserName(), e);
            log.info("username is already in use");
            log.warn("username is already in use");
            log.trace("username is already in use");
            log.debug("username is already in use");
            return false;
        }
    }

    public List<UserEntity> getAllEntries() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getEntryById(ObjectId id) {
        return userRepository.findById(id);
    }

    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void deleteJournalById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public void saveAdmin(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(List.of("USER", "ADMIN"));
        userRepository.save(userEntity);
    }
}