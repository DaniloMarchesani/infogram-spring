package com.infogram.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infogram.models.Comment;
import com.infogram.service.CommentService;

/* NOTE:
 * This class is a controller that handles the comments of a post.
 * It has a method that returns all the comments of a post.
 * date: 2024-03-13
 * version: 1.0
 * author: Danilo Marchesani
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String systemCheck() {
        return "Comment Controller is up and running";
    }

    /*
     * This method returns all the comments of a post.
     * 
     * @param id: the id of the post.
     * 
     * @param pageable: the pageable object.
     * 
     * @return ResponseEntity: the response entity with the comments of the post.
     * date: 2024-03-13
     * version: 1.0
     * author: Danilo Marchesani
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentsOfAPost(@PathVariable Long id, Pageable pageable) {
        try {
            Optional<Page<Comment>> comments = commentService.findByPostId(id, pageable);
            if (comments.isPresent()) {
                return ResponseEntity.ok(comments.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No comments found for post with id: " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /*
     * This method creates a new comment.
     * 
     * @param comment: the comment to be created.
     * 
     * @return ResponseEntity: the response entity with the result of the creation.
     * date: 2024-03-13
     * version: 1.0
     * author: Danilo Marchesani
     */
    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        try {
            Comment newComment = commentService.saveComment(comment);
            if (newComment != null) {
                return ResponseEntity.ok("Comment created successfully with id: " + newComment.getId());
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error: Comment not created cause is null.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /*
     * This method updates a comment.
     * 
     * @param id: the id of the comment.
     * 
     * @param comment: the comment to be updated.
     * 
     * @return ResponseEntity: the response entity with the result of the update.
     * date: 2024-03-13
     * version: 1.0
     * author: Danilo Marchesani
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        try {

            Optional<Comment> updatedComment = commentService.findById(id);
            if (!updatedComment.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Comment not found!");
            }

            updatedComment.get().setText(comment.getText());
            updatedComment.get().setCreatedAt(comment.getCreatedAt());
            updatedComment.get().setLikes(comment.getLikes());
            updatedComment.get().setUser(comment.getUser());
            updatedComment.get().setPost(comment.getPost());

            commentService.saveComment(updatedComment.get());

            return ResponseEntity.ok("Comment updated successfully with id: " + updatedComment.get().getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /*
     * This method deletes all the comments of a post.
     * 
     * @param id: the id of the post.
     * 
     * @return ResponseEntity: the response entity with the result of the deletion.
     * date: 2024-03-13
     * version: 1.0
     * author: Danilo Marchesani
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCommentsByPost(@PathVariable Long id) {
        try {
            if (!commentService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Comment not found in thids post!");
            }
            commentService.deleteCommentById(id);
            return ResponseEntity.ok("Comments deleted successfully for post with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
