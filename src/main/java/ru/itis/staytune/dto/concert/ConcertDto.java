package ru.itis.staytune.dto.concert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.staytune.dto.user.UserDto;
import ru.itis.staytune.models.concert.Concert;
import ru.itis.staytune.models.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDto {
    private Long id;
    private String name;
    private String location;
    private Double latitude;
    private Double longitude;
    private LocalDate date;
    private String about;
    private String singer;
    private String singerUrl;
    private Integer price;
    private String imageUrl;
    private List<UserDto> subscribers;
    private List<UserDto> userLiked;

    public static ConcertDto from(Concert concert) {
        return ConcertDto.builder()
                .id(concert.getId())
                .name(concert.getName())
                .location(concert.getLocation())
                .latitude(concert.getLatitude())
                .longitude(concert.getLongitude())
                .date(concert.getDate())
                .about(concert.getAbout())
                .singer(concert.getSinger())
                .singerUrl(concert.getSingerUrl())
                .price(concert.getPrice())
                .imageUrl(concert.getImageUrl())
                .subscribers(concert.getUsers().stream().map(UserDto::from).collect(Collectors.toList()))
                .userLiked(concert.getUserLiked().stream().map(UserDto::from).collect(Collectors.toList()))
                .build();
    }

    public static List<ConcertDto> from(List<Concert> concerts) {
        return concerts.stream().map(ConcertDto::from).collect(Collectors.toList());
    }
}
