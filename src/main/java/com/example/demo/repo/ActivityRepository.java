package com.example.demo.repo;

import com.example.demo.models.Activity;
import org.springframework.data.repository.CrudRepository; //интерфейс позволяющий работать с таблицами
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActivityRepository extends PagingAndSortingRepository<Activity, Long> {//extends - наследование. <> - указание модели, с которой работаем и типа данных
    Activity findByTitle(String title);
}