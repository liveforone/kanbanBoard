package kanbanBoard.kanbanBoard.testing.controller;

import kanbanBoard.kanbanBoard.doing.service.DoingService;
import kanbanBoard.kanbanBoard.testing.domain.Testing;
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
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestingController {

    private final TestingService testingService;
    private final DoingService doingService;

    @GetMapping("/testing")
    public ResponseEntity<Page<Testing>> testingHome(
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "emergency", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            }) Pageable pageable
    ) {
        Page<Testing> testingList = testingService.getTestingList(pageable);

        return new ResponseEntity<>(testingList, HttpStatus.OK);
    }

    @PostMapping("/testing/post/{id}")
    public ResponseEntity<?> postTesting(
            @PathVariable("id") Long id
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/testing"));

        testingService.saveTesting(id);
        doingService.deleteDoing(id);
        log.info("testing save Success!! Plus Doing delete Success!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    /*
    현재 user 뷰로 보내줌.
    뷰에서 worker와 동일한 유저인지 판별
     */
    @GetMapping("/testing/{id}")
    public ResponseEntity<Map<String, Object>> testingDetail(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        Map<String, Object> map = new HashMap<>();
        String user = principal.getName();
        Testing testing = testingService.getOneTesting(id);

        map.put("user", user);
        map.put("testing", testing);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
