package kanbanBoard.kanbanBoard.doing.service;

import kanbanBoard.kanbanBoard.backlog.domain.BackLog;
import kanbanBoard.kanbanBoard.backlog.repository.BackLogRepository;
import kanbanBoard.kanbanBoard.doing.domain.Doing;
import kanbanBoard.kanbanBoard.doing.dto.DoingDto;
import kanbanBoard.kanbanBoard.doing.repository.DoingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoingService {

    private final DoingRepository doingRepository;
    private final BackLogRepository backLogRepository;

    @Transactional(readOnly = true)
    public Page<Doing> getDoingList(Pageable pageable) {
        return doingRepository.findAll(pageable);
    }

    //==  save Doing ==//
    @Transactional
    public void saveDoing(Long id, String worker) {
        BackLog backLog = backLogRepository.findOneById(id);

        DoingDto doingDto = DoingDto.builder()
                .title(backLog.getTitle())
                .content(backLog.getContent())
                .writer(backLog.getWriter())
                .worker(worker)
                .emergency(backLog.getEmergency())
                .startedDate(backLog.getStartedDate())
                .build();
        doingRepository.save(doingDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Doing getDoingOne(Long id) {
        return doingRepository.findOneById(id);
    }

    @Transactional
    public void deleteDoing(Long id) {
        doingRepository.deleteById(id);
    }
}
