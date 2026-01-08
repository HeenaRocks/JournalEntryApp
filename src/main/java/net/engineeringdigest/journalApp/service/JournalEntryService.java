package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService  {

    @Autowired //dependency injection
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveJournalEntry(JournalEntry journalEntry, String userName)
    {
        try {
            User user= userService.findByUserName(userName);
            JournalEntry savedJournalEntry= journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedJournalEntry);
            //user.setUserName(null);
            userService.saveUser(user);
        }
        catch (Exception e)
        {
            System.out.println("heena exception:"+ e);
            throw new RuntimeException("An error occured while saving the entry:",e);
        }
    }

    public void saveJournalEntry(JournalEntry journalEntry)
    {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllJournal()
    {
     return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findByJournalId(ObjectId myId)
    {
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public boolean deleteByJournalId(ObjectId myId, String userName)
    {
        boolean removed=false;
        try {
            User user= userService.findByUserName(userName);
            removed= user.getJournalEntries().removeIf(x->x.getId().equals(myId));
            if(removed)
            {
                userService.saveUser(user);
                journalEntryRepository.deleteById(myId);
            }
        }catch (Exception e)
        {
            System.out.println("e :"+e);
            throw new RuntimeException("An error occured while deleting the entry :"+e);
        }
        return removed;

    }

    public void updateByJournalId(ObjectId myId, String newTitle)
    {
        journalEntryRepository.updateTitleById(myId,newTitle);
    }
}



//controller calls--->service calls--->repository