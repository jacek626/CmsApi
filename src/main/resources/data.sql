insert into Users (id, login, password, email, salt) values (-1, 'osadmin', 'osadmin', 'osadmin@gmail.com','salt');
insert into Users (id, login, password, email, salt) values (-2, 'ossystem', 'ossystem', 'ossystem@gmail.com','salt');

insert into Category (id, name) values (-1, 'Kategoria glowna');
insert into Category (id, name) values (-2, 'Kategoria glowna 2');

insert into Article (id, title, content, creation_date, user_id, category, ratings_positive, ratings_negative) values (-1, 'Article1 title', 'Article 1 content',NOW(), -1,-1,1,2);
insert into Article (id, title, content, creation_date, user_id, category, ratings_positive, ratings_negative) values (-2, 'Article2 title', 'Article 1 content',NOW(), -1,-1,4,3);
insert into Article (id, title, content, creation_date, user_id, category, ratings_positive, ratings_negative) values (-3, 'Article3 title', 'Article 1 content',NOW(), -1,-1,3,4);

insert into Comment(id, content, author, creation_date, article) values (-1, 'content 1', 'author 1',NOW(), -1);
insert into Comment(id, content, author, creation_date, article) values (-2, 'content 2', 'author 1',NOW(), -1);
insert into Comment(id, content, author, creation_date, article) values (-3, 'content 3', 'author 1',NOW(), -1);
insert into Comment(id, content, author, creation_date, article) values (-4, 'content 4', 'author 1',NOW(), -1);

insert into Comment(id, content, author, creation_date, article) values (-5, 'content 5', 'author 1',NOW(), -2);
insert into Comment(id, content, author, creation_date, article) values (-6, 'content 6', 'author 1',NOW(), -2);
insert into Comment(id, content, author, creation_date, article) values (-7, 'content 7', 'author 1',NOW(), -2);
