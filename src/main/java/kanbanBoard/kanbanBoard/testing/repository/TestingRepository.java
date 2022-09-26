package kanbanBoard.kanbanBoard.testing.repository;

import kanbanBoard.kanbanBoard.testing.domain.Testing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestingRepository extends JpaRepository<Testing, Long> {
    Testing findOneById(Long id);
}
