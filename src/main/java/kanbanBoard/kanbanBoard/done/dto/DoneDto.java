package kanbanBoard.kanbanBoard.done.dto;

import kanbanBoard.kanbanBoard.done.domain.Done;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DoneDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String worker;
    private int emergency;
    private LocalDate startedDate;
    private LocalDate endedDate;

    public Done toEntity() {
        return Done.builder()
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
    public DoneDto(Long id, String title, String content, String writer, String worker, int emergency, LocalDate startedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.worker = worker;
        this.emergency = emergency;
        this.startedDate = startedDate;
    }
}
