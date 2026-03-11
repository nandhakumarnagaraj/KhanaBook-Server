-- KhanaBook Optimization: Composite Indexes for Sync Performance
-- Run these on your PostgreSQL production database

-- 1. Sales & Revenue
CREATE INDEX idx_bills_sync ON bills (restaurant_id, server_updated_at);
CREATE INDEX idx_bill_items_sync ON bill_items (restaurant_id, server_updated_at);
CREATE INDEX idx_bill_payments_sync ON bill_payments (restaurant_id, server_updated_at);

-- 2. Menu Configuration
CREATE INDEX idx_categories_sync ON categories (restaurant_id, server_updated_at);
CREATE INDEX idx_menu_items_sync ON menu_items (restaurant_id, server_updated_at);
CREATE INDEX idx_item_variants_sync ON item_variants (restaurant_id, server_updated_at);

-- 3. Inventory & Supply Chain
CREATE INDEX idx_rawmaterials_sync ON rawmaterials (restaurant_id, server_updated_at);
CREATE INDEX idx_material_batches_sync ON material_batches (restaurant_id, server_updated_at);
CREATE INDEX idx_recipe_ingredients_sync ON recipe_ingredients (restaurant_id, server_updated_at);
CREATE INDEX idx_stock_logs_sync ON stock_logs (restaurant_id, server_updated_at);
CREATE INDEX idx_raw_stock_logs_sync ON raw_material_stock_logs (restaurant_id, server_updated_at);

-- 4. Administration
CREATE INDEX idx_users_sync ON users (restaurant_id, server_updated_at);
CREATE INDEX idx_restaurant_profile_sync ON restaurant_profile (restaurant_id, server_updated_at);

-- 5. Primary Key Optimizations (Composite Support)
-- Already handled by PK constraints usually, but these help push reconciliation
CREATE INDEX idx_bills_device_local ON bills (restaurant_id, device_id, local_id);
