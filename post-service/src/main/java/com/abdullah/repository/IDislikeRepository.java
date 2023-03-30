package com.abdullah.repository;


import com.abdullah.repository.entity.Dislike;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDislikeRepository extends MongoRepository<Dislike,String > {
}
