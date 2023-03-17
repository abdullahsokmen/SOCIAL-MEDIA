package com.abdullah.mapper;

import com.abdullah.dto.request.NewCreateUserRequestDto;
import com.abdullah.dto.request.UpdateEmailOrUsernameRequestDto;
import com.abdullah.dto.request.UserProfileUpdateRequestDto;
import com.abdullah.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE= Mappers.getMapper(IUserMapper.class);

    UserProfile toUserProfile(final NewCreateUserRequestDto dto);

    UpdateEmailOrUsernameRequestDto toUpdateEmailOrUsernameRequestDto(final UserProfileUpdateRequestDto dto);

}
