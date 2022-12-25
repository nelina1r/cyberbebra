package com.example.demo.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String img;

    @Column(name = "description")
    private String description;

    @Column(name = "site")
    private String site;

    @Column(name = "date_of_foundation")
    private LocalDate date;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Player> roster;

    @OneToOne
    private Discipline discipline;

    public Team(String name, String img, String description, String site, LocalDate date, Discipline discipline) {
        this.name = name;
        this.img = img;
        this.description = description;
        this.site = site;
        this.date = date;
        this.discipline = discipline;
    }
}
