CREATE TABLE "image" (
     "id" UUID DEFAULT gen_random_uuid() PRIMARY KEY,
     "item_id" UUID NOT NULL,
     "url" VARCHAR(1000) NOT NULL,
     CONSTRAINT fk_item_image FOREIGN KEY ("item_id") REFERENCES "item"("id") ON DELETE CASCADE
);