package api.board.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentAddDto {
    @NotBlank
    private String content;
}
