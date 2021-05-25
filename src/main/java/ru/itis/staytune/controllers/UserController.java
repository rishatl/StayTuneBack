package ru.itis.staytune.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.staytune.dto.concert.ConcertDto;
import ru.itis.staytune.dto.concert.ConcertsByUserIdDto;
import ru.itis.staytune.dto.concert.FavoriteConcertDto;
import ru.itis.staytune.dto.user.AnotherUserDto;
import ru.itis.staytune.dto.user.ImageSuccessResponse;
import ru.itis.staytune.dto.user.UserDto;
import ru.itis.staytune.security.details.UserDetailsImpl;
import ru.itis.staytune.services.user.UserService;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    private static final String baseUrl = "http://localhost:8080/image/";

    @GetMapping("/users")
    public ResponseEntity<List<AnotherUserDto>> getSubscribers() {
        return ResponseEntity.ok(userService.getFriends());
    }

    @PostMapping("/users/user")
    public ResponseEntity<UserDto> getUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.getById(userDto.getId()));
    }

    @PostMapping("/users/byConcertId")
    public ResponseEntity<List<AnotherUserDto>> getSubscribers(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ConcertDto concertDto) {
        return ResponseEntity.ok(userService.getUsersByConcertId(userDetails.getUser().getId(), concertDto.getId()));
    }

    @PostMapping("/unsub/byConcertId")
    public ResponseEntity<List<AnotherUserDto>> unsubscribeFromConcert(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ConcertDto concertDto) {
        return ResponseEntity.ok(userService.unsubscribeFromEvent(userDetails.getUser().getId(), concertDto.getId()));
    }

    @PostMapping("/sub/byConcertId")
    public ResponseEntity<List<AnotherUserDto>> subscribeForConcert(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ConcertDto concertDto) {
        return ResponseEntity.ok(userService.subscribeToEvent(userDetails.getUser().getId(), concertDto.getId()));
    }

    @PostMapping("/addFriend/byUserId")
    public ResponseEntity<Void> addFriend(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.addAsFriend(userDetails.getUser().getId(), userDto.getId()));
    }

    @PostMapping("/deleteFriend/byUserId")
    public ResponseEntity<Void> removeFriend(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.removeFromFriend(userDetails.getUser().getId(), userDto.getId()));
    }

    @PostMapping("/concerts/favorites")
    public ResponseEntity<List<FavoriteConcertDto>> getFavorites(@RequestBody ConcertsByUserIdDto concerts) {
        return ResponseEntity.ok(userService.getFavoritesByUserId(concerts.getId()));
    }

    @PostMapping("/concerts/addFavorite")
    public ResponseEntity<Void> addFavorite(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ConcertDto concertDto) {
        return ResponseEntity.ok(userService.addFavoriteConcert(userDetails.getUser().getId(), concertDto.getId()));
    }

    @PostMapping("/concerts/removeFavorite")
    public ResponseEntity<Void> removeFavorite(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ConcertDto concertDto) {
        return ResponseEntity.ok(userService.removeFavoriteConcert(userDetails.getUser().getId(), concertDto.getId()));
    }

    @PostMapping("/changePhoto")
    public ResponseEntity<ImageSuccessResponse> changePhoto(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("image_file") MultipartFile multipartFile) throws IOException {
        String imagePath = baseUrl + userService.changePhoto(userDetails.getUser().getId(), multipartFile);
        return ResponseEntity.ok(ImageSuccessResponse.builder().imagePath(imagePath).build());
    }

    @PostMapping("/users/user/edit")
    public ResponseEntity<Void> changeProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.editProfile(userDetails.getUser().getId(), userDto));
    }
}
