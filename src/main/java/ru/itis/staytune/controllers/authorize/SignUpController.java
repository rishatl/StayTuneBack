package ru.itis.staytune.controllers.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.staytune.dto.user.UserDto;
import ru.itis.staytune.services.user.UserService;

@RestController
public class SignUpController {

    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        return new ResponseEntity(userService.signUpAndSave(userDto), HttpStatus.OK);
    }
}
