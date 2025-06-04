package com.newProjectSpring.Journal.Controller;

import com.newProjectSpring.Journal.Model.JournalData;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("journal")
public class JournalController {

    private final Map<Long, JournalData> journalEntries = new HashMap<>();




    //to get all journal entries
    @GetMapping
    public List<JournalData> getAllJournal(){
        return new ArrayList<>(journalEntries.values());
    }

    // find journal entries by id
    @GetMapping("id/{myId}")
    public JournalData updateEntry(@PathVariable Long myId){
        return journalEntries.get(myId);

    }
    // create new journal entries
    @PostMapping
    public boolean addNewEntry(@RequestBody JournalData myData){
        journalEntries.put(myData.getId(), myData);
        return true;
    }


    //update entry by id
    @PutMapping("/id/{myId}")
    public JournalData updateJournalEntries(@PathVariable Long myId, @RequestBody JournalData updateData){
         return journalEntries.put(myId,updateData);
    }

    // delete entries by id
    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntries(@RequestBody Long myId){
        journalEntries.remove(myId);
        return true;
    }
}
