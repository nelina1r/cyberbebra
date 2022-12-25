package com.example.demo.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Discipline discipline;

    @Column(name = "date_of_start")
    private LocalDateTime time;

    @OneToOne
    private Activity tournament;

    @OneToOne
    private Team team1;

    @OneToOne
    private Team team2;

    public Match(Discipline discipline, LocalDateTime time, Activity tournament, Team team1, Team team2) {
        this.discipline = discipline;
        this.time = time;
        this.tournament = tournament;
        this.team1 = team1;
        this.team2 = team2;
    }
}
