package api.board.service;

import api.board.dto.post.PostAddDto;
import api.board.dto.post.PostGetDto;
import api.board.dto.post.PostUpdateDto;
import api.board.entity.Post;
import api.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
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

    public PostGetDto getPost(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isPresent()) {
            Post post = findPost.get();
            return PostGetDto.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
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




}
