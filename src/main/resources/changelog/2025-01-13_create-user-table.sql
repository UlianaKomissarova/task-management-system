create sequence if not exists user_id_seq start with 1 increment by 1;

create table app_user (
    id bigint not null default nextval('user_id_seq'),
    username varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    role varchar(255) not null default 'ROLE_USER',
    enabled boolean not null default true,
    primary key (id),
    unique (username),
    unique (email)
);

create unique index idx_user_email on app_user(email);
create unique index idx_user_username on app_user(username)
