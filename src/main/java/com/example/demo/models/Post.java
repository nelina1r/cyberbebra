package com.example.demo.models;

import javax.persistence.*;
import java.util.Date;

@Entity //аннотация. указание того, что данный класс - модель, которая будет отвечать за отпределённую таблицу БД
public class Post {

    @Id //аннотация - уникальный идентификатор
    @GeneratedValue(strategy = GenerationType.AUTO) //аннотация - при добавлении новой записи позволит генерировать новые значения внутри данного поля (автоматически)
    private Long id; //поле - уникальные идентификатор (Long - тип данных)
    private String img, title, anons; // поле - название статьи, анонс
    @Column(length = 10000)
    private String fullText;
    private Date date;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg(){
        return img;
    }

    public  void setImg(String img){
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Post() {
    }

    public Post(String img, String title, String anons, String fullText, Date date, String author) {
        this.img = img;
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
        this.date = date;
        this.author = author;
    }
}

