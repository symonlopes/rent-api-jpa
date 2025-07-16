CREATE TABLE "tag" (
   "id" UUID DEFAULT gen_random_uuid() PRIMARY KEY,
   "name" VARCHAR(100) NOT NULL UNIQUE,
   "value" VARCHAR(50) NOT NULL UNIQUE
);