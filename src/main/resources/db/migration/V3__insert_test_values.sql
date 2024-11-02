set search_path to it_profile;

--Пользователи
insert into users(username, password, role_id)
values ('joe', '', 2),
       ('donald', '', 2);
--
--Навыки

insert into skill_categories (skill_type_id, name)
values (1, 'Languages'), (1, 'Tools'),
       (1, 'Techniques'), (1, 'Platforms');

insert into rate_scales(name)
values ('Уровень владения языком программирования'), ('Уровень владения иностранным языком');

insert into rates (scale_id, description, numeric_value)
values (1, 'Стажер', 0), (1, 'Джуниор', 1), (1, 'Мидл', 2), (1, 'Синьер', 3),
       (2, 'A0', 0), (2, 'А2', 1), (2, 'B2', 2), (2, 'C1', 3), (2, 'C2', 4);

insert into skills(name, type_id, rate_scale_id)
values ('Java', 1, 1), ('Typescript', 1, 2), ('Spring', 1, 2), ('English', 2, 1);
--

--Должности
insert into positions(name)
values ('Backend Developer'), ('Frontend developer');

insert into positions_skills (position_id, skill_id)
values (1, 1), (1, 3), (1, 4),
       (2, 2), (2, 4);
--

--Специалисты
insert into specialists (user_id, position_id, surname, name, patronymic, birthday, sex, city)
values (3, 1, 'biden', 'joe', '', '11-12-1999', 'male', 'Vavilon'),
       (4, 2, 'trump', 'donald', '', '11-12-1999', 'male', 'Moscow');

insert into specialists_skills (specialist_id, skill_id, level_id, date)
values (3, 1, 4, now()), (3, 3, 3, now()), (3, 4, 2, now()),
       (4, 2, 5, now()), (4, 4, 1, now())
--