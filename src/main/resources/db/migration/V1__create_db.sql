drop table if exists users cascade;
drop table if exists activity cascade;
drop table if exists discipline cascade;
drop table if exists match cascade;
drop table if exists player cascade;
drop table if exists post cascade;
drop table if exists team cascade;
drop table if exists activity_participants cascade;
drop table if exists team_roster cascade;

drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 1 increment 1;

create table users
(
    id       SERIAL not null ,
    username varchar(255),
    password varchar(255),
    role varchar(255),
    primary key (id)
);
alter table users
    add constraint uk_username unique (username);


create table discipline
(
    id   SERIAL not null,
    img varchar(500),
    name varchar(255),
    primary key (id)
);

create table match
(
    id   SERIAL not null,
    date_of_start TIMESTAMP WITHOUT TIME ZONE not null,
    discipline_id BIGINT not null,
    team1_id BIGINT not null,
    team2_id BIGINT not null,
    tournament_id BIGINT not null,
    primary key (id)
);

create table player
(
    id   SERIAL not null,
    img varchar(500),
    name varchar(500),
    primary key (id)
);

create table post
(
    id   SERIAL not null,
    anons varchar(255) not null,
    author varchar(255) not null,
    date TIMESTAMP WITHOUT TIME ZONE not null,
    full_text varchar(10000) not null,
    img varchar(500) not null,
    title varchar(255) not null,
    primary key (id)
);

create table team
(
    id   SERIAL not null,
    date_of_foundation date not null,
    description varchar(10000) not null,
    img varchar(255) not null,
    name varchar(255) not null,
    site varchar(255) not null,
    discipline_id BIGINT not null,
    primary key (id)
);

create table activity
(
    id   SERIAL not null,
    anons varchar(255) not null,
    end_date date not null,
    start_date date not null,
    img varchar(500) not null,
    full_text varchar(10000) not null,
    prize varchar(255) not null,
    title varchar(255) not null,
    discipline_id BIGINT not null,
    primary key (id)
);

create table team_roster
(
    id_team BIGINT not null,
    roster_id BIGINT not null
);

create table activity_participants
(
    activity_id   BIGINT not null,
    participants_id BIGINT not null
);

alter table team_roster
    add constraint fkbl8nk01gsmuq4y4jqxxsm5g8b foreign key (id_team) references team;
alter table team_roster
    add constraint fkf4jcx35uw68ue9exa8hnfgsu4 foreign key (roster_id) references player;


alter table activity_participants
    add constraint fk6f6f40mjs4n8439tl1utsorpj foreign key (activity_id) references activity;
alter table activity_participants
    add constraint fkParts foreign key (participants_id) references team;




