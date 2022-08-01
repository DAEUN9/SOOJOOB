package soojoop.daeun.test.badge.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import soojoop.daeun.test.user.domain.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String badgeName;

    private String badgeUrl;

    private String detail;
}
