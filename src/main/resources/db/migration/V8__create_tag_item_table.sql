CREATE TABLE "tag_item" (
    "item_id" UUID NOT NULL,
    "tag_id" UUID NOT NULL,
    PRIMARY KEY ("item_id", "tag_id"),
    CONSTRAINT fk_item_id FOREIGN KEY ("item_id") REFERENCES "item"("id") ON DELETE CASCADE,
    CONSTRAINT fk_tag_id FOREIGN KEY ("tag_id") REFERENCES "tag"("id") ON DELETE CASCADE
);