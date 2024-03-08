package api.board.dto.post;

import api.board.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostsGetDto {
    private Long id;
    private String title;
    private Long viewCount;
    private Long likeCount;

    static public PostsGetDto toDto(Post post) {
        return PostsGetDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount()).build();
    }
}
