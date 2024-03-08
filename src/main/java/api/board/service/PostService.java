package api.board.service;

import api.board.dto.comment.CommentDto;
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
        Post savePost = postRepository.save(postAddDto.toEntity());
        return savePost.getId();
    }
    public Page<PostsGetDto> getPosts(Pageable pageable, PostSearchContent postSearchContent) {
        return dslPostRepository.getPosts(pageable, postSearchContent).map(PostsGetDto::toDto);
    }


    public PostGetDto getPost(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isPresent()) {
            Post post = findPost.get();
            List<Comment> findComment = commentRepository.findCommentByPost(post);
            return PostGetDto.toDto(post, findComment);
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
    public HttpStatus addComment(Long postId, CommentDto commentDto) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        commentRepository.save(commentDto.toEntity(findPost.get()));
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus updateComment(Long commentId, CommentDto commentDto) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        findComment.get().updateComment(commentDto);
        return HttpStatus.OK;
    }
    @Transactional
    public HttpStatus deleteComment(Long commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isPresent()) {
            commentRepository.delete(findComment.get());
            return HttpStatus.OK;
        }

        return HttpStatus.NOT_FOUND;

    }
}
