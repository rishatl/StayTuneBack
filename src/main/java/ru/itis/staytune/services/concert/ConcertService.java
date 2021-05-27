package ru.itis.staytune.services.concert;

import ru.itis.staytune.dto.concert.AnotherConcertDto;
import ru.itis.staytune.dto.concert.ConcertDto;
import ru.itis.staytune.models.concert.Concert;

import java.util.List;

public interface ConcertService {
    Concert getById(Long id);

    List<ConcertDto> getConcertsByUserId(Long userId);

    List<ConcertDto> getAllConcerts();

    AnotherConcertDto addNewConcert(ConcertDto concertDto);

    ConcertDto getConcertByConcertId(Long concertId);

    Void createConcerts();
}
