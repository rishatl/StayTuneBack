package ru.itis.staytune.services.concert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.staytune.dto.concert.AnotherConcertDto;
import ru.itis.staytune.dto.concert.ConcertDto;
import ru.itis.staytune.models.concert.Concert;
import ru.itis.staytune.reposetories.concert.ConcertRepository;

import java.util.List;

@Service
public class ConcertServiceImpl implements ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    @Override
    public Concert getById(Long id) {
        return concertRepository.getOne(id);
    }

    @Override
    public List<ConcertDto> getConcertsByUserId(Long userId) {
        return ConcertDto.from(concertRepository.findByUserId(userId));
    }

    @Override
    public List<ConcertDto> getAllConcerts() {
        return ConcertDto.from(concertRepository.findAll());
    }

    @Override
    public AnotherConcertDto addNewConcert(ConcertDto concertDto) {
        Concert concert = Concert.builder()
                .name(concertDto.getName())
                .date(concertDto.getDate())
                .location(concertDto.getLocation())
                .latitude(concertDto.getLatitude())
                .longitude(concertDto.getLongitude())
                .about(concertDto.getAbout())
                .singer(concertDto.getSinger())
                .singerUrl(concertDto.getSingerUrl())
                .price(concertDto.getPrice())
                .imageUrl(concertDto.getImageUrl())
                .build();
        concertRepository.save(concert);

        return AnotherConcertDto.from(concert);
    }

    @Override
    public ConcertDto getConcertByConcertId(Long concertId) {
        return ConcertDto.from(concertRepository.getOne(concertId));
    }
}
