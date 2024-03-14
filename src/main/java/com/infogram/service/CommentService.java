package com.infogram.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.infogram.models.Comment;
import com.infogram.models.Post;
import com.infogram.repository.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public boolean existsById(Long id) {
        return commentRepository.existsById(id);
    }

    public Optional<Page<Comment>> findByPost(Post post, Pageable pageable){
        return commentRepository.findByPostId(post.getId(), pageable);
    }

    public Optional<Page<Comment>> findByPostId(Long postId, Pageable pageable){
        return commentRepository.findByPostId(postId, pageable);
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    /* 
     * This method deletes all the comments of a post.
     * @param post: the post.
     * date: 2024-03-13
     * version: 1.0
     * author: Danilo Marchesani
     */
    public void deleteCommentsByPost(Post post) {
        commentRepository.deleteById(post.getId());
    }

    /* 
     * This method deletes a comment by its id.
     * @param id: the id of the comment.
     *  date: 2024-03-13
     * version: 1.0
     * author: Danilo Marchesani
     */
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }
}
