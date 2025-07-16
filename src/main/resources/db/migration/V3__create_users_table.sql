CREATE TABLE "users" (
     "id" UUID DEFAULT gen_random_uuid() PRIMARY KEY,
     "name" VARCHAR(100) NOT NULL,
     "email" VARCHAR(200) NOT NULL UNIQUE,
     "roles" TEXT[],
     "password_hash" VARCHAR(120) NOT NULL
);