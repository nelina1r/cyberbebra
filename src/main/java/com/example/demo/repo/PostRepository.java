package com.example.demo.repo;

import com.example.demo.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository; //интерфейс позволяющий работать с таблицами
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

//public interface PostRepository extends CrudRepository<Post, Long> { //extends - наследование. <> - указание модели, с которой работаем и типа данных
//}
//@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {  //extends - наследование. <> - указание модели, с которой работаем и типа данных
}

//public interface PostRepository extends JpaRepository<Post, Long> {  //extends - наследование. <> - указание модели, с которой работаем и типа данных
//   Post findById(Long id);
//}