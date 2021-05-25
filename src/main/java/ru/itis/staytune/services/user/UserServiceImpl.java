package ru.itis.staytune.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.staytune.dto.concert.FavoriteConcertDto;
import ru.itis.staytune.dto.user.AnotherUserDto;
import ru.itis.staytune.dto.user.UserDto;
import ru.itis.staytune.models.concert.Concert;
import ru.itis.staytune.models.user.User;
import ru.itis.staytune.reposetories.concert.ConcertRepository;
import ru.itis.staytune.reposetories.user.UserRepository;
import ru.itis.staytune.services.ImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AnotherUserDto> getAllUsers() {
        return AnotherUserDto.fromFriends(userRepository.findAll());
    }

    @Override
    public List<AnotherUserDto> getFriends() {
        List<AnotherUserDto> users = getAllUsers();
        List<AnotherUserDto> friends = new ArrayList<>();
        for (AnotherUserDto user1 : users) {
            if (user1.getIsFriend()) {
                friends.add(user1);
            }
        }
        if (!friends.isEmpty()) {
            return friends;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public UserDto getById(Long id) {
        return UserDto.from(userRepository.getOne(id));
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }

    @Override
    public boolean existUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.isPresent();
    }

    @Override
    public String generateSecurePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    @Override
    public UserDto signUpAndSave(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(generateSecurePassword(userDto.getPassword()))
                .isDeleted(false)
                .isFriend(false)
                .favorites(new ArrayList<>())
                .userRole(User.Role.USER)
                .userState(User.State.ACTIVE)
                .build();
        userRepository.save(user);

        return UserDto.from(user);
    }

    @Override
    public UserDto signInAndCheck(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user != null) {
            if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                return UserDto.from(user);
            }
        }
        return null;
    }

    @Override
    public UserDto ban(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setUserState(User.State.BANNED);
        userRepository.save(user);
        return UserDto.from(userRepository.save(user));
    }

    @Override
    public UserDto unban(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setUserState(User.State.ACTIVE);
        userRepository.save(user);
        return UserDto.from(userRepository.save(user));
    }

    @Override
    public List<AnotherUserDto> getUsersByConcertId(Long userId, Long concertId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new IllegalStateException("Concert not found"));
        for (User user1 : concert.getUsers()) {
            if (user.getFriends().contains(user1)) {
                user1.setIsFriend(true);
            } else {
                user1.setIsFriend(false);
            }
            userRepository.save(user1);
        }
        return AnotherUserDto.fromFriends(userRepository.findByConcertId(concertId));
    }

    @Override
    public List<AnotherUserDto> subscribeToEvent(Long userId, Long concertId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new IllegalStateException("Concert not found"));
        if (!user.getConcerts().contains(concert)) {
            user.getConcerts().add(concert);
            concert.getUsers().add(user);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("Concert is exist");
        }
        return getUsersByConcertId(userId, concertId);
    }

    @Override
    public List<AnotherUserDto> unsubscribeFromEvent(Long userId, Long concertId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new IllegalStateException("Concert not found"));
        user.getConcerts().remove(concert);
        concert.getUsers().remove(user);
        userRepository.save(user);
        return new ArrayList<>();
    }

    @Override
    public Void removeFromFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalStateException("Concert not found"));
        if (user.getFriends().contains(friend)) {
            user.getFriends().remove(friend);
            friend.getFriends().remove(user);
            friend.setIsFriend(false);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("Friend doesn't exist");
        }
        return null;
    }

    @Override
    public Void addAsFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalStateException("Concert not found"));
        if (!user.getFriends().contains(friend)) {
            user.getFriends().add(friend);
            friend.setIsFriend(true);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("Friend is exist");
        }
        return null;
    }

    @Override
    public List<FavoriteConcertDto> getFavoritesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<FavoriteConcertDto> favorites = FavoriteConcertDto.from(user.getFavorites());
        if (!favorites.isEmpty()) {
            return favorites;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Void addFavoriteConcert(Long userId, Long concertId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new IllegalStateException("Concert not found"));
        if (!user.getFavorites().contains(concert)) {
            user.getFavorites().add(concert);
            userRepository.save(user);
            concert.getUserLiked().add(user);
            concertRepository.save(concert);
        } else {
            throw new IllegalStateException("Favorite concert is exist");
        }
        return null;
    }

    @Override
    public Void removeFavoriteConcert(Long userId, Long concertId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new IllegalStateException("Concert not found"));
        if (user.getFavorites().contains(concert)) {
            user.getFavorites().remove(concert);
            userRepository.save(user);
            concert.getUserLiked().remove(user);
            concertRepository.save(concert);
        } else {
            throw new IllegalStateException("Favorite concert doesn't exist");
        }
        return null;
    }

    @Override
    public Void editProfile(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        List<AnotherUserDto> users = getAllUsers();
        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            for (AnotherUserDto user1 : users) {
                if (user1.getEmail().equals(userDto.getEmail())) {
                    throw new IllegalStateException("User with this email is exist");
                }
            }
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getCity() != null && !userDto.getCity().isEmpty()) {
            user.setCity(userDto.getCity());
        }
        if (userDto.getTelegram() != null && !userDto.getTelegram().isEmpty()) {
            user.setTelegram(userDto.getTelegram());
        }
        userRepository.save(user);
        return null;
    }

    @Override
    public String changePhoto(long userId, MultipartFile multipartFile) throws IOException {
        String fileName = imageService.saveImage(multipartFile);
        User user = userRepository.getOne(userId);
        user.setImage(fileName);
        userRepository.save(user);
        return fileName;
    }
}
