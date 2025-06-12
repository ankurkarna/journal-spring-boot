package com.karna.ankur.Journal.Config;

import com.karna.ankur.Journal.Entity.UserEntity;
import com.karna.ankur.Journal.Repository.UserRepository;
import com.karna.ankur.Journal.Service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockito.Mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        try{
        MockitoAnnotations.openMocks(this);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository
                .findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(UserEntity.builder()
                        .userName("prafulla").password("dsafd").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("prafull");
        Assertions.assertNotNull(user);
    }
}
