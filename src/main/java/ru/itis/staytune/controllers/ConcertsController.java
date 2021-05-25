package ru.itis.staytune.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.staytune.dto.concert.ConcertDto;
import ru.itis.staytune.dto.concert.ConcertsByUserIdDto;
import ru.itis.staytune.services.concert.ConcertService;

import java.util.List;

@Controller
public class ConcertsController {

    @Autowired
    private ConcertService concertService;

    @PostMapping("/concerts/byUserId")
    public ResponseEntity<List<ConcertDto>> getConcerts(@RequestBody ConcertsByUserIdDto concerts) {
        return ResponseEntity.ok(concertService.getConcertsByUserId(concerts.getId()));
    }

    @PostMapping("/concert/byConcertId")
    public ResponseEntity<ConcertDto> getConcert(@RequestBody ConcertDto concertDto) {
        return ResponseEntity.ok(concertService.getConcertByConcertId(concertDto.getId()));
    }

    @GetMapping("/concerts")
    public ResponseEntity<List<ConcertDto>> getListConcerts() {
        return new ResponseEntity(concertService.getAllConcerts(), HttpStatus.OK);
    }
}
