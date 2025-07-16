CREATE TABLE "category" (
    "id" UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    "name" VARCHAR(100) NOT NULL UNIQUE
);
ALTER TABLE "item" ADD CONSTRAINT "fk_item_category" FOREIGN KEY ("category_id") REFERENCES "category"("id");