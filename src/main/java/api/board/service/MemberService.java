package api.board.service;

import api.board.dto.member.MemberDto;
import api.board.dto.member.SignUpDto;
import api.board.dto.security.JwtToken;
import api.board.repository.MemberRepository;
import api.board.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public MemberDto signup(SignUpDto signUpDto) {
        if (memberRepository.existsByLoginId(signUpDto.getLoginId())) {
            throw new IllegalArgumentException("이미 사용중인 ID입니다.");
        }
        //password암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        ArrayList<String> roles = new ArrayList<>();
        roles.add("USER");
        return MemberDto.toDto(memberRepository.save(signUpDto.toEntity(encodedPassword, roles)));
    }
    @Transactional
    public JwtToken signIn(String loginId, String password) {
        // 1.username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        log.info("loginId, password = {} {}",loginId,password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId,password);
        log.info("authenticate = {}" , authenticationToken);
        // 2. 실제 검증 authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때, CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        try {
            Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            log.info("authenticate = {}", authenticate);
            return jwtTokenProvider.generateToken(authenticate);

        } catch (AuthenticationException e) {
            log.error("Authentication filed: {}", e.getMessage());
            throw new RuntimeException("인증실패", e);
        }
        //3.인증 정보를 기반으로 JWT 토큰 생성
    }
}
