package ru.itis.staytune.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.itis.staytune.dto.concert.ConcertDto;
import ru.itis.staytune.models.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnotherUserDto {
    private Long id;
    private String email;
    private String username;
    private Boolean isFriend;
    private String image;

    private static final String baseUrl = "http://localhost:8080/image/";

    public static AnotherUserDto fromFriends(User user) {
        return AnotherUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .isFriend(user.getIsFriend())
                .image(baseUrl + user.getImage())
                .build();
    }

    public static List<AnotherUserDto> fromFriends(List<User> users) {
        return users.stream().map(AnotherUserDto::fromFriends).collect(Collectors.toList());
    }
}
