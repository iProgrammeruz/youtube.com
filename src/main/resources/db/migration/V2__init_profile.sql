-- create admin
INSERT INTO profile (name, surname, email, password, status, visible, created_date, role)
VALUES ('Adminjon', 'Adminov', 'admin@gmail.com', '71c153844e99916ca6091f1679741028',
        'ACTIVE', true, now(), 'ROLE_ADMIN') ON CONFLICT (id) DO NOTHING;
SELECT setval('profile_id_seq', max(id))
FROM profile;

-- create user
INSERT INTO profile (name, surname, email, password, status, visible, created_date, role)
VALUES ('Valish', 'Alish', 'vali@gmail.com', 'cf4f0b3dc04683f4eec42f9009c5d296',
        'ACTIVE', true, now(), 'ROLE_USER') ON CONFLICT (id) DO NOTHING;
SELECT setval('profile_id_seq', max(id))
FROM profile;