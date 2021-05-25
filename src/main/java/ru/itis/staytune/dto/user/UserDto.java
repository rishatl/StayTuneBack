package ru.itis.staytune.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.itis.staytune.dto.concert.AnotherConcertDto;
import ru.itis.staytune.dto.concert.ConcertDto;
import ru.itis.staytune.dto.concert.FavoriteConcertDto;
import ru.itis.staytune.models.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String role;
    private String state;
    private String city;
    private String telegram;
    private String image;
    private Boolean isFriend;
    private List<FavoriteConcertDto> favorites;


    private static final String baseUrl = "http://localhost:8080/image/";

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getUserRole().toString())
                .state(user.getUserState().toString())
                .city(user.getCity())
                .telegram(user.getTelegram())
                .image(baseUrl + user.getImage())
                .isFriend(user.getIsFriend())
                .favorites(user.getFavorites().stream().map(FavoriteConcertDto::from).collect(Collectors.toList()))
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }
}
