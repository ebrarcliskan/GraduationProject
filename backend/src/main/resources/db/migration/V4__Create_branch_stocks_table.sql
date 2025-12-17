-- Create branch_stocks table
CREATE TABLE IF NOT EXISTS branch_stocks (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_branch_stocks_branch FOREIGN KEY (branch_id) REFERENCES branches(id),
    CONSTRAINT fk_branch_stocks_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT uk_branch_stocks_branch_product UNIQUE (branch_id, product_id)
);

-- Create indexes for faster lookups
CREATE INDEX IF NOT EXISTS idx_branch_stocks_branch_id ON branch_stocks(branch_id);
CREATE INDEX IF NOT EXISTS idx_branch_stocks_product_id ON branch_stocks(product_id);

