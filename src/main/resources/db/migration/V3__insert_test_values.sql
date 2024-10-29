set search_path to it_profile;

insert into skill_types(name)
values ('Hard'), ('Soft');

insert into rate_scales(name)
values ('Scale1'), ('Scale2');

insert into skills(name, type_id, rate_scale_id)
values ('Java', 1, 1), ('Typescript', 1, 2), ('Spring', 1, 2), ('English', 1, 1);

insert into positions(name)
values ('Backend Developer'), ('Frontend developer');

insert into positions_skills (position_id, skill_id)
values (1, 1), (1, 3), (1, 4),
       (2, 2), (2, 4)
