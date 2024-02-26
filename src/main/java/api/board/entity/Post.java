package api.board.entity;

import api.board.dto.post.PostUpdateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private Long viewCount;
    private Long likeCount;

    public void updatePost(PostUpdateDto postUpdateDto) {
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
    }
}
