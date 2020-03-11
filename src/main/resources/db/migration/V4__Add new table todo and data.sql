create table todos
(
    id   integer unsigned primary key auto_increment,
    text varchar(100) not null,
    done bit
);
insert into todos (text, done) values ('Done Todo', 1);
insert into todos (text, done) values ('Undone Todo', 0);