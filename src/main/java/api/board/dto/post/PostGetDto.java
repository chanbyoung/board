package api.board.dto.post;

import api.board.entity.Comment;
import api.board.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class PostGetDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private List<Comment> commentList;
    private Long viewCount;
    private Long likeCount;


    static public PostGetDto toDto(Post post, List<Comment> commentList) {
        return PostGetDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .commentList(commentList)
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .build();
    }
}
