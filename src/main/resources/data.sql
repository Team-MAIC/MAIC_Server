INSERT INTO center (center_name) VALUES ('김포물류센터');
INSERT INTO center (center_name) VALUES ('송파물류센터');
INSERT INTO center (center_name) VALUES ('고촌물류센터');

INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'A');
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'A');
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'A');
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'A');
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'B');
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'B');
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'B');
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'B');
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'C');
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'C');
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'C');
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'PICK', null, 'C');

INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'DAS', 1, null);
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'DAS', 2, null);
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'DAS', 3, null);
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'DAS', 4, null);
INSERT INTO worker (center_id, role, passage, area)
VALUES (1, 'DAS', 5, null);
INSERT INTO worker (center_id, role, passage, area)
VALUES (2, 'DAS', 1, null);
INSERT INTO worker (center_id, role, passage, area)
VALUES (2, 'DAS', 2, null);
INSERT INTO worker (center_id, role, passage, area)
VALUES (2, 'DAS', 3, null);

INSERT INTO product (product_name, barcode, weight, price)
VALUES ("친환경 노루궁뎅이 버섯", 2304023748221, 160, 3000);
INSERT INTO product (product_name, barcode, weight, price)
VALUES ("초당옥수수", 1093402938421, 250, 3000);
INSERT INTO product (product_name, barcode, weight, price)
VALUES ("온담 표고 3종세트", 1039294893820, 355, 40000);
INSERT INTO product (product_name, barcode, weight, price)
VALUES ("온담 견과 흑화고 혼합세트", 2048523422327, 760, 40000);
INSERT INTO product (product_name, barcode, weight, price)
VALUES ("냉동 그릴드 바밤 단호박", 0137402483022, 320, 5000);
INSERT INTO product (product_name, barcode, weight, price)
VALUES ("흙연근", 2384790382132, 600, 5000);
INSERT INTO product (product_name, barcode, weight, price)
VALUES ("참다리버섯", 4037989302449, 350, 2000);
INSERT INTO product (product_name, barcode, weight, price)
VALUES ("친환경 깐 감자", 3895032394329, 500, 4000);
INSERT INTO product (product_name, barcode, weight, price)
VALUES ("양파", 9320482334235, 900, 3000);
INSERT INTO product (product_name, barcode, weight, price)
VALUES ("유기농 아스파라거스", 2934023470237, 150, 6000);

INSERT INTO center_product (center_id, product_id, area, location)
VALUES (1, 1, 'A', '11');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (1, 2, 'A', '12');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (1, 3, 'A', '13');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (1, 4, 'A', '14');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (1, 5, 'B', '21');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (1, 6, 'B', '22');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (1, 7, 'B', '23');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (1, 8, 'B', '24');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (1, 9, 'C', '31');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (1, 10, 'C', '32');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (2, 1, 'A', '11');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (2, 2, 'A', '12');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (2, 3, 'B', '21');
INSERT INTO center_product (center_id, product_id, area, location)
VALUES (2, 4, 'B', '22');

INSERT INTO order_info (round_id, consumer_id, total_price, status)
VALUES (1, 1, 31000, 'WAIT');
INSERT INTO order_info (round_id, consumer_id, total_price, status)
VALUES (1, 2, 147000, 'WAIT');

INSERT INTO order_product (order_info_id, product_id, amount)
VALUES (1, 1, 5);
INSERT INTO order_product (order_info_id, product_id, amount)
VALUES (1, 1, 3);
INSERT INTO order_product (order_info_id, product_id, amount)
VALUES (1, 5, 1);
INSERT INTO order_product (order_info_id, product_id, amount)
VALUES (1, 7, 1);
INSERT INTO order_product (order_info_id, product_id, amount)
VALUES (2, 3, 3);
INSERT INTO order_product (order_info_id, product_id, amount)
VALUES (2, 5, 3);
INSERT INTO order_product (order_info_id, product_id, amount)
VALUES (2, 9, 4);

INSERT INTO pick_todo (round_id, product_id, worker_id, area, location, amount, status)
VALUES (1, 1, 1, 'A', '11', 8, 'READY');
INSERT INTO pick_todo (round_id, product_id, worker_id, area, location, amount, status)
VALUES (1, 3, 2, 'A', '13', 3, 'READY');
INSERT INTO pick_todo (round_id, product_id, worker_id, area, location, amount, status)
VALUES (1, 5, 6, 'B', '21', 4, 'READY');
INSERT INTO pick_todo (round_id, product_id, worker_id, area, location, amount, status)
VALUES (1, 7, 7, 'B', '23', 1, 'READY');
INSERT INTO pick_todo (round_id, product_id, worker_id, area, location, amount, status)
VALUES (1, 9, 11, 'C', '31', 4, 'READY');
