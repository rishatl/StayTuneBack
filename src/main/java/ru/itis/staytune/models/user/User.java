package ru.itis.staytune.models.user;

import lombok.*;
import ru.itis.staytune.models.concert.Concert;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    private Boolean isDeleted;

    private Boolean isFriend;

    private String city;

    private String image;

    private String telegram;

    @Column(name = "user_state")
    @Enumerated(value = EnumType.STRING)
    private State userState;

    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private Role userRole;

    public enum State {
        ACTIVE, BANNED
    }

    public enum Role {
        USER, ADMIN, ORGANIZER
    }

    public boolean isActive() {
        return this.userState == State.ACTIVE;
    }

    public boolean isBanned() {
        return this.userState == State.BANNED;
    }

    public boolean isAdmin() {
        return this.userRole == Role.ADMIN;
    }

    @ToString.Exclude
    @ManyToMany(mappedBy = "users")
    private List<Concert> concerts;

    @ToString.Exclude
    @ManyToMany(mappedBy = "userLiked")
    private List<Concert> favorites;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "user_friend",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id", referencedColumnName = "id")})
    private List<User> friends;
}
