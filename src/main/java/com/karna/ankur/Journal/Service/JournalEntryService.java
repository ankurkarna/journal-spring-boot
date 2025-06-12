package com.karna.ankur.Journal.Service;

import com.karna.ankur.Journal.Entity.JournalEntry;
import com.karna.ankur.Journal.Entity.UserEntity;
import com.karna.ankur.Journal.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    JournalEntryRepository journalEntryRepository;
    //    JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntryModel, String userName) {
        try {
            UserEntity user = userService.findByUserName(userName);
            journalEntryModel.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntryModel);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);

        } catch (Exception e) {
            System.out.println("save method failed due to some internal error");
        }

    }

    //overloaded the save entry method to use in put method as currently we do not required username parameter
    public void saveEntry(JournalEntry journalEntryModel) {
        journalEntryModel.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntryModel);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteJournalById(ObjectId id, String userName) {
        try {
            UserEntity user = userService.findByUserName(userName);
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);

            }

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("error occurred while trying to delete entry");
        }
    }

//    public List<JournalEntry> findByUserName(String userName){
//
//    }

}
