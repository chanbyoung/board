package api.board.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostAddDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

}
