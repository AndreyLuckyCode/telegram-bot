package com.andrey.blog.first_blog.controller;

import com.andrey.blog.first_blog.models.Post;
import com.andrey.blog.first_blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class FreeBlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String freeBlog (Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "free-blog";
    }

    @GetMapping("/blog/add")
    public String freeBlogAdd (Model model){
        return "blog-add";
    }


    @PostMapping("/blog/add")
    public String freeBlogPostAdd(@RequestParam String title,
                                  @RequestParam String anons,
                                  @RequestParam String text,
                                  Model model){
        Post post = new Post(title,anons,text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String freeBlogDetails(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }


    @GetMapping("/blog/{id}/edit")
    public String freeBlogEdit (@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }


    @PostMapping("/blog/{id}/edit")
    public String freeBlogPostUpdate(@PathVariable(value = "id") long id,
                                     @RequestParam String title,
                                     @RequestParam String anons,
                                     @RequestParam String text,
                                     Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setText(text);
        postRepository.save(post);

        return "redirect:/blog";
    }


    @PostMapping("/blog/{id}/remove")
    public String freeBlogPostDelete(@PathVariable(value = "id") long id,
                                     Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }



}
