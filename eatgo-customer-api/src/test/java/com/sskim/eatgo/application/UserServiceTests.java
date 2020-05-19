package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.User;
import com.sskim.eatgo.domain.UserExistedException;
import com.sskim.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void registerUser(){

        String email = "tester@example.com";
        String name = "tester";
        String password = "1234";

        userService.registerUser(email, name, password);

        verify(userRepository).save(any());
    }

    @Test(expected= UserExistedException.class)
    public void registerUserWithExistedEmail(){

        String email = "tester@example.com";
        String name = "tester";
        String password = "1234";

        User user = User.builder().build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        userService.registerUser(email, name, password);

        verify(userRepository, never()).save(any());
    }
}