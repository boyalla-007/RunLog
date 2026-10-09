package com.rc.runlog.Repository;
import java.time.LocalDate;
import java.util.Optional;
import com.rc.runlog.model.Run;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RunRepository extends MongoRepository<Run, ObjectId> {
    Page<Run> findByUserIdOrderByDateDesc(ObjectId  userId,Pageable pageable);
    Optional<Run> findByIdAndUserId(ObjectId id, ObjectId userId);
    Page<Run> findByUserIdAndDateGreaterThanEqualOrderByDateDesc(
            ObjectId userId,
            LocalDate from,
            Pageable pageable
    );
    Page<Run> findByUserIdAndDateLessThanEqualOrderByDateDesc(
            ObjectId userId,
            LocalDate to,
            Pageable pageable
    );
    @Query(
            value = "{ 'userId': ?0, 'date': { '$gte': ?1, '$lte': ?2 } }",
            sort = "{ 'date': -1 }"
    )
    Page<Run> findByUserIdAndDateBetween(
            ObjectId userId,
            LocalDate from,
            LocalDate to,
            Pageable pageable
    );
    List<Run> findByUserId(ObjectId userId);
    Optional<Run> findTopByUserIdOrderByDistanceDesc(ObjectId userId);

}
