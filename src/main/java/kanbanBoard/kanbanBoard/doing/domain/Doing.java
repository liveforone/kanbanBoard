package kanbanBoard.kanbanBoard.doing.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Doing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String writer;
    private String worker;

    @Column(columnDefinition = "integer default 0")
    private int emergency;
    private LocalDate startedDate;

    @Builder
    public Doing(Long id, String title, String content, String writer, String worker, int emergency, LocalDate startedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.worker = worker;
        this.emergency = emergency;
        this.startedDate = startedDate;
    }
}
