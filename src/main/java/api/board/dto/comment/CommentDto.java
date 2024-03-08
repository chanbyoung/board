package api.board.dto.comment;

import api.board.entity.Comment;
import api.board.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    @NotBlank
    private String content;

    public Comment toEntity(Post post) {
        return Comment.builder()
                .content(content)
                .post(post)
                .build();
    }
}
