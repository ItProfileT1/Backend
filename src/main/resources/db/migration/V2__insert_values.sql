set search_path to it_profile;

insert into roles (name)
values ('ROLE_ADMIN'), ('ROLE_USER'), ('ROLE_MASTER');

insert into users(username, password, role_id)
values ('admin', '$2a$10$olLZfxwgaD1N9e4GIDoBIe6tQgu80Sphyg.kqSzum3VISvMOhwlxS', '1'),
       ('master', '$2a$10$6SCfk4ieU87oaBwuWKdaZOISFkrYAeRCGg/wOlxB3wkGXL3OuZQea', '3');

insert into skill_levels(name, numeric_value)
values ('Негативное проявление компетенции', -1),
       ('Компетенция не развита', 0),
       ('Базовый уровень владения компетенцией', 1),
       ('Продвинутый уровень владения компетенцией', 2),
       ('Экспертный уровень владения компетенцией', 3);

insert into skill_types(name)
values ('Hard'), ('Soft');