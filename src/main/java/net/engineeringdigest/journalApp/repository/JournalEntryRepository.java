package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

    @Query("{'_ObjectId': ?0}")
    @Update("{'$set' : {'title': ?1}}")
    void updateTitleById(ObjectId myId, String newTitle);


}
