package api.board.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostGetDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private Long viewCount;
    private Long likeCount;

}
