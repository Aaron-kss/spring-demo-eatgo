package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.User;
import com.sskim.eatgo.domain.UserExistedException;
import com.sskim.eatgo.domain.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(String email, String name, String password) {

        Optional<User> existed = userRepository.findByEmail(email);
        if(existed.isPresent()) {
            throw new UserExistedException(email);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(password);

        User user = User.builder()
                .email(email)
                .name(name)
                .password(encodePassword)
                .level(1L)
                .build();

        return userRepository.save(user);
    }
}