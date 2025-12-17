-- Create branch_sales table
CREATE TABLE IF NOT EXISTS branch_sales (
    id BIGSERIAL PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(12, 2) NOT NULL,
    total_amount NUMERIC(14, 2) NOT NULL,
    sale_time TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_branch_sales_branch FOREIGN KEY (branch_id) REFERENCES branches(id),
    CONSTRAINT fk_branch_sales_product FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Create indexes for faster lookups and queries
CREATE INDEX IF NOT EXISTS idx_branch_sales_branch_id ON branch_sales(branch_id);
CREATE INDEX IF NOT EXISTS idx_branch_sales_product_id ON branch_sales(product_id);
CREATE INDEX IF NOT EXISTS idx_branch_sales_sale_time ON branch_sales(sale_time);
CREATE INDEX IF NOT EXISTS idx_branch_sales_branch_sale_time ON branch_sales(branch_id, sale_time);

