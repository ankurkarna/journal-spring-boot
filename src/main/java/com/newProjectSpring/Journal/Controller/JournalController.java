package com.newProjectSpring.Journal.Controller;

import com.newProjectSpring.Journal.Model.JournalData;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("entry")
public class JournalController {

    private final Map<Long, JournalData> journalEntries = new HashMap<>();


    @GetMapping
    public List<JournalData> showAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean addNewEntry(@RequestBody JournalData myData){
        journalEntries.put(myData.getId(), myData);
        return true;
    }

    public void updateEntry(){

    }
}
