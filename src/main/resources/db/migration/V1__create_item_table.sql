CREATE TABLE "item" (
    "id" UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    "name" VARCHAR(100) NOT NULL,
    "category_id" UUID NOT NULL,
    "details" TEXT
);