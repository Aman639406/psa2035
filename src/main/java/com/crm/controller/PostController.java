package com.crm.controller;


import com.crm.entity.Post;
import com.crm.repository.CommentRepository;
import com.crm.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public PostController(PostRepository postRepository,CommentRepository commentRepository){
        this.postRepository=postRepository;
        this.commentRepository=commentRepository;
    }


    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestBody Post post
    ){

       Post posts= postRepository.save(post);

       return new ResponseEntity<>(post, HttpStatus.CREATED);
    }


    @DeleteMapping
    public String deletePost(

    ){
        postRepository.deleteById(1L);
        return "Deleted";
    }


}
