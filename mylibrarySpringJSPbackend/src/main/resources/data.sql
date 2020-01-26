INSERT INTO roles VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');

INSERT INTO public.users(
	id, firstname, lastname, username, password, email)
	VALUES (1, 'Nick', 'Me', 'me', '123', 'me@gmail.com');

INSERT INTO public.users(
	id, firstname, lastname, username, password, email)
	VALUES (2, 'Time', 'Mensk', 'mensk', '123', 'mensk@gmail.com');
	
	INSERT INTO public.user_role(
	id, user_id, role_id)
	VALUES (1, 1, 1);
	
	INSERT INTO public.user_role(
	id, user_id, role_id)
	VALUES (2, 2, 2);
