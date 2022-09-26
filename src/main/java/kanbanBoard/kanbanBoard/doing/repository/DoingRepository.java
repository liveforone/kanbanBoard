package kanbanBoard.kanbanBoard.doing.repository;

import kanbanBoard.kanbanBoard.doing.domain.Doing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoingRepository extends JpaRepository<Doing, Long> {

    Doing findOneById(Long id);
}
