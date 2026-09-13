package com.lwc.blogapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    private static final int TITLE_MIN_LENGTH = 3;
    private static final int TITLE_MAX_LENGTH = 100;
    private static final int CONTENT_MIN_LENGTH = 50;
    private static final int CONTENT_MAX_LENGTH = 5000;

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new InvalidPostException("Title cannot be empty");
        }
        if (title.length() < TITLE_MIN_LENGTH) {
            throw new InvalidPostException("Title must be at least " + TITLE_MIN_LENGTH + " characters long");
        }
        if (title.length() > TITLE_MAX_LENGTH) {
            throw new InvalidPostException("Title must not exceed " + TITLE_MAX_LENGTH + " characters");
        }
    }

    private void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new InvalidPostException("Content cannot be empty");
        }
        if (content.length() < CONTENT_MIN_LENGTH) {
            throw new InvalidPostException("Content must be at least " + CONTENT_MIN_LENGTH + " characters long");
        }
        if (content.length() > CONTENT_MAX_LENGTH) {
            throw new InvalidPostException("Content must not exceed " + CONTENT_MAX_LENGTH + " characters");
        }
    }

    public Post createPost(String title, String content) {
        validateTitle(title);
        validateContent(content);

        Post post = new Post(title, content);
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(String id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException(id));
    }

    public Post updatePost(String id, String title, String content) {
        validateTitle(title);
        validateContent(content);

        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException(id));

        post.setTitle(title);
        post.setContent(content);

        return postRepository.save(post);
    }

    public void deletePost(String id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }
        postRepository.deleteById(id);
    }

    public List<Post> searchByTitle(String keyword) {
        return postRepository.findByKeyword(keyword);
    }
}
