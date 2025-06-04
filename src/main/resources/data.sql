-- ADD TEST USERS
INSERT INTO customer (id, bonus_points, name) VALUES (1, 500, 'John');
INSERT INTO customer (id, bonus_points, name) VALUES (2, 0, 'Jane');

-- ADD TEST MOVIES
INSERT INTO movie (id, title, type, rented) VALUES (1,'Matrix 11', 'NEW', false);
INSERT INTO movie (id, title, type, rented) VALUES (2,'Spider man', 'REGULAR', false);
INSERT INTO movie (id, title, type, rented) VALUES (3,'Spider man 2', 'REGULAR', false);
INSERT INTO movie (id, title, type, rented) VALUES (4,'Out of africa', 'OLD', false);