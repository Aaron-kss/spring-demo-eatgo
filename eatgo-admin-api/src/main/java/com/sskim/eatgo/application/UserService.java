package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.User;
import com.sskim.eatgo.domain.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        return Optional.ofNullable(users).orElse(Collections.emptyList());
    }

    public User addUser(String email, String name) {
        User user = User.builder()
                .email(email)
                .level(1L)
                .name(name)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, String email, String name, Long level) {

        //TODO : restaurantService 예외 처리 참고
        User user = userRepository.findById(id).orElse(null);

        user.setEmail(email);
        user.setName(name);
        user.setLevel(level);

        return user;
    }

    @Transactional
    public User deactivateUser(Long id) {

        User user = userRepository.findById(id).orElse(null);
        user.deactivate();

        return user;
    }
}