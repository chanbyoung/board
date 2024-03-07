package api.board.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignDto {
    private String loginId;
    private String password;

}
