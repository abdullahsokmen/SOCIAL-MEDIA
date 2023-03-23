package com.abdullah.mapper;

import com.abdullah.dto.request.NewCreateUserRequestDto;
import com.abdullah.dto.request.UpdateEmailOrUsernameRequestDto;
import com.abdullah.dto.request.UserProfileUpdateRequestDto;
import com.abdullah.rabbitmq.model.RegisterElasticModel;
import com.abdullah.rabbitmq.model.RegisterModel;
import com.abdullah.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE= Mappers.getMapper(IUserMapper.class);

    UserProfile toUserProfile(final NewCreateUserRequestDto dto);

    UserProfile toUserProfile(final RegisterModel model);

    NewCreateUserRequestDto toNewCreateUserRequestDto(final RegisterModel model);

    UpdateEmailOrUsernameRequestDto toUpdateEmailOrUsernameRequestDto(final UserProfileUpdateRequestDto dto);

    RegisterElasticModel toRegisterElasticModel(final UserProfile userProfile);



}
