package soojoop.daeun.test.record.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import soojoop.daeun.test.user.domain.User;

import javax.persistence.*;
import java.awt.print.Book;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "record")
    private User user;

    @Column
    private Double totalDistance;


}

