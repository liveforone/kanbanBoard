package kanbanBoard.kanbanBoard.backlog.dto;

import kanbanBoard.kanbanBoard.backlog.domain.BackLog;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BackLogDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private int emergency;
    private LocalDate startedDate;

    public BackLog toEntity() {
        return BackLog.builder()
                .id(id)
                .title(title)
                .content(content)
                .writer(writer)
                .emergency(emergency)
                .build();
    }
}
