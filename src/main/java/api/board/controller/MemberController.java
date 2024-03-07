package api.board.controller;

import api.board.dto.member.SignDto;
import api.board.dto.security.JwtToken;
import api.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/sign-in")
    public JwtToken signin(@RequestBody SignDto signDto) {
        String loginId = signDto.getLoginId();
        String password = signDto.getPassword();
        JwtToken jwtToken = memberService.signIn(loginId, password);
        log.info("request loginId = {}, password = {}", loginId, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}