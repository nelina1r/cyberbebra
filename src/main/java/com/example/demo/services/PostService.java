package com.example.demo.services;

import com.example.demo.models.Post;
import com.example.demo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public List<Post> findAll() {
        ArrayList<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        return posts;
    }

    @Transactional
    public List<Post> intNews(){
        List<Post> posts = findAll();
        List<Post> posts1 = new ArrayList<>();
        for (Post post : posts){
            if (post.getTitle().contains("International")){
                posts1.add(post);
            }
        }
        return posts1;
    }

//    Pageable pageable = PageRequest.of(0, 10);
//    Page<Post> page = postRepository.findAll(pageable);
//    Pageable pageable = PageRequest.of(0, 20, Sort.by("firstName"));

    @Transactional
    public Page<Post> findAll(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size, Sort.by("date")));
    }

    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Optional<Post> findById(long id) {
        return  postRepository.findById(id);
    }

    @Transactional
    public boolean existsById(long id) {
        return postRepository.existsById(id);
    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }
}
