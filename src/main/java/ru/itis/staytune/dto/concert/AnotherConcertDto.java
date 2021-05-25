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
public class AnotherConcertDto {
    private Long id;
    private String name;
    private String location;


    public static AnotherConcertDto from(Concert concert) {
        return AnotherConcertDto.builder()
                .id(concert.getId())
                .name(concert.getName())
                .location(concert.getLocation())
                .build();
    }

    public static List<AnotherConcertDto> from(List<Concert> concerts) {
        return concerts.stream().map(AnotherConcertDto::from).collect(Collectors.toList());
    }
}
