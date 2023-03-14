package com.abdullah.mapper;

import com.abdullah.dto.request.RegisterRequestDto;
import com.abdullah.dto.response.RegisterResponseDto;
import com.abdullah.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE= Mappers.getMapper(IAuthMapper.class);

    Auth toAuth(final RegisterRequestDto dto);

    RegisterResponseDto toRegisterResponseDto(final Auth auth);
}