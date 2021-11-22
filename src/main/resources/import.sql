INSERT INTO TB_USER (nickname, email, password, role) VALUES ('PEDRO','pedro@gmail.com','DEFAULT','COMMON'), ('MIGUEL','miguel@gmail.com','DEFAULT','ADMIN'), ('JULIA','julia@gmail.com','DEFAULT','COMMON');

INSERT INTO TB_PRODUCT (name, description, price, weight, img_url) VALUES ('Suco de Laranja', 'Lorem ipsum dolor sit amet. Et voluptates excepturi qui error dolore qui iste quis? Et quia laborum At veniam atque sit quas nihil qui nesciunt expedita est quas perferendis. Est voluptas culpa aut autem maiores qui voluptas expedita.', 199.50, 2, 'google.com.br');
INSERT INTO TB_PRODUCT (name, description, price, weight, img_url) VALUES ('Suco de Morango', 'Lorem ipsum dolor sit amet. Et voluptates excepturi qui error dolore qui iste quis? Et quia laborum At veniam atque sit quas nihil qui nesciunt expedita est quas perferendis. Est voluptas culpa aut autem maiores qui voluptas expedita.', 234.50, 1.5, 'google.com.br');
INSERT INTO TB_PRODUCT (name, description, price, weight, img_url) VALUES ('Suco de Caju', 'Lorem ipsum dolor sit amet. Et voluptates excepturi qui error dolore qui iste quis? Et quia laborum At veniam atque sit quas nihil qui nesciunt expedita est quas perferendis. Est voluptas culpa aut autem maiores qui voluptas expedita.', 43.50, 0.7, 'google.com.br');
INSERT INTO TB_PRODUCT (name, description, price, weight, img_url) VALUES ('Suco de Uva', 'Lorem ipsum dolor sit amet. Et voluptates excepturi qui error dolore qui iste quis? Et quia laborum At veniam atque sit quas nihil qui nesciunt expedita est quas perferendis. Est voluptas culpa aut autem maiores qui voluptas expedita.', 45, 3, 'google.com.br');
INSERT INTO TB_PRODUCT (name, description, price, weight, img_url) VALUES ('Suco de Limão', 'Lorem ipsum dolor sit amet. Et voluptates excepturi qui error dolore qui iste quis? Et quia laborum At veniam atque sit quas nihil qui nesciunt expedita est quas perferendis. Est voluptas culpa aut autem maiores qui voluptas expedita.', 237.99, 3, 'google.com.br');


INSERT INTO TB_ORDER (status) VALUES ('ORDERED');
INSERT INTO TB_ORDER (status) VALUES ('ORDERED');

INSERT INTO TB_PRODUCT_ORDER (product_id, order_id, product_amount) VALUES (1,1,2), (2,1,1), (5,1,4);
INSERT INTO TB_PRODUCT_ORDER (product_id, order_id, product_amount) VALUES (1,2,2), (2,2,1), (5,2,4);