set search_path to it_profile;

insert into roles (name)
values ('ROLE_ADMIN'), ('ROLE_USER');

insert into users(username, password, role_id)
values ('admin', 'ag17iWkzhGrMCsgzXG0l8hUwToFC5HPsn_QK2m3x3nE', '1')