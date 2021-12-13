INSERT INTO TB_USER (nickname, email, password, role) VALUES ('PEDRO','pedro@gmail.com','$2a$04$ZrsynW2Wx9xGrBBsKrje3.4q6udAUBJPaOvlGWuFRnb2GjnxshMMO','COMMON'), ('MIGUEL','miguel@gmail.com','$2a$04$ZrsynW2Wx9xGrBBsKrje3.4q6udAUBJPaOvlGWuFRnb2GjnxshMMO','ADMIN'), ('JULIA','julia@gmail.com','$2a$04$ZrsynW2Wx9xGrBBsKrje3.4q6udAUBJPaOvlGWuFRnb2GjnxshMMO','COMMON');

INSERT INTO TB_PRODUCT (name, description, price, weight, img_url) VALUES ('Suco de Laranja', 'Lorem ipsum dolor sit amet. Et voluptates excepturi qui error dolore qui iste quis? Et quia laborum At veniam atque sit quas nihil qui nesciunt expedita est quas perferendis. Est voluptas culpa aut autem maiores qui voluptas expedita.', 199.50, 2, 'https://cdn.shopify.com/s/files/1/0024/9803/5810/products/511226-Product-0-I-637545935948507800_5bc33ac2-be48-4fd1-8f2c-e940352486c2_800x800.jpg');
INSERT INTO TB_PRODUCT (name, description, price, weight, img_url) VALUES ('Suco de Morango', 'Lorem ipsum dolor sit amet. Et voluptates excepturi qui error dolore qui iste quis? Et quia laborum At veniam atque sit quas nihil qui nesciunt expedita est quas perferendis. Est voluptas culpa aut autem maiores qui voluptas expedita.', 234.50, 1.5, 'https://cdn.shopify.com/s/files/1/0024/9803/5810/products/511226-Product-0-I-637545935948507800_5bc33ac2-be48-4fd1-8f2c-e940352486c2_800x800.jpg');
INSERT INTO TB_PRODUCT (name, description, price, weight, img_url) VALUES ('Suco de Caju', 'Lorem ipsum dolor sit amet. Et voluptates excepturi qui error dolore qui iste quis? Et quia laborum At veniam atque sit quas nihil qui nesciunt expedita est quas perferendis. Est voluptas culpa aut autem maiores qui voluptas expedita.', 43.50, 0.7, 'https://cdn.shopify.com/s/files/1/0024/9803/5810/products/511226-Product-0-I-637545935948507800_5bc33ac2-be48-4fd1-8f2c-e940352486c2_800x800.jpg');
INSERT INTO TB_PRODUCT (name, description, price, weight, img_url) VALUES ('Suco de Uva', 'Lorem ipsum dolor sit amet. Et voluptates excepturi qui error dolore qui iste quis? Et quia laborum At veniam atque sit quas nihil qui nesciunt expedita est quas perferendis. Est voluptas culpa aut autem maiores qui voluptas expedita.', 45, 3, 'https://cdn.shopify.com/s/files/1/0024/9803/5810/products/511226-Product-0-I-637545935948507800_5bc33ac2-be48-4fd1-8f2c-e940352486c2_800x800.jpg');
INSERT INTO TB_PRODUCT (name, description, price, weight, img_url) VALUES ('Suco de Limão', 'Lorem ipsum dolor sit amet. Et voluptates excepturi qui error dolore qui iste quis? Et quia laborum At veniam atque sit quas nihil qui nesciunt expedita est quas perferendis. Est voluptas culpa aut autem maiores qui voluptas expedita.', 237.99, 3, 'https://cdn.shopify.com/s/files/1/0024/9803/5810/products/511226-Product-0-I-637545935948507800_5bc33ac2-be48-4fd1-8f2c-e940352486c2_800x800.jpg');


INSERT INTO TB_ORDER (status,user_id) VALUES ('ORDERED',1);
INSERT INTO TB_ORDER (status,user_id) VALUES ('ORDERED',1);

INSERT INTO TB_PRODUCT_ORDER (product_id, order_id, product_amount) VALUES (1,1,2), (2,1,1), (5,1,4);
INSERT INTO TB_PRODUCT_ORDER (product_id, order_id, product_amount) VALUES (1,2,2), (2,2,1), (5,2,4);

-- INSERT INTO TB_PAYLOAD (payload_id, username, destiny, origin, distance, price, order_id) VALUES ('#241','pedro@gmail.com','Destino muito louco', 'Origem muito louca', 23, 128.98,1);
-- INSERT INTO TB_PAYLOAD (payload_id, username, destiny, origin, distance, price, order_id) VALUES ('#242','pedro@gmail.com','Destino muito louco', 'Origem muito louca', 23, 128.98,2);