package com.sskim.eatgo.interfaces;

import com.sskim.eatgo.application.UserService;
import com.sskim.eatgo.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> list(){
        return userService.getUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {

        User user = userService.addUser(resource.getEmail(), resource.getName());

        String url = "/users/" + user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<?> update(
            @PathVariable("userId") Long userId,
            @RequestBody User resource
    ) throws URISyntaxException {

        String email = resource.getEmail();
        String name = resource.getName();
        Long level = resource.getLevel();

        User user = userService.updateUser(userId, email, name, level);

        String url = "/users/" + user.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> delete(@PathVariable("userId") Long userId){

        userService.deactivateUser(userId);

        return ResponseEntity.ok().body("{}");
    }
}
