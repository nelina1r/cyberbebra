package com.example.demo.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Activity {
    @Id //аннотация - уникальный идентификатор
    @GeneratedValue(strategy = GenerationType.AUTO) //аннотация - при добавлении новой записи позволит генерировать новые значения внутри данного поля (автоматически)
    private Long id; //поле - уникальные идентификатор (Long - тип данных)
    private String img, title, anons, fullText; // поле - название турнира, анонс, полный текст описания, дата
    private LocalDate startDate;
    private LocalDate endDate;
    private String prize;

    @OneToOne
    private Discipline discipline;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Team> participants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
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

    public Activity() {
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public List<Team> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Team> participants) {
        this.participants = participants;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public LocalDate getStart() {
        return startDate;
    }

    public void setStart(LocalDate start) {
        this.startDate = start;
    }

    public LocalDate getEnd() {
        return endDate;
    }

    public void setEnd(LocalDate end) {
        this.endDate = end;
    }

    public Activity(String img, String title, String anons, LocalDate startDate, LocalDate endDate, String fullText, String prize, List<Team> participants, Discipline discipline) {
        this.img = img;
        this.title = title;
        this.anons = anons;
        this.startDate = startDate;
        this.fullText = fullText;
        this.prize = prize;
        this.participants = participants;
        this.discipline = discipline;
        this.endDate = endDate;
    }

}
