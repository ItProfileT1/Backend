set search_path to it_profile;

insert into skills(name)
values ('Java'), ('Typescript'), ('Spring'), ('English');

insert into positions(name)
values ('Backend Developer'), ('Frontend developer');

insert into positions_skills (position_id, skill_id)
values (1, 1), (1, 3), (1, 4),
       (2, 2), (2, 4)
