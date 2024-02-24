package api.board.dto.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostGetDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private Long viewCount;
    private Long likeCount;

}
