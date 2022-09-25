package kanbanBoard.kanbanBoard.user.repository;

import kanbanBoard.kanbanBoard.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
}
