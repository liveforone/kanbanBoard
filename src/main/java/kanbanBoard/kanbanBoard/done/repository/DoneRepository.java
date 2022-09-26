package kanbanBoard.kanbanBoard.done.repository;

import kanbanBoard.kanbanBoard.done.domain.Done;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoneRepository extends JpaRepository<Done, Long> {
    Done findOneById(Long id);
}
