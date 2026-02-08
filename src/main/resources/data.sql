-- Desactivar revisión de llaves foráneas temporalmente
SET FOREIGN_KEY_CHECKS = 0;

-- ===============================================================================================
-- 1. USUARIOS (Password para todos: "123456")
-- ===============================================================================================

-- ADMIN
INSERT IGNORE INTO users (id, email, password, first_name, last_name, role) 
VALUES (UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 'admin@construrenta.com', '$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq', 'Super', 'Admin', 'ADMIN');

-- PROVEEDOR 1 (Marcela)
INSERT IGNORE INTO users (id, email, password, first_name, last_name, role) 
VALUES (UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 'marcela@proveedor.com', '$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq', 'Marcela', 'Albarracin', 'PROVIDER');

-- PROVEEDOR 2 (Sebastian)
INSERT IGNORE INTO users (id, email, password, first_name, last_name, role) 
VALUES (UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), 'sebastian@proveedor.com', '$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq', 'Sebastian', 'Jaimes', 'PROVIDER');

-- PROVEEDOR 3 (Victor)
INSERT IGNORE INTO users (id, email, password, first_name, last_name, role) 
VALUES (UUID_TO_BIN('44444444-4444-4444-4444-444444444445'), 'victor@proveedor.com', '$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq', 'Victor', 'Moreno', 'PROVIDER');

-- CLIENTE 1 (Marcela)
INSERT IGNORE INTO users (id, email, password, first_name, last_name, role) 
VALUES (UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), 'marcela@cliente.com', '$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq', 'Marcela', 'Albarracin', 'CUSTOMER');

-- CLIENTE 2 (Sebastian)
INSERT IGNORE INTO users (id, email, password, first_name, last_name, role) 
VALUES (UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), 'sebastian@cliente.com', '$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq', 'Sebastian', 'Jaimes', 'CUSTOMER');

-- CLIENTE 3 (Victor)
INSERT IGNORE INTO users (id, email, password, first_name, last_name, role) 
VALUES (UUID_TO_BIN('66666666-6666-6666-6666-666666666666'), 'victor@cliente.com', '$2a$10$.5Elh8fgxypNUWhpUUr/xOa2sZm0VIaE0qWuGGl9otUfobb46T1Pq', 'Victor', 'Moreno', 'CUSTOMER');

-- ===============================================================================================
-- 2. HERRAMIENTAS (20 Items) - Precios en COP y Rutas Locales
-- ===============================================================================================

INSERT INTO tools (id, name, description, price_per_day, image_url, status, stock, provider_id) VALUES 
(UUID_TO_BIN(UUID()), 'Taladro Percutor Dewalt', 'Taladro industrial 20V MAX XR de alto rendimiento. Ideal para concreto y mampostería pesada. Incluye 2 baterías y cargador rápido.', 45000.00, '/herramientas/taladro.jpeg', 'AVAILABLE', 8, UUID_TO_BIN('22222222-2222-2222-2222-222222222222')),

(UUID_TO_BIN(UUID()), 'Sierra Circular Makita', 'Sierra de 7-1/4 pulgadas, motor de 1800W. Corte preciso en madera y aglomerados. Guía láser incorporada para cortes rectos.', 60000.00, '/herramientas/sierra.jpeg', 'AVAILABLE', 5, UUID_TO_BIN('22222222-2222-2222-2222-222222222222')),

(UUID_TO_BIN(UUID()), 'Lijadora Orbital Bosch', 'Lijadora roto-orbital profesional con filtro microfilter. Perfecta para acabados finos en madera y metal. Velocidad variable.', 35000.00, '/herramientas/lijadora.jpeg', 'AVAILABLE', 6, UUID_TO_BIN('22222222-2222-2222-2222-222222222222')),

(UUID_TO_BIN(UUID()), 'Martillo Demoledor Hilti', 'Rompedor de concreto TE-3000. La bestia para demolición de pisos y losas. Sistema de reducción de vibración AVR.', 180000.00, '/herramientas/demoledor.jpeg', 'RENTED', 0, UUID_TO_BIN('22222222-2222-2222-2222-222222222222')),

(UUID_TO_BIN(UUID()), 'Andamio Tubular (Cuerpo)', 'Cuerpo de andamio certificado estándar 1.5m x 1.5m. Incluye crucetas. Ideal para trabajos en altura y fachada.', 12000.00, '/herramientas/andamio.png', 'AVAILABLE', 50, UUID_TO_BIN('22222222-2222-2222-2222-222222222222')),

(UUID_TO_BIN(UUID()), 'Escalera Telescópica', 'Escalera de aluminio extensible hasta 6 metros. Ligera y fácil de transportar. Patas antideslizantes de seguridad.', 25000.00, '/herramientas/escalera.jpeg', 'AVAILABLE', 4, UUID_TO_BIN('22222222-2222-2222-2222-222222222222')),

(UUID_TO_BIN(UUID()), 'Generador Eléctrico Honda', 'Planta eléctrica a gasolina 5000W. Arranque manual y eléctrico. Autonomía de 8 horas. Ideal para obras sin conexión a red.', 120000.00, '/herramientas/generador.jpeg', 'MAINTENANCE', 1, UUID_TO_BIN('22222222-2222-2222-2222-222222222222')),

(UUID_TO_BIN(UUID()), 'Compresor de Aire', 'Compresor de 50 Litros, motor 2HP. Ideal para pintura y herramientas neumáticas ligeras. Presión máxima 115 PSI.', 55000.00, '/herramientas/compresor.jpeg', 'AVAILABLE', 3, UUID_TO_BIN('22222222-2222-2222-2222-222222222222')),

(UUID_TO_BIN(UUID()), 'Hidrolavadora Industrial', 'Máquina de lavado a presión 3000 PSI a gasolina. Perfecta para limpieza de fachadas, maquinaria y pisos industriales.', 90000.00, '/herramientas/hidrolavadora.jpeg', 'AVAILABLE', 2, UUID_TO_BIN('22222222-2222-2222-2222-222222222222')),

(UUID_TO_BIN(UUID()), 'Mezcladora de Concreto', 'Trompo mezclador de 1 bulto con motor eléctrico 110V/220V. Chasis reforzado para trabajo pesado en obra.', 75000.00, '/herramientas/mezcladora.jpeg', 'AVAILABLE', 4, UUID_TO_BIN('22222222-2222-2222-2222-222222222222')),

-- Herramientas del Proveedor 2 (María)
(UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), 'Soldador Inverter Elite', 'Equipo soldador 200A bi-voltaje (110/220V). Suelda electrodos 6011, 6013 y 7018. Pantalla digital.', 65000.00, '/herramientas/soldador.jpeg', 'AVAILABLE', 3, UUID_TO_BIN('33333333-3333-3333-3333-333333333333')),

(UUID_TO_BIN(UUID()), 'Pulidora Angular 9"', 'Pulidora grande para trabajo pesado. Disco de 9 pulgadas. Mango antivibración. Ideal para corte de metal estructural.', 40000.00, '/herramientas/pulidora.jpeg', 'AVAILABLE', 6, UUID_TO_BIN('33333333-3333-3333-3333-333333333333')),

(UUID_TO_BIN(UUID()), 'Nivel Láser Bosch', 'Nivel de líneas cruzadas con alcance de 20m. Autonivelante. Incluye trípode y estuche protector.', 35000.00, '/herramientas/nivellaser.jpeg', 'AVAILABLE', 2, UUID_TO_BIN('33333333-3333-3333-3333-333333333333')),

(UUID_TO_BIN(UUID()), 'Cortadora de Baldosa', 'Cortadora manual profesional de 60cm. Rodel de carburo de tungsteno. Cortes precisos en cerámica y porcelanato.', 28000.00, '/herramientas/cortadora.png', 'AVAILABLE', 5, UUID_TO_BIN('33333333-3333-3333-3333-333333333333')),

(UUID_TO_BIN(UUID()), 'Vibrador de Concreto', 'Vibrador a gasolina con manguera de 4 metros. Indispensable para evitar burbujas de aire en el fundido de losas y columnas.', 70000.00, '/herramientas/vibrador.jpeg', 'AVAILABLE', 2, UUID_TO_BIN('33333333-3333-3333-3333-333333333333')),

(UUID_TO_BIN(UUID()), 'Pistola de Calor', 'Pistola térmica 1800W con temperatura variable (50°C - 600°C). Ideal para decapar pintura y moldear plásticos.', 20000.00, '/herramientas/pistola.jpeg', 'AVAILABLE', 4, UUID_TO_BIN('33333333-3333-3333-3333-333333333333')),

(UUID_TO_BIN(UUID()), 'Cizalla Manual', 'Cortadora de varilla y pernos. Capacidad de corte hasta 1/2 pulgada. Brazos largos para mayor palanca.', 15000.00, '/herramientas/cizalla.jpeg', 'AVAILABLE', 3, UUID_TO_BIN('33333333-3333-3333-3333-333333333333')),

(UUID_TO_BIN(UUID()), 'Carretilla Buggy', 'Carretilla plástica profunda tipo buggy. Capacidad 100 litros. Llanta neumática reforzada.', 12000.00, '/herramientas/carretilla.jpeg', 'AVAILABLE', 15, UUID_TO_BIN('33333333-3333-3333-3333-333333333333')),

(UUID_TO_BIN(UUID()), 'Extensión Eléctrica Ind.', 'Cable encauchetado calibre 12 de 30 metros. Tomas industriales a prueba de agua. Soporta maquinaria pesada.', 15000.00, '/herramientas/extension.jpeg', 'AVAILABLE', 10, UUID_TO_BIN('33333333-3333-3333-3333-333333333333')),

(UUID_TO_BIN(UUID()), 'Casco de Seguridad', 'Casco certificado tipo ingeniero. Suspensión rachet de 4 puntos. Dieléctrico. Colores variados.', 5000.00, '/herramientas/casco.jpeg', 'AVAILABLE', 30, UUID_TO_BIN('33333333-3333-3333-3333-333333333333'));


-- ===============================================================================================
-- 3. RESERVAS Y PAGOS (20 Registros Pasados) - Precios actualizados
-- ===============================================================================================

-- Reserva 1 (Completada - Soldador por 3 días)
INSERT INTO bookings (id, user_id, tool_id, start_date, end_date, total_price, status) VALUES 
(UUID_TO_BIN('b0000001-0000-0000-0000-000000000001'), UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-01-10 08:00:00', '2024-01-13 18:00:00', 195000.00, 'COMPLETED');

INSERT INTO payments (id, amount, payment_date, method, status, booking_id) VALUES 
(UUID_TO_BIN(UUID()), 195000.00, '2024-01-10 09:00:00', 'CREDIT_CARD', 'COMPLETED', UUID_TO_BIN('b0000001-0000-0000-0000-000000000001'));

-- Reserva 2 (Completada - Soldador por 5 días)
INSERT INTO bookings (id, user_id, tool_id, start_date, end_date, total_price, status) VALUES 
(UUID_TO_BIN('b0000002-0000-0000-0000-000000000002'), UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-02-01 08:00:00', '2024-02-06 18:00:00', 325000.00, 'COMPLETED');

INSERT INTO payments (id, amount, payment_date, method, status, booking_id) VALUES 
(UUID_TO_BIN(UUID()), 325000.00, '2024-02-01 08:30:00', 'PAYPAL', 'COMPLETED', UUID_TO_BIN('b0000002-0000-0000-0000-000000000002'));

-- Generación de reservas variadas (relleno para cumplir rúbrica)
INSERT INTO bookings (id, user_id, tool_id, start_date, end_date, total_price, status) VALUES (UUID_TO_BIN(UUID()), UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-03-01 08:00', '2024-03-02 18:00', 65000.00, 'COMPLETED');
INSERT INTO payments (id, amount, payment_date, method, status, booking_id) VALUES (UUID_TO_BIN(UUID()), 65000.00, '2024-03-01 08:00', 'CASH', 'COMPLETED', (SELECT id FROM bookings WHERE total_price = 65000.00 LIMIT 1));

INSERT INTO bookings (id, user_id, tool_id, start_date, end_date, total_price, status) VALUES (UUID_TO_BIN(UUID()), UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-03-05 08:00', '2024-03-07 18:00', 130000.00, 'COMPLETED');
INSERT INTO payments (id, amount, payment_date, method, status, booking_id) VALUES (UUID_TO_BIN(UUID()), 130000.00, '2024-03-05 08:00', 'TRANSFER', 'COMPLETED', (SELECT id FROM bookings WHERE total_price = 130000.00 LIMIT 1));

INSERT INTO bookings (id, user_id, tool_id, start_date, end_date, total_price, status) VALUES (UUID_TO_BIN(UUID()), UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-04-10 08:00', '2024-04-15 18:00', 325000.00, 'CONFIRMED');
INSERT INTO payments (id, amount, payment_date, method, status, booking_id) VALUES (UUID_TO_BIN(UUID()), 325000.00, '2024-04-10 08:00', 'CREDIT_CARD', 'COMPLETED', (SELECT id FROM bookings WHERE total_price = 325000.00 LIMIT 1));

-- Reservas de relleno masivas (Precios aleatorios coherentes)
INSERT INTO bookings (id, user_id, tool_id, start_date, end_date, total_price, status) VALUES 
(UUID_TO_BIN(UUID()), UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-05-01 08:00', '2024-05-03 18:00', 130000.00, 'COMPLETED'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-05-05 08:00', '2024-05-06 18:00', 65000.00, 'COMPLETED'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-05-10 08:00', '2024-05-15 18:00', 325000.00, 'COMPLETED'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-06-01 08:00', '2024-06-03 18:00', 130000.00, 'COMPLETED'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-06-10 08:00', '2024-06-12 18:00', 130000.00, 'COMPLETED'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-07-01 08:00', '2024-07-05 18:00', 260000.00, 'COMPLETED'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-07-15 08:00', '2024-07-16 18:00', 65000.00, 'COMPLETED'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-08-01 08:00', '2024-08-02 18:00', 65000.00, 'COMPLETED'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-08-10 08:00', '2024-08-15 18:00', 325000.00, 'COMPLETED'),
(UUID_TO_BIN(UUID()), UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), '2024-09-01 08:00', '2024-09-05 18:00', 260000.00, 'COMPLETED');

-- REACTIVAR LLAVES FORÁNEAS
SET FOREIGN_KEY_CHECKS = 1;