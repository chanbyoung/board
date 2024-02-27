package api.board.controller;

import api.board.dto.comment.CommentAddDto;
import api.board.dto.post.PostAddDto;
import api.board.dto.post.PostGetDto;
import api.board.dto.post.PostUpdateDto;
import api.board.entity.Post;
import api.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> addPost(@RequestBody PostAddDto postAddDto) {
        Long postId = postService.addPost(postAddDto);
        log.info("postId={}",postId);
        if (postId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(postId,HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostGetDto> getPost(@PathVariable Long postId) {
        PostGetDto post = postService.getPost(postId);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody PostUpdateDto postUpdateDto, @PathVariable Long postId) {
        return new ResponseEntity<>(postService.updatePost(postId, postUpdateDto));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.deletePost(postId));
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<String> addComment(@PathVariable Long postId, @RequestBody CommentAddDto commentAddDto) {
        HttpStatus httpStatus = postService.addComment(postId, commentAddDto);
        if (httpStatus.equals(HttpStatus.NOT_FOUND)) {
            return new ResponseEntity<>("이미 삭제된 게시글입니다.",httpStatus);
        }
        return new ResponseEntity<>(httpStatus);
    }
}
