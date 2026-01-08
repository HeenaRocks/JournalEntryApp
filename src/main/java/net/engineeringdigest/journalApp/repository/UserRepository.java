package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    @Query("{'_ObjectId': ?0}")
    @Update("{'$set' : {'password': ?1}}")
    void updatepasswordById(ObjectId id, String password);

    User findByUserName(String userName);

    void deleteByUserName(String userName);

}
