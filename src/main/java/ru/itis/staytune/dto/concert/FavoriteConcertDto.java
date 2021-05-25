package ru.itis.staytune.dto.concert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.staytune.models.concert.Concert;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteConcertDto {
    private Long id;
    private String name;
    private String location;
    private String imageUrl;

    public static FavoriteConcertDto from(Concert concert) {
        return FavoriteConcertDto.builder()
                .id(concert.getId())
                .name(concert.getName())
                .location(concert.getLocation())
                .imageUrl(concert.getImageUrl())
                .build();
    }

    public static List<FavoriteConcertDto> from(List<Concert> concerts) {
        return concerts.stream().map(FavoriteConcertDto::from).collect(Collectors.toList());
    }
}
