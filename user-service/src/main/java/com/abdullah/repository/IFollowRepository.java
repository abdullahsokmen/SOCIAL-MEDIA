package com.abdullah.repository;

import com.abdullah.repository.entity.Follow;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFollowRepository extends MongoRepository<Follow,String> {

    Optional<Follow>findOptionalByUserIdAndFollowId(String userId,String followId);


}
