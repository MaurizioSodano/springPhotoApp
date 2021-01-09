package com.soulsoftware.blog.photoapp.api.users.service;

import com.soulsoftware.blog.photoapp.api.users.data.UserEntity;
import com.soulsoftware.blog.photoapp.api.users.data.UsersRepository;
import com.soulsoftware.blog.photoapp.api.users.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService{

    UsersRepository usersRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.usersRepository=usersRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity=modelMapper.map(userDetails, UserEntity.class);

        //Temporary
        //userEntity.setEncryptedPassword("test");

        //Save
        usersRepository.save(userEntity);

        UserDto returnValue=modelMapper.map(userEntity,UserDto.class);

        return returnValue;
    }
}
