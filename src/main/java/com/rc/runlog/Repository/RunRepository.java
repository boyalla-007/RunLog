package com.rc.runlog.Repository;

import com.rc.runlog.model.Run;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RunRepository extends MongoRepository<Run, ObjectId> {
    List<Run> findByUserId(ObjectId  userId);
}
