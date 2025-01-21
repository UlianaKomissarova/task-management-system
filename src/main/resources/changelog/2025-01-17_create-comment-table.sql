create sequence if not exists comment_id_seq start with 1 increment by 1;

create table comment (
    id bigint not null default nextval('comment_id_seq'),
    text text not null,
    author_id bigint not null,
    task_id bigint not null,
    primary key (id),
    foreign key (author_id) references app_user(id) on delete cascade,
    foreign key (task_id) references task(id) on delete cascade
);

create index idx_comment_author_id on comment(author_id);
create index idx_comment_task_id on comment(task_id);