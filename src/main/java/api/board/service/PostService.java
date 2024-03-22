package api.board.service;

import api.board.dto.comment.CommentDto;
import api.board.dto.post.*;
import api.board.entity.Comment;
import api.board.entity.Member;
import api.board.entity.Post;
import api.board.repository.CommentRepository;
import api.board.repository.DslPostRepository;
import api.board.repository.MemberRepository;
import api.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final DslPostRepository dslPostRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public HttpStatus addPost(PostAddDto postAddDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication ={}" ,authentication.getName());
        Optional<Member> findMember = memberRepository.findByLoginId(authentication.getName());
        if (findMember.isPresent()) {
            Post savePost = postRepository.save(postAddDto.toEntity(findMember.get()));
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
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
        String memberLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (findPost.isPresent()) {
            Post post = findPost.get();
            if (post.getMember().getLoginId().equals(memberLoginId)) {
                post.updatePost(postUpdateDto);
                return HttpStatus.OK;
            }
            return  HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.NOT_FOUND;
    }

    @Transactional
    public HttpStatus deletePost(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        String memberLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (findPost.isPresent()) {
            Post post = findPost.get();
            if (post.getMember().getLoginId().equals(memberLoginId)) {
                postRepository.delete(post);
                return HttpStatus.OK;
            }
            return  HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.NOT_FOUND;

    }

    @Transactional
    public HttpStatus addComment(Long postId, CommentDto commentDto) {
        String memberLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Member> findMember = memberRepository.findByLoginId(memberLoginId);
        if (findMember.isPresent()) {
            Optional<Post> findPost = postRepository.findById(postId);
            if (findPost.isEmpty()) {
                return HttpStatus.NOT_FOUND;
            }
            commentRepository.save(commentDto.toEntity(findPost.get(),findMember.get()));
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Transactional
    public HttpStatus updateComment(Long commentId, CommentDto commentDto) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        String memberLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (findComment.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        Comment comment = findComment.get();
        if (!comment.getMember().getLoginId().equals(memberLoginId)) {
            return HttpStatus.BAD_REQUEST;
        }
        comment.updateComment(commentDto);
        return HttpStatus.OK;
    }
    @Transactional
    public HttpStatus deleteComment(Long commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        String memberLoginId = SecurityContextHolder.getContext().getAuthentication().getName();
        if (findComment.isPresent()) {
            Comment comment = findComment.get();
            if (!comment.getMember().getLoginId().equals(memberLoginId)) {
                return HttpStatus.BAD_REQUEST;
            }
            commentRepository.delete(comment);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;

    }
}
