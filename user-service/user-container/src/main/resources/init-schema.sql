DROP TABLE IF EXISTS os_user;

--CREATE TABLE "user".os_user
CREATE TABLE os_user
(
    user_id uuid NOT NULL,
    login character varying NOT NULL,
    password character varying NOT NULL,
    email character varying NOT NULL,
    position character varying NOT NULL,
    status character varying NOT NULL,
    creation_date DATE NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (user_id)
);