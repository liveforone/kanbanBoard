package kanbanBoard.kanbanBoard.done.service;

import kanbanBoard.kanbanBoard.doing.domain.Doing;
import kanbanBoard.kanbanBoard.doing.repository.DoingRepository;
import kanbanBoard.kanbanBoard.done.domain.Done;
import kanbanBoard.kanbanBoard.done.dto.DoneDto;
import kanbanBoard.kanbanBoard.done.repository.DoneRepository;
import kanbanBoard.kanbanBoard.testing.domain.Testing;
import kanbanBoard.kanbanBoard.testing.repository.TestingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoneService {

    private final DoneRepository doneRepository;
    private final DoingRepository doingRepository;
    private final TestingRepository testingRepository;

    @Transactional(readOnly = true)
    public Page<Done> getDoneList(Pageable pageable) {
        return doneRepository.findAll(pageable);
    }

    //== doing에서 done 하는 경우 ==//
    @Transactional
    public void saveDoneWithDoing(Long id) {
        Doing doing = doingRepository.findOneById(id);
        DoneDto doneDto = DoneDto.builder()
                .title(doing.getTitle())
                .content(doing.getContent())
                .writer(doing.getWriter())
                .worker(doing.getWorker())
                .emergency(doing.getEmergency())
                .startedDate(doing.getStartedDate())
                .build();

        doneRepository.save(doneDto.toEntity());
    }

    //== testing에서 done 하는 경우 ==//
    @Transactional
    public void saveDoneWithTesting(Long id) {
        Testing testing = testingRepository.findOneById(id);
        DoneDto doneDto = DoneDto.builder()
                .title(testing.getTitle())
                .content(testing.getContent())
                .writer(testing.getWriter())
                .worker(testing.getWorker())
                .emergency(testing.getEmergency())
                .startedDate(testing.getStartedDate())
                .build();

        doneRepository.save(doneDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Done getOneDone(Long id) {
        return doneRepository.findOneById(id);
    }
}
