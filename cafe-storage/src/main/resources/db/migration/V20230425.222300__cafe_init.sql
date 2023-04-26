use cafe;

insert into option_group(name)
values ('size');

insert into options(name, option_group_id)
values ('small', last_insert_id()),
       ('large', last_insert_id());

insert into role(name)
values ('ROLE_MANAGER'),
       ('ROLE_EMPLOYEE');
