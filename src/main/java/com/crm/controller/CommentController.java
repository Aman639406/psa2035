package com.crm.controller;


import com.crm.entity.Comment;
import com.crm.entity.Post;
import com.crm.repository.CommentRepository;
import com.crm.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private PostRepository postRepository;
    private CommentRepository commentRepository;


    public CommentController(PostRepository postRepository, CommentRepository commentRepository){
        this.postRepository=postRepository;
        this.commentRepository=commentRepository;
    }

    @PostMapping
    public ResponseEntity<Comment>  createComment(

            @RequestBody Comment comment,
            @RequestParam long postId

    ){

        Post post=postRepository.findById(postId).get();
        comment.setPost(post);
       Comment comments = commentRepository.save(comment);
       return new ResponseEntity<>(comments, HttpStatus.CREATED);
    }


}
