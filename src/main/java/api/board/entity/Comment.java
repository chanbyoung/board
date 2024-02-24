package api.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
@Entity
@Getter
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_Id")
    private Long id;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;
}
