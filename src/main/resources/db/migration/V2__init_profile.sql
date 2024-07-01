-- create admin
INSERT INTO profile (name, surname, email, password, status, visible, created_date, role)
VALUES ('Adminjon', 'Adminov', 'admin11@gmail.com', '71c153844e99916ca6091f1679741028',
        'ACTIVE', true, now(), 'ROLE_ADMIN') ON CONFLICT (id) DO NOTHING;
SELECT setval('profile_id_seq', max(id))
FROM profile;

-- -- create owner
-- INSERT INTO profile (name, surname, email, password, status, visible, created_date, role)
-- VALUES ('Alish', 'Aliyev', 'ali1@gmail.com', '083eb1f22813e7ae478dddad6aa436d8',
--         'ACTIVE', true, now(), 'ROLE_OWNER') ON CONFLICT (id) DO NOTHING;
-- SELECT setval('profile_id_seq', max(id))
-- FROM profile;

-- create user
INSERT INTO profile (name, surname, email, password, status, visible, created_date, role)
VALUES ('Valish', 'Alish', 'vali1@gmail.com', 'cf4f0b3dc04683f4eec42f9009c5d296',
        'ACTIVE', true, now(), 'ROLE_USER') ON CONFLICT (id) DO NOTHING;
SELECT setval('profile_id_seq', max(id))
FROM profile;