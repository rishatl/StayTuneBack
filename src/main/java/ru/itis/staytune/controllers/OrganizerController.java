package ru.itis.staytune.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.staytune.dto.concert.AnotherConcertDto;
import ru.itis.staytune.dto.concert.ConcertDto;
import ru.itis.staytune.services.concert.ConcertService;

@Controller
public class OrganizerController {

    @Autowired
    private ConcertService concertService;

    @PreAuthorize("hasAuthority('ORGANIZER')")
    @PostMapping("/organizer/addConcert")
    public ResponseEntity<AnotherConcertDto> addConcert(@RequestBody ConcertDto concertDto) {
        return new ResponseEntity(concertService.addNewConcert(concertDto), HttpStatus.OK);
    }
}
