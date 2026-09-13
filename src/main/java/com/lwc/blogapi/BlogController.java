package com.lwc.blogapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestParam String title, @RequestParam String content) {
        Post post = postService.createPost(title, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchByKeyword(@RequestParam String keyword) {
        List<Post> posts = postService.searchByTitle(keyword);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/total")
    public ResponseEntity<String> getTotalWordCount() {
        List<String> wordCounts = List.of("100", "200", "300");
        String total = "";
        for (String count : wordCounts) {
            total += count;
        }
        return ResponseEntity.ok("Total word counts: " + total);
    }
}