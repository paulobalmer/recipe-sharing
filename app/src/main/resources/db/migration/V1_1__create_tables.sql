CREATE TABLE public.users (
	id uuid NOT NULL,
	"name" varchar(100) NOT NULL,
	username varchar(50) NOT NULL,
	email varchar(200) NOT NULL,
	"password" varchar(100) NOT NULL,
	active bool NOT NULL,
	created_at timestamp NOT NULL,
	updated_at timestamp NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE public.recipes (
	id uuid NOT NULL,
	title varchar(150) NOT NULL,
	user_id uuid NOT NULL,
	description text NULL,
	instructions text NOT NULL,
	servings text NULL,
	created_at timestamp NOT NULL,
	updated_at timestamp NULL,
	CONSTRAINT recipes_pkey PRIMARY KEY (id),
	CONSTRAINT fk_recipes_user FOREIGN KEY (user_id) REFERENCES public.users(id)
);

CREATE TABLE public.ingredients (
	id uuid NOT NULL,
	recipe_id uuid NOT NULL,
	"value" numeric(19, 2) NOT NULL,
	unit varchar(30) NOT NULL,
	"type" varchar(120) NOT NULL,
	created_at timestamp NOT NULL,
	updated_at timestamp NULL,
	CONSTRAINT ingredients_pkey PRIMARY KEY (id),
	CONSTRAINT fk_ingredients_recipe FOREIGN KEY (recipe_id) REFERENCES public.recipes(id)
);

CREATE UNIQUE INDEX idx_users_username ON public.users (username);
CREATE UNIQUE INDEX idx_users_email ON public.users (email);