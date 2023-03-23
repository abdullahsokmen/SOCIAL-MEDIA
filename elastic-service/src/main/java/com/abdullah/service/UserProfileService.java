package com.abdullah.service;

import com.abdullah.mapper.IElasticMapper;
import com.abdullah.rabbitmq.model.RegisterElasticModel;
import com.abdullah.repository.IUserProfileRepository;
import com.abdullah.repository.entity.UserProfile;
import com.abdullah.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {

    private final IUserProfileRepository repository;

    public UserProfileService(IUserProfileRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public UserProfile createUserWithRabbitMq(RegisterElasticModel model) {
        return save(IElasticMapper.INSTANCE.toUserProfile(model));
    }
}
