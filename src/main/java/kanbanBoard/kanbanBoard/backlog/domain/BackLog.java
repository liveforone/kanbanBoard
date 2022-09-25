package kanbanBoard.kanbanBoard.backlog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class BackLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String writer;

    @Column(columnDefinition = "integer default 0")
    private int emergency;  //긴급정도

    @CreatedDate
    @Column(updatable = false)
    private LocalDate startedDate;  //작성일

    @Builder
    public BackLog(Long id, String title, String content, String writer, int emergency) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.emergency = emergency;
    }
}
