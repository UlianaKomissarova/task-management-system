create sequence if not exists task_id_seq start with 1 increment by 1;

create table task (
    id bigint not null default nextval('task_id_seq'),
    title varchar(255) not null,
    description text not null,
    status varchar(255) not null,
    priority varchar(255) not null,
    author_id bigint not null,
    assignee_id bigint not null,
    primary key (id),
    foreign key (author_id) references app_user(id) on delete cascade,
    foreign key (assignee_id) references app_user(id) on delete cascade
);

create index idx_task_author_id on task(author_id);
create index idx_task_assignee_id on task(assignee_id);
