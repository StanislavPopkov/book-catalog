insert into genre (genre_id, genre) values (1, 'Mystery');
insert into genre (genre_id, genre) values (2, 'Fiction');
insert into genre (genre_id, genre) values (3, 'Nonfiction');
insert into genre (genre_id, genre) values (4, 'Unknownfind');


insert into authors (name, last_name, genre_id) values ('Vasya', 'Ivanov', 1);
insert into authors (name, last_name, genre_id) values ('Petua', 'Petrov', 3);


insert into books (book_name, genre_id, author_id) values ('50 gradient of mind', 3, (select author_id from authors where name = 'Petua' and last_name = 'Petrov'));
insert into books (book_name, genre_id, author_id) values ('qwe', 3, (select author_id from authors where name = 'Petua' and last_name = 'Petrov'));


