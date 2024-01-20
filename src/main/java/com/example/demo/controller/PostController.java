package com.example.demo.controller;

import com.example.demo.dto.PostRequest;
import com.example.demo.model.Assignment;
import com.example.demo.model.Post;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    @GetMapping("/of-user")
    public ResponseEntity<Page<Post>> getAllPostsOfCurrentUser(@RequestParam(defaultValue = "0") int offset,
                                                               @RequestParam(defaultValue = "3") int pageSize,
                                                               @RequestParam(defaultValue = "creationTime") String sortBy){
        return new ResponseEntity<>(postService.getAllPostsOfCurrentUser(offset, pageSize, sortBy), HttpStatus.OK);
    }
    @GetMapping("/by-subject/{subjectId}")
    public ResponseEntity<Page<Post>> getAllPostsOfCurrentUserBySubject(@RequestParam(defaultValue = "0") int offset,
                                                                        @RequestParam(defaultValue = "3") int pageSize,
                                                                        @RequestParam(defaultValue = "creationTime") String sortBy,
                                                                        @PathVariable Long subjectId){
        return new ResponseEntity<>(postService.getAllPostsOfCurrentUserBySubject(offset, pageSize, sortBy, subjectId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER')")
    @PostMapping("/create")
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
        postService.createPost(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER')")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
