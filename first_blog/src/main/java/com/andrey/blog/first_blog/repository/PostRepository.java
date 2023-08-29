package com.andrey.blog.first_blog.repository;

import com.andrey.blog.first_blog.models.Post;
import org.springframework.data.repository.CrudRepository;
public interface PostRepository extends CrudRepository<Post, Long> {
}
