package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user=    userService.findByUserName(userName);
        List<JournalEntry> all=  user.getJournalEntries();
        if(all !=null)
        {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry ){
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Authentication: " + authentication);
            String userName= authentication.getName();
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveJournalEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myId}")//in path variable path(ex "id" here) is compulsory before variable
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user= userService.findByUserName(userName);
       List<JournalEntry> collect= user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty())
        {
            Optional <JournalEntry> journalEntry =journalEntryService.findByJournalId(myId);
            if(journalEntry.isPresent())
            {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        boolean removed= journalEntryService.deleteByJournalId(myId,userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("id/{myId}/{title}") // update is done using query
//    public void updateById(@PathVariable ObjectId myId, @PathVariable String title) {
//
//        journalEntryService.updateByJournalId(myId,title);
//    }

    @PutMapping("id/{myId}") // update using code
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user= userService.findByUserName(userName);
        List<JournalEntry> collect= user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            Optional <JournalEntry> journalEntry =journalEntryService.findByJournalId(myId);
            if(journalEntry.isPresent()) {
                JournalEntry old=journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && ! newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() !=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveJournalEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    //        @GetMapping("myId")//in  requestparam have to use ?myId=2
//    public JournalEntry getJournalEntryById(@RequestParam Long myId)
//    {
//        System.out.println("id:"+journalEntries.get(myId));
//       return journalEntries.get(myId);
//    }
}

