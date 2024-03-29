package api.board.dto.member;

import api.board.dto.post.PostsGetDto;
import api.board.entity.Member;
import api.board.entity.Post;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String username;
    private List<PostsGetDto> postList;
    private List<PostsGetDto> heartList;

    static public MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .postList(member.getPostList().stream()
                        .map(PostsGetDto::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public MemberDto updateHeartList(List<Post> heartList) {
        this.heartList = heartList.stream()
                .map(PostsGetDto::toDto)
                .collect(Collectors.toList());
        return this;
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .username(username)
                .build();
    }
}
