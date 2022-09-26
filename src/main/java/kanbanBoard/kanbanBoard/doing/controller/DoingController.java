package kanbanBoard.kanbanBoard.doing.controller;

import kanbanBoard.kanbanBoard.backlog.service.BackLogService;
import kanbanBoard.kanbanBoard.doing.domain.Doing;
import kanbanBoard.kanbanBoard.doing.dto.DoingDto;
import kanbanBoard.kanbanBoard.doing.service.DoingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DoingController {

    private final DoingService doingService;
    private final BackLogService backLogService;

    @GetMapping("/doing")
    public ResponseEntity<Page<Doing>> doingHome(
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "emergency", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            }) Pageable pageable
    ) {
        Page<Doing> doingList = doingService.getDoingList(pageable);

        return new ResponseEntity<>(doingList, HttpStatus.OK);
    }

    /*
    getmapping이 필요없는 이유는 backlog detail에서 doing버튼 클릭후 바로 해당
    값들이 서버로 전달되서 doing에 save되는 것이기 때문이다.
    전체적인 backlog값과 현재 유저(worker)를 가져와서 save처리한다.
    id값을 받아오는 이유는 backlog를 삭제하기 위함이다.
    doing에 저장하거나 하는 용도로 사용하진 않는다.
     */
    @PostMapping("/doing/post/{id}")
    public ResponseEntity<?> postDoing(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/doing"));
        String worker = principal.getName();

        doingService.saveDoing(id, worker);
        backLogService.deleteBackLog(id);
        log.info("Doing Save Success!!, Plus Backlog Delete!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/doing/{id}")
    public ResponseEntity<Doing> doingDetail(@PathVariable("id") Long id) {
        Doing doing = doingService.getDoingOne(id);

        return new ResponseEntity<>(doing, HttpStatus.OK);
    }
}
