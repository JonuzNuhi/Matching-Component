package core.matchingcomponent.controller;

import core.matchingcomponent.model.Users;
import core.matchingcomponent.model.UsersDB;
import core.matchingcomponent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/usersdb")
    public List<UsersDB> getAllDBUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/asList")
    public List<UsersDB> getUsersAsList() {
        return userService.getUsersAsList();
    }

    @PostMapping("/match-and-update")
    public void matchAndUpdate() {
        userService.matchAndUpdate();
    }

}
