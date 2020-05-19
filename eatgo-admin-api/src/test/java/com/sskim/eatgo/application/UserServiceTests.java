package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.User;
import com.sskim.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
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
    public void getUsers() {

        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .email("test@test.com")
                .name("테스터")
                .level(100L)
                .build());

        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();
        User user = users.get(0);

        assertThat(user.getName(), is("테스터"));
    }

    @Test
    public void addUser(){

        String email = "admin@exmaple.com";
        String name = "Administrator";

        User mockUser = User.builder()
                .email(email)
                .name(name)
                .build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(email, name);

        assertThat(user.getEmail(), is(user.getEmail()));
        assertThat(user.getName(), is(user.getName()));
    }

    @Test
    public void update(){

        Long id = 1004L;
        String email = "admin@exmaple.com";
        String name = "Superman";
        Long level = 100L;

        User mockUser = User.builder()
                .email(email)
                .name("Administrator")
                .level(1L)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.updateUser(id, email, name, level);

        verify(userRepository).findById(eq(id));

        assertThat(user.getName(), is("Superman"));
        assertThat(user.isAdmin(), is(true));
    }

    @Test
    public void deactivateUser(){

        Long id = 1004L;

        User mockUser = User.builder()
                .id(id)
                .email("admin@exmaple.com")
                .name("Administrator")
                .level(100L)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.deactivateUser(id);

        verify(userRepository).findById(1004L);

        assertThat(user.isActive(), is(false));
        assertThat(user.isAdmin(), is(false));
    }
}