INSERT INTO public.users (id, email, last_name, name, phone) VALUES (4, 'paletgerardo.devs@gmail.com', 'Palet', 'Gerardo', '113643247');
INSERT INTO public.users (id, email, last_name, name, phone) VALUES (6, 'gracorossanigo@gmail.com', 'Rossanigo', 'Graco', '2494315464');
INSERT INTO public.users (id, email, last_name, name, phone) VALUES (5, 'chocho@gmail.com', 'Palet', 'maxi', '1130313233');
INSERT INTO public.users (id, email, last_name, name, phone) VALUES (7, 'facuna@gmailcom', 'Acuña', 'Facundo', '1111111111');
INSERT INTO public.users (id, email, last_name, name, phone) VALUES (8, 'fbarragan@gmail.com', 'Barragan', 'Fernando', '2222222222');
INSERT INTO public.users (id, email, last_name, name, phone) VALUES (9, 'nico@gmail.com', 'Marchi', 'Nicolas', '3333333333');



INSERT INTO public.auth (id, active, password, user_name, user_id) VALUES (1, true, 'devs9003..', 'gerr', 4);
INSERT INTO public.auth (id, active, password, user_name, user_id) VALUES (2, true, 'devs9003..', 'chocho', 5);
INSERT INTO public.auth (id, active, password, user_name, user_id) VALUES (3, true, 'devs9003..', 'graco', 6);
INSERT INTO public.auth (id, active, password, user_name, user_id) VALUES (4, true, 'devs9003..', 'facu', 7);
INSERT INTO public.auth (id, active, password, user_name, user_id) VALUES (5, true, 'devs9003..', 'fer', 8);
INSERT INTO public.auth (id, active, password, user_name, user_id) VALUES (6, true, 'devs9003..', 'nico', 9);


INSERT INTO public.company (id, cuit, description, direction, name) VALUES (2, '696969696969', 'POSTA NO SABE QUIENE SON', 'YANQUILANDIA 888', 'MICROSOFOT');
INSERT INTO public.company (id, cuit, description, direction, name) VALUES (1, '20202020202', 'descripcion de prueba', 'direccion falsa 123', 'Car_dix');


INSERT INTO public.project (id, description, name, status, company_id) VALUES (2, 'Detalle Ecosistemas', 'Ecosistemas', true, 1);
INSERT INTO public.project (id, description, name, status, company_id) VALUES (1, 'Detalle Portal Ventas', 'Portal ventas', true, 1);
INSERT INTO public.project (id, description, name, status, company_id) VALUES (3, 'Detalle Nueva Landing', 'Landing', true, 1);
INSERT INTO public.project (id, description, name, status, company_id) VALUES (4, 'Detalle Desarrollador Fullstack', 'Portal ventas', true, 1);


INSERT INTO public.role (id, description) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.role (id, description) VALUES (2, 'ROLE_DEVS');


INSERT INTO public.auth_roles (auth_id, role_id) VALUES (1, 1);
INSERT INTO public.auth_roles (auth_id, role_id) VALUES (1, 2);
INSERT INTO public.auth_roles (auth_id, role_id) VALUES (2, 2);
INSERT INTO public.auth_roles (auth_id, role_id) VALUES (3, 2);
INSERT INTO public.auth_roles (auth_id, role_id) VALUES (4, 2);
INSERT INTO public.auth_roles (auth_id, role_id) VALUES (5, 2);
INSERT INTO public.auth_roles (auth_id, role_id) VALUES (6, 2);


INSERT INTO public.users_projects (users_id, projects_id) VALUES (4, 1);
INSERT INTO public.users_projects (users_id, projects_id) VALUES (5, 2);
INSERT INTO public.users_projects (users_id, projects_id) VALUES (8, 3);
INSERT INTO public.users_projects (users_id, projects_id) VALUES (9, 4);
