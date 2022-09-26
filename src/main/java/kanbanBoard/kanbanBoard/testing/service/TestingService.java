package kanbanBoard.kanbanBoard.testing.service;

import kanbanBoard.kanbanBoard.doing.domain.Doing;
import kanbanBoard.kanbanBoard.doing.repository.DoingRepository;
import kanbanBoard.kanbanBoard.testing.domain.Testing;
import kanbanBoard.kanbanBoard.testing.dto.TestingDto;
import kanbanBoard.kanbanBoard.testing.repository.TestingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestingService {

    private final TestingRepository testingRepository;
    private final DoingRepository doingRepository;

    @Transactional(readOnly = true)
    public Page<Testing> getTestingList(Pageable pageable) {
        return testingRepository.findAll(pageable);
    }

    @Transactional
    public void saveTesting(Long id) {
        Doing doing = doingRepository.findOneById(id);
        TestingDto testingDto = TestingDto.builder()
                .title(doing.getTitle())
                .content(doing.getContent())
                .writer(doing.getWriter())
                .worker(doing.getWorker())
                .emergency(doing.getEmergency())
                .startedDate(doing.getStartedDate())
                .build();

        testingRepository.save(testingDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Testing getOneTesting(Long id) {
        return testingRepository.findOneById(id);
    }

    @Transactional
    public void deleteTesting(Long id) {
        testingRepository.deleteById(id);
    }
}
