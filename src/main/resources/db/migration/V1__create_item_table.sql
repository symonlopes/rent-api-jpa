CREATE TABLE "item" (
    "id" UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    "name" VARCHAR(100) NOT NULL,
    "details" TEXT
);