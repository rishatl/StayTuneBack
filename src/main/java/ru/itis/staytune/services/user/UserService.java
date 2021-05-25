package ru.itis.staytune.services.user;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.staytune.dto.concert.FavoriteConcertDto;
import ru.itis.staytune.dto.user.AnotherUserDto;
import ru.itis.staytune.dto.user.UserDto;
import ru.itis.staytune.models.user.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<AnotherUserDto> getAllUsers();

    List<AnotherUserDto> getFriends();

    UserDto getById(Long id);

    User getByEmail(String email);

    boolean existUser(String email);

    String generateSecurePassword(String password);

    User loadUserByUsername(String email);

    UserDto signUpAndSave(UserDto userDto);

    UserDto signInAndCheck(UserDto userDto);

    UserDto ban(UserDto userDto);

    UserDto unban(UserDto userDto);

    List<AnotherUserDto> getUsersByConcertId(Long userId, Long concertId);

    List<FavoriteConcertDto> getFavoritesByUserId(Long userId);

    List<AnotherUserDto> subscribeToEvent(Long userId, Long concertId);

    Void addAsFriend(Long userId, Long friendId);

    List<AnotherUserDto> unsubscribeFromEvent(Long userId, Long concertId);

    Void removeFromFriend(Long userId, Long friendId);

    Void addFavoriteConcert(Long userId, Long concertId);

    Void removeFavoriteConcert(Long userId, Long concertId);

    Void editProfile(Long userId, UserDto userDto);

    String changePhoto(long userId, MultipartFile multipartFile) throws IOException;
}
