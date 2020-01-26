DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS users_roles;
CREATE TABLE roles (
  id serial NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);


ALTER TABLE roles
    OWNER to myuser;
	

CREATE TABLE users (
  id serial NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
	CONSTRAINT "userrolePK" PRIMARY KEY (id)
);

ALTER TABLE users
    OWNER to myuser;
	
CREATE TABLE public.user_role
(
    id serial NOT NULL,
    user_id numeric,
    role_id numeric,
    CONSTRAINT id PRIMARY KEY (id),
    CONSTRAINT fk_security_user_id FOREIGN KEY (id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_security_role_id FOREIGN KEY (id)
        REFERENCES public.roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE public.user_role
    OWNER to myuser;
