package kanbanBoard.kanbanBoard.testing.dto;

import kanbanBoard.kanbanBoard.testing.domain.Testing;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TestingDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String worker;
    private int emergency;
    private LocalDate startedDate;

    public Testing toEntity() {
        return Testing.builder()
                .id(id)
                .title(title)
                .content(content)
                .writer(writer)
                .worker(worker)
                .emergency(emergency)
                .startedDate(startedDate)
                .build();
    }

    @Builder
    public TestingDto(Long id, String title, String content, String writer, String worker, int emergency, LocalDate startedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.worker = worker;
        this.emergency = emergency;
        this.startedDate = startedDate;
    }
}
