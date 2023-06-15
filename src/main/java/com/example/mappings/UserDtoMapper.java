package com.example.mappings;

import com.example.dto.LoggedUserDTO;
import com.example.entities.RegisteredUser;
import org.mapstruct.Mapper;

@Mapper
public interface UserDtoMapper {

    LoggedUserDTO fromEntity(RegisteredUser registeredUser);

}
