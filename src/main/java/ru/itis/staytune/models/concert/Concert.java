package ru.itis.staytune.models.concert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.staytune.models.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "concert")
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String name;

    private LocalDate date;

    private String location;

    private Double latitude;

    private Double longitude;

    @Column(length = 5000)
    private String about;

    private String singer;

    private String singerUrl;

    private Integer price;

    private String imageUrl;

    @ManyToMany
    @JoinTable(name = "concert_user",
            joinColumns = {@JoinColumn(name = "concert_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "favorite_user",
            joinColumns = {@JoinColumn(name = "concert_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> userLiked;
}
