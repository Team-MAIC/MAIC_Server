SET SESSION sql_mode = 'NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES';

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
INSERT INTO product (product_name, barcode, weight, price)
VALUES ("국산 태양초 햇 건고추", 9234071283944, 1800, 69900);
INSERT INTO product (product_name, barcode, weight, price)
VALUES ("강개상인 수삼 허니 세트", 2934023470237, 1000, 65100);

INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 1, 'A', 1, '11');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 2, 'A', 2, '12');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 3, 'A', 5, '13');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 4, 'A', 1, '14');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 5, 'B', 6, '21');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 6, 'B', 4, '22');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 7, 'B', 5, '23');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 8, 'B', 4, '24');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 9, 'C', 2, '31');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 10, 'C', 3, '32');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 11, 'C', 1, '33');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (1, 12, 'C', 4, '34');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (2, 1, 'A', 5, '21');
INSERT INTO center_product (center_id, product_id, area, line, location)
VALUES (2, 2, 'B', 1, '22');

-- INSERT INTO order_info (round_id, consumer_id, total_price, status)
-- VALUES (1, 1, 31000, 'FINISH');
-- INSERT INTO order_info (round_id, consumer_id, total_price, status)
-- VALUES (1, 2, 147000, 'WAIT');
-- INSERT INTO order_info (round_id, consumer_id, total_price, status)
-- VALUES (2, 3, 106000, 'WAIT');
-- INSERT INTO order_info (round_id, consumer_id, total_price, status)
-- VALUES (2, 4, 147000, 'WAIT');
-- INSERT INTO order_info (round_id, consumer_id, total_price, status)
-- VALUES (3, 5, 147000, 'WAIT');
-- INSERT INTO order_info (round_id, consumer_id, total_price, status)
-- VALUES (3, 6, 147000, 'WAIT');
-- INSERT INTO order_info (round_id, consumer_id, total_price, status)
-- VALUES (4, 7, 147000, 'WAIT');
--
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (1, 1, 5);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (1, 2, 3);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (1, 5, 1);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (1, 7, 1);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (2, 3, 3);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (2, 5, 3);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (3, 2, 2);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (3, 11, 2);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (4, 6, 1);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (4, 11, 1);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (5, 10, 4);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (5, 12, 3);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (6, 5, 2);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (6, 9, 1);
-- INSERT INTO order_product (order_info_id, product_id, amount)
-- VALUES (7, 5, 1);
--
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (1, 1, 1, 'A', 1, '11', 5, 'READY');
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (1, 2, 2, 'A', 2, '12', 3, 'FINISH');
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (1, 3, 3, 'A', 5, '13', 3, 'FINISH');
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (1, 5, 5, 'B', 6, '21', 4, 'READY');
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (1, 7, 6, 'A', 5, '23', 1, 'FINISH');
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (2, 2, 1, 'A', 2, '12', 2, 'FINISH');
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (2, 6, 6, 'B', 4, '22', 1, 'READY');
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (2, 11, 11, 'C', 1, '33', 3, 'FINISH');
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (3, 5, 7, 'B', 6, '21', 2, 'READY');
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (3, 10, 12, 'C', 3, '32', 4, 'READY');
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (3, 12, 9, 'C', 4, '34', 4, 'READY');
-- INSERT INTO pick_todo (round_id, product_id, worker_id, area, line, location, amount, status)
-- VALUES (4, 5, 6, 'B', 6, '21', 1, 'READY');
--
-- INSERT INTO round (center_id, center_round_number, status)
-- VALUES (1, 10, 'PICK');
-- INSERT INTO round (center_id, center_round_number, status)
-- VALUES (1, 11, 'PICK');
-- INSERT INTO round (center_id, center_round_number, status)
-- VALUES (1, 12, 'READY');
-- INSERT INTO round (center_id, center_round_number, status)
-- VALUES (1, 13, 'WAIT');
