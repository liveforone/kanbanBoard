package kanbanBoard.kanbanBoard.done.controller;

import kanbanBoard.kanbanBoard.doing.service.DoingService;
import kanbanBoard.kanbanBoard.done.domain.Done;
import kanbanBoard.kanbanBoard.done.service.DoneService;
import kanbanBoard.kanbanBoard.testing.service.TestingService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DoneController {

    private final DoneService doneService;
    private final DoingService doingService;
    private final TestingService testingService;

    @GetMapping("/done")
    public ResponseEntity<Page<Done>> doneHome(
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "emergency", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            }) Pageable pageable
    ) {
        Page<Done> doneList = doneService.getDoneList(pageable);

        return new ResponseEntity<>(doneList, HttpStatus.OK);
    }

    /*
    done 을 save하는 경우 두가지 조건이 생긴다.
    1. doing에서 save 하는 경우
    2. testing에서 save 하는 경우
    이에 맞추어서 api를 수정하여 save 처리한다.
     */
    @PostMapping("/done/doing-post/{id}")  //-> doing 에서 done
    public ResponseEntity<?> doneDoingPost(@PathVariable("id") Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/done"));

        doneService.saveDoneWithDoing(id);
        doingService.deleteDoing(id);
        log.info("done save success!! Plus doing delete success!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/done/testing-post/{id}")  //-> testing 에서 done
    public ResponseEntity<?> doneTestingPost(@PathVariable("id") Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/done"));

        doneService.saveDoneWithTesting(id);
        testingService.deleteTesting(id);
        log.info("done save success!! Plus testing delete success!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/done/{id}")
    public ResponseEntity<Done> getOneDone(@PathVariable("id") Long id) {
        Done done = doneService.getOneDone(id);

        return new ResponseEntity<>(done, HttpStatus.OK);
    }
}
