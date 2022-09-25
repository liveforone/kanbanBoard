package kanbanBoard.kanbanBoard.backlog.repository;

import kanbanBoard.kanbanBoard.backlog.domain.BackLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BackLogRepository extends JpaRepository<BackLog, Long> {

    BackLog findOneById(Long id);

    @Modifying
    @Query("update BackLog b set b.emergency = b.emergency + 1 where b.id = :id")
    void updateEmergency(@Param("id") Long id);
}
