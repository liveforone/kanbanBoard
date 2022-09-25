package kanbanBoard.kanbanBoard.user.controller;

import kanbanBoard.kanbanBoard.user.dto.UserDto;
import kanbanBoard.kanbanBoard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    //== 메인 페이지 ==//
    @GetMapping("/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 회원가입 페이지 ==//
    @GetMapping("/user/signup")
    public ResponseEntity<?> signupPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 회원가입 처리 ==//
    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/"));  //해당 경로로 리다이렉트

        userService.joinUser(userDto);
        log.info("Sign Up Success!!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    //== 로그인 페이지 ==//
    @GetMapping("/user/login")
    public ResponseEntity<?> loginPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //== 로그인 ==//
    @PostMapping("/user/login")
    public ResponseEntity<?> loginPage(
            @RequestBody UserDto userDto,
            HttpSession session
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/backlog"));

        userService.login(userDto, session);
        log.info("Login Success!!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    /*
    로그아웃은 시큐리티 단에서 이루어짐.
    /user/logout 으로 post 하면 된다.
     */

    //== 접근 거부 페이지 ==//
    @GetMapping("/user/prohibition")
    public ResponseEntity<?> prohibition() {
        return new ResponseEntity<>("접근 권한이 없습니다. 로그인하여주세요.", HttpStatus.FORBIDDEN);
    }

    //== 어드민 페이지 ==//
    @GetMapping("/admin")
    public ResponseEntity<?> admin() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
