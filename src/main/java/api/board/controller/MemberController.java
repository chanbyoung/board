package api.board.controller;

import api.board.dto.member.MemberDto;
import api.board.dto.member.MemberUpdateDto;
import api.board.dto.member.SignDto;
import api.board.dto.member.SignUpDto;
import api.board.dto.security.JwtToken;
import api.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<MemberDto> signUp(@RequestBody SignUpDto signUpDto) {
        MemberDto savedMemberDto = memberService.signup(signUpDto);
        return ResponseEntity.ok(savedMemberDto);
    }
    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignDto signDto) {
        String loginId = signDto.getLoginId();
        String password = signDto.getPassword();
        JwtToken jwtToken = memberService.signIn(loginId, password);
        log.info("request loginId = {}, password = {}", loginId, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @GetMapping("/member")
    public ResponseEntity<MemberDto> getMember() {
        MemberDto member = memberService.getMember();
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PatchMapping("/member")
    public ResponseEntity<String> updateMember(@RequestBody MemberUpdateDto memberUpdateDto) {
        HttpStatus httpStatus = memberService.updateMember(memberUpdateDto);
        if (httpStatus.equals(HttpStatus.NOT_FOUND)) {
            return new ResponseEntity<>("업데이트 하려는 사용자를 찾을 수 없습니다.", httpStatus);
        }
        return new ResponseEntity<>(httpStatus);
    }

    @DeleteMapping("/member")
    public ResponseEntity<String> deleteMember() {
        HttpStatus httpStatus = memberService.deleteMember();
        if (httpStatus.equals(HttpStatus.NOT_FOUND)) {
            return new ResponseEntity<>("탈퇴하려는 하려는 사용자를 찾을 수 없습니다.", httpStatus);
        }
        return new ResponseEntity<>(httpStatus);
    }
}
