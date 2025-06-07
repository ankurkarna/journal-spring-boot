package com.newProjectSpring.Journal.Service;

import com.newProjectSpring.Journal.Entity.JournalEntry;
import com.newProjectSpring.Journal.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    JournalEntryRepository journalEntryRepository;
//    JournalEntryService journalEntryService;

    public void saveEntries(JournalEntry journalEntryModel){
        journalEntryRepository.save(journalEntryModel);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteJournalById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }

}
