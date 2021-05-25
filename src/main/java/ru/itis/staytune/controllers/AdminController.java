package ru.itis.staytune.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.staytune.dto.user.UserDto;
import ru.itis.staytune.services.concert.ConcertService;
import ru.itis.staytune.services.user.UserService;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConcertService concertService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<List<UserDto>> getUsers() {
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/admin/ban")
    public ResponseEntity<UserDto> banUser(@RequestBody UserDto userDto) {
        return new ResponseEntity(userService.ban(userDto), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/admin/unban")
    public ResponseEntity<UserDto> unbanUser(@RequestBody UserDto userDto) {
        return new ResponseEntity(userService.unban(userDto), HttpStatus.OK);
    }
}
