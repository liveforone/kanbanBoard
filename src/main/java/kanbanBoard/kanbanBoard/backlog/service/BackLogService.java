package kanbanBoard.kanbanBoard.backlog.service;

import kanbanBoard.kanbanBoard.backlog.domain.BackLog;
import kanbanBoard.kanbanBoard.backlog.dto.BackLogDto;
import kanbanBoard.kanbanBoard.backlog.repository.BackLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BackLogService {

    private final BackLogRepository backLogRepository;

    @Transactional(readOnly = true)
    public Page<BackLog> getBackLogList(Pageable pageable) {
        return backLogRepository.findAll(pageable);
    }

    @Transactional
    public void saveBackLog(BackLogDto backLogDto, String writer) {
        backLogDto.setWriter(writer);
        backLogRepository.save(backLogDto.toEntity());
    }

    @Transactional(readOnly = true)
    public BackLog getOne(Long id) {
        return backLogRepository.findOneById(id);
    }

    @Transactional
    public void updateEmergency(Long id) {
        backLogRepository.updateEmergency(id);
    }

    @Transactional
    public void updateOne(Long id, BackLogDto backLogDto) {
        BackLog backLog = backLogRepository.findOneById(id);

        if (backLogDto.getTitle() == null) {  //컨텐츠만 업데이트
            backLogDto.setId(backLog.getId());
            backLogDto.setTitle(backLog.getTitle());
            backLogDto.setEmergency(backLog.getEmergency());
            backLogDto.setWriter(backLog.getWriter());
        } else if (backLogDto.getContent() == null) {  //제목만 업데이트
            backLogDto.setId(backLog.getId());
            backLogDto.setContent(backLog.getContent());
            backLogDto.setEmergency(backLog.getEmergency());
            backLogDto.setWriter(backLog.getWriter());
        } else {  //모두 업데이트 하는 경우
            backLogDto.setId(backLog.getId());
            backLogDto.setEmergency(backLog.getEmergency());
            backLogDto.setWriter(backLog.getWriter());
        }

        backLogRepository.save(backLogDto.toEntity());
    }
}
