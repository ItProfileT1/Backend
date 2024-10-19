create schema if not exists it_profile;

set search_path to it_profile;

create table if not exists roles(
    id bigint primary key,
    name varchar(255),
    description text
);

create table if not exists specialists(
    id bigint primary key,
    role_id bigint references roles(id),
    fio varchar(255),
    birthday date,
    sex varchar(255),
    city varchar(255)
);

create table if not exists soft_skills(
    id bigint primary key,
    name varchar(255),
    description text
);

create table if not exists specialists_soft_skills(
    specialist_id bigint references specialists(id),
    soft_skills_id bigint references soft_skills(id),
    level bigint,
    primary key (specialist_id, soft_skills_id)
);

create table if not exists hard_skills(
    id bigint primary key,
    name varchar(255),
    description text
);

create table if not exists roles_hard_skills(
    role_id bigint references roles(id),
    hard_skill_id bigint references hard_skills(id),
    primary key (role_id, hard_skill_id)
);

create table if not exists specialists_hard_skills(
    specialist_id bigint references specialists(id),
    hard_skill_id bigint references hard_skills(id),
    level bigint,
    primary key (specialist_id, hard_skill_id)
);