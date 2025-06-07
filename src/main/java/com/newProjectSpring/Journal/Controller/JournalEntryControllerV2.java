package com.newProjectSpring.Journal.Controller;

import com.newProjectSpring.Journal.Entity.JournalEntry;
import com.newProjectSpring.Journal.Entity.UserEntity;
import com.newProjectSpring.Journal.Service.JournalEntryService;
import com.newProjectSpring.Journal.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping//("{/userName}")
    public ResponseEntity<?> getAllJournal(@PathVariable String userName) {
//        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> allEntries = journalEntryService.getAllEntries();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //get journal by ID
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> findById(@PathVariable ObjectId myId) {
//         journalEntryService.getEntryById(myId).orElse(null);
        if (journalEntryService.getEntryById(myId).isPresent()) {
            return new ResponseEntity<>(journalEntryService.getEntryById(myId).get(), HttpStatus.OK);

        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addNewEntry(@RequestBody JournalEntry journalEntryModel) {
        try {
            journalEntryModel.setDate(LocalDateTime.now());
            journalEntryService.saveEntries(journalEntryModel);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //    @PutMapping("/id/{myId}")
//    public void updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntryModel journalEntryModel) {
//        JournalEntryModel old = journalEntryService.getEntryById(myId).orElse(null);
//        if (old != null) {
//            if (journalEntryModel.getTitle() != null && !journalEntryModel.getTitle().isEmpty()) {
//                old.setTitle(journalEntryModel.getTitle());
//            } else {
//                old.setTitle(old.getTitle());
//            }
//            if (journalEntryModel.getContent() != null && !journalEntryModel.getContent().isEmpty()) {
//                old.setContent(journalEntryModel.getContent());
//            } else {
//                old.setContent(old.getContent());
//            }
//            journalEntryService.saveEntries(old);
//        }
//    }
    //update entry by id using ternary and response entity
    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry journalEntryModel) {
        Optional<JournalEntry> old = journalEntryService.getEntryById(myId);
        if (old.isPresent()) {
            JournalEntry oldEntry = old.get();

            oldEntry.setTitle(journalEntryModel.getTitle() != null && !journalEntryModel.getTitle().isEmpty()
                    ? journalEntryModel.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(journalEntryModel.getContent() != null && !journalEntryModel.getContent().isEmpty()
                    ? journalEntryModel.getContent() : oldEntry.getContent());
            journalEntryService.saveEntries(oldEntry);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId) {

        Optional<JournalEntry> id = journalEntryService.getEntryById(myId);
        if (id.isPresent()) {
            journalEntryService.deleteJournalById(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }
}