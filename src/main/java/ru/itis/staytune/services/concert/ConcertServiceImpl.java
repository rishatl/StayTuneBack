package ru.itis.staytune.services.concert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import ru.itis.staytune.dto.concert.AnotherConcertDto;
import ru.itis.staytune.dto.concert.ConcertDto;
import ru.itis.staytune.models.concert.Concert;
import ru.itis.staytune.reposetories.concert.ConcertRepository;

import java.time.LocalDate;
import java.util.Date;
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
    public Void createConcerts() {
        Concert concert1 = Concert.builder()
                .name("KARMAGEDON")
                .date(LocalDate.ofEpochDay(19002))
                .location("Riga")
                .latitude(56.9549973)
                .longitude(24.158839)
                .about("Kizaru ???? ?????????? ???????????????????? ?????? ???????????? ?????????????????????? ?????????? ?????????????????????????? ??????????????????????: ???????????????? ?????????????????? ???????????????? ?? ?????????????????? ?????????????????? ???? ??????????-???????????????????? ?? ?????? ???????? ?????????? ???????????????????????????????????? ??????????????. ???? ???????? ?????????????????????? ???????????????? ???? ????????????, Kizaru ???????????????????? ???????????????????? ?????? ???? ?????????????? ??????????, ???????? ?? ???? ?????? ???????????? ???????????????? ????????????. ?????????????????? ?? ???????????? ???????????????? ???? ?????????????? ????????????????, ???????????? ???????????? ???? ??Karmageddon?? ?????????????????? ?????????????????? ?????????????? ?? ???????????????????????????? ???????????? ?????? ???????????????? ????????-???????? ?? ?????????????????????? ?????????????????????????? ????????????, ???????????????? ?????????????????? ???? ?????? ???????????????????? ??????????. ?? ???????????????? ???????????????????????? ?????????? ??? ???????????????????????? ???????????? Smokepurpp ?? Black Kray.")
                .singer("Kizaru")
                .singerUrl("https://music.apple.com/ru/artist/kizaru/503131736")
                .price(1999)
                .imageUrl("https://upload.wikimedia.org/wikipedia/ru/3/39/Karmageddon.jpg")
                .build();
        concertRepository.save(concert1);
        Concert concert2 = Concert.builder()
                .name("Big Baby Tape")
                .date(LocalDate.ofEpochDay(19001))
                .location("Kazan")
                .latitude(55.82111)
                .longitude(49.16083)
                .about("?? ?????????????? ???????????? ?????????????????? ?????????????? ??Dragonborn?? 18-???????????? ?????????? Big Baby Tape ?????? ???????????????????????? ?????????? ?????????????????????? ?????????????????????? ???????????????? ?? ?????????????????? ???????????????? ?? ????????????, ?????????????????? ?????? ?????????? ???????? ?? ?????????????? ??????????. ???? ?????????? ?????????????????? ???? ???????????????????? ?? ???????????????????? (?????? ???? ??????????????, ?????? ?? ???? ???????????????????? ????????????), ?????????????????????????????????? ?????????????????? ?? ???????????? ????????????????????. ?????????? ?????????????? ?????????????????????????? Gucci Mane ?? MF Doom ?????????? ?? ?????????????????? ???????????????????? ?????????????????????? ?? ?????????? ???????????????? ???? ?????????? ??????????????????????. ?? ???????????? ?????????? ???????????????????? ??????????, Boulevard Depo, White Punk ?? ???????????? ??????????????????????????????.")
                .singer("DRAGONBORN")
                .singerUrl("https://music.apple.com/ru/artist/big-baby-tape/1356097231")
                .price(2499)
                .imageUrl("https://upload.wikimedia.org/wikipedia/ru/b/b3/ARGUMENTS_%26_FACTS.jpg")
                .build();
        concertRepository.save(concert2);
        Concert concert3 = Concert.builder()
                .name("Sexy Drill")
                .date(LocalDate.ofEpochDay(19000))
                .location("Moscow")
                .latitude(55.78111)
                .longitude(37.62639)
                .about("?????????????????????????? ???????????? ?? ???????????? ???????????????????? ???????????? ????????????????????????. ?????????????????????? ?????????????????????? ???????????????????? ?????????????? ??????????????????.")
                .singer("OG Buda")
                .singerUrl("https://music.apple.com/ru/artist/og-buda/1382321701")
                .price(1199)
                .imageUrl("https://zapoem.com/images/covers/23535df9.a.15237031-1.jpg")
                .build();
        concertRepository.save(concert3);
        return null;
    }

    @Override
    public ConcertDto getConcertByConcertId(Long concertId) {
        return ConcertDto.from(concertRepository.getOne(concertId));
    }
}
