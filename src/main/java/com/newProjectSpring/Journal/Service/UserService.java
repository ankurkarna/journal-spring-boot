package com.newProjectSpring.Journal.Service;

import com.newProjectSpring.Journal.Entity.UserEntity;
import com.newProjectSpring.Journal.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {


    @Autowired
    UserRepository userRepository;

    public void saveEntries(UserEntity userEntity) {
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