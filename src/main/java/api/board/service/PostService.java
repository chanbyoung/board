package api.board.service;

import api.board.dto.comment.CommentAddDto;
import api.board.dto.post.*;
import api.board.entity.Comment;
import api.board.entity.Post;
import api.board.repository.CommentRepository;
import api.board.repository.DslPostRepository;
import api.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final DslPostRepository dslPostRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long addPost(PostAddDto postAddDto) {
        Post post = Post.builder()
                .title(postAddDto.getTitle())
                .content(postAddDto.getContent())
                .likeCount(0L)
                .viewCount(0L)
                .build();
        Post savePost = postRepository.save(post);
        return savePost.getId();
    }
    public Page<PostsGetDto> getPosts(Pageable pageable, PostSearchContent postSearchContent) {
        return dslPostRepository.getPosts(pageable, postSearchContent).map(this::convertToDto);
    }

    private PostsGetDto convertToDto(Post post) {
        return PostsGetDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount()).build();
    }


    public PostGetDto getPost(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isPresent()) {
            Post post = findPost.get();
            List<Comment> findComment = commentRepository.findCommentByPost(post);
            return PostGetDto.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .commentList(findComment)
                    .viewCount(post.getViewCount())
                    .likeCount(post.getLikeCount())
                    .build();
        }
        return null;
    }


    @Transactional
    public HttpStatus updatePost(Long postId, PostUpdateDto postUpdateDto) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isPresent()) {
            Post post = findPost.get();
            post.updatePost(postUpdateDto);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @Transactional
    public HttpStatus deletePost(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isPresent()) {
            postRepository.delete(findPost.get());
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;

    }

    @Transactional
    public HttpStatus addComment(Long postId, CommentAddDto commentAddDto) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        Comment comment = Comment.builder()
                .post(findPost.get())
                .content(commentAddDto.getContent())
                .build();
        commentRepository.save(comment);
        return HttpStatus.OK;
    }


}
