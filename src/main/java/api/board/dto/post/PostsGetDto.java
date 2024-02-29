package api.board.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostsGetDto {
    private Long id;
    private String title;
    private Long viewCount;
    private Long likeCount;
}
