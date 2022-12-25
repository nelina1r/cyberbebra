package com.example.demo.services;

import com.example.demo.models.Post;
import com.example.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired private PostRepository postRepository;

    /*private Object PostUtils;
    final private List<Post> posts = PostUtils.buildPosts();

    public Page<Post> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Post> list;

        if (posts.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, posts.size());
            list = posts.subList(startItem, toIndex);
        }

        Page<Post> postPage
                = new PageImpl<Post>(list, PageRequest.of(currentPage, pageSize), posts.size());

        return postPage;
    }*/

    public List<Post> findAll() {
        ArrayList<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        return posts;
    }

//    Pageable pageable = PageRequest.of(0, 10);
//    Page<Post> page = postRepository.findAll(pageable);
//    Pageable pageable = PageRequest.of(0, 20, Sort.by("firstName"));

    public Page<Post> findAll(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size, Sort.by("date")));
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> findById(long id) {
        return  postRepository.findById(id);
    }

    public boolean existsById(long id) {
        return postRepository.existsById(id);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }
}
