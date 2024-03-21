package api.board.dto.post;

import api.board.entity.Member;
import api.board.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostAddDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public Post toEntity(Member member) {
        return Post.builder()
                .member(member)
                .title(title)
                .content(content)
                .viewCount(0L)
                .likeCount(0L)
                .build();
    }
}
