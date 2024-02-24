package api.board.dto.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostAddDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

}
