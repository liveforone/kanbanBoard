package kanbanBoard.kanbanBoard.backlog.controller;

import kanbanBoard.kanbanBoard.backlog.domain.BackLog;
import kanbanBoard.kanbanBoard.backlog.dto.BackLogDto;
import kanbanBoard.kanbanBoard.backlog.service.BackLogService;
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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BackLogController {

    private final BackLogService backLogService;

    @GetMapping("/backlog")
    public ResponseEntity<Page<BackLog>> backLogHome(
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "emergency", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            }) Pageable pageable
    ) {
        Page<BackLog> backLogList = backLogService.getBackLogList(pageable);

        return new ResponseEntity<>(backLogList, HttpStatus.OK);
    }

    @GetMapping("/backlog/post")
    public ResponseEntity<?> postPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/backlog/post")
    public ResponseEntity<?> backlogPost(
            @RequestBody BackLogDto backLogDto,
            Principal principal
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/backlog"));
        String writer = principal.getName();

        backLogService.saveBackLog(backLogDto, writer);
        log.info("backlog save success!!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    /*
    현재 유저를 user로 보냄
    따라서 뷰에서는 해당 user와 writer를 비교후 일치한다면 수정 버튼을 보여주면됨
     */
    @GetMapping("/backlog/{id}")
    public ResponseEntity<Map<String, Object>> backLogDetail(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        String user = principal.getName();
        BackLog backLog = backLogService.getOne(id);

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("backLog", backLog);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //== emergency update ==//
    @PostMapping("/backlog/emergency/{id}")
    public ResponseEntity<?> updateEmergency(@PathVariable("id") Long id) {
        backLogService.updateEmergency(id);
        log.info("update emergency Success!!");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    뷰에서 user == writer 판별이 끝난 상태
     */
    @GetMapping("/backlog/edit/{id}")
    public ResponseEntity<BackLog> editPage(@PathVariable("id") Long id) {
        BackLog backLog = backLogService.getOne(id);

        return new ResponseEntity<>(backLog, HttpStatus.OK);
    }

    @PostMapping("/backlog/edit/{id}")
    public ResponseEntity<?> backlogEdit(
            @PathVariable("id") Long id,
            @RequestBody BackLogDto backLogDto
    ) {
        backLogService.updateOne(id, backLogDto);
        log.info("backlog update Success!!");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
