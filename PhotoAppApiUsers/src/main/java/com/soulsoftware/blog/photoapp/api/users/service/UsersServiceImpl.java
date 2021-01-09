package com.soulsoftware.blog.photoapp.api.users.service;

import com.soulsoftware.blog.photoapp.api.users.data.UserEntity;
import com.soulsoftware.blog.photoapp.api.users.data.UsersRepository;
import com.soulsoftware.blog.photoapp.api.users.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService{

    UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository){
        this.usersRepository=usersRepository;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity=modelMapper.map(userDetails, UserEntity.class);

        //Temporary
        userEntity.setEncryptedPassword("test");

        //Save
        usersRepository.save(userEntity);

        UserDto returnValue=modelMapper.map(userEntity,UserDto.class);

        return returnValue;
    }
}
