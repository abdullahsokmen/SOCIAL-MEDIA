package com.abdullah.mapper;

import com.abdullah.dto.request.NewCreateUserRequestDto;
import com.abdullah.dto.request.RegisterRequestDto;
import com.abdullah.dto.response.RegisterResponseDto;
import com.abdullah.rabbitmq.model.RegisterModel;
import com.abdullah.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE= Mappers.getMapper(IAuthMapper.class);

    Auth toAuth(final RegisterRequestDto dto);

    RegisterResponseDto toRegisterResponseDto(final Auth auth);
    @Mapping(source = "id",target = "authId")
    NewCreateUserRequestDto toNewCreateUserRequestDto(final Auth auth);
    @Mapping(source = "id",target = "authId")
    RegisterModel toRegisterModel(final Auth auth);
}
