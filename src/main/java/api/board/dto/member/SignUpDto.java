package api.board.dto.member;

import api.board.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {

    private String username;
    private String loginId;
    private String password;
    private List<String> roles = new ArrayList<>();

    public Member toEntity(String encodedPassword, List<String> roles) {
        return Member.builder()
                .username(username)
                .loginId(loginId)
                .password(encodedPassword)
                .roles(roles)
                .build();
    }
}
