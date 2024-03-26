package api.board.controller;

import api.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;
    @PostMapping("/heart/{postId}")
    public HttpStatus addHeart(@PathVariable Long postId) {
        return postService.addHeart(postId);

    }
}
