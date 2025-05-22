-- Insert users (passwords are intentionally weak for training purposes)
-- Default password for all users is 'password'
INSERT INTO users (username, password, email, first_name, last_name, enabled, points)
VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@haxquarium.com', 'Admin', 'User', true, 0),
('user', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'user@haxquarium.com', 'Regular', 'User', true, 0),
('manager', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'manager@haxquarium.com', 'Manager', 'User', true, 0);

-- Insert user roles
INSERT INTO user_roles (user_id, role)
VALUES
(1, 'ROLE_ADMIN'),
(1, 'ROLE_USER'),
(2, 'ROLE_USER'),
(3, 'ROLE_MANAGER'),
(3, 'ROLE_USER');

-- Insert product categories
INSERT INTO categories (name, description)
VALUES
('Fish', 'Live fish for your aquarium'),
('Tanks', 'Aquariums of various sizes'),
('Equipment', 'Filters, pumps, and other equipment'),
('Decorations', 'Plants, rocks, and other decorations'),
('Food', 'Fish food and supplements');

-- Insert products
INSERT INTO products (name, description, price, image_url, stock, category_id)
VALUES
('Goldfish', 'Common goldfish, perfect for beginners', 5.99, '/images/products/goldfish.svg', 50, 1),
('Betta Fish', 'Siamese fighting fish with vibrant colors', 15.99, '/images/products/betta.svg', 30, 1),
('Guppy', 'Colorful and easy to care for', 3.99, '/images/products/guppy.svg', 100, 1),
('10 Gallon Tank', 'Standard glass aquarium, 10 gallon capacity', 29.99, '/images/products/10gallon.svg', 20, 2),
('20 Gallon Tank', 'Standard glass aquarium, 20 gallon capacity', 49.99, '/images/products/20gallon.svg', 15, 2),
('Power Filter', 'Efficient filtration for tanks up to 30 gallons', 24.99, '/images/products/filter.svg', 25, 3),
('Air Pump', 'Quiet operation air pump for aquarium oxygenation', 19.99, '/images/products/airpump.svg', 30, 3),
('Aquarium Heater', 'Submersible heater with adjustable temperature', 22.99, '/images/products/heater.svg', 20, 3),
('Artificial Plants', 'Set of 5 realistic plastic plants', 12.99, '/images/products/plants.svg', 40, 4),
('Aquarium Gravel', '5 pound bag of natural colored gravel', 8.99, '/images/products/gravel.svg', 50, 4),
('Decorative Castle', 'Medieval castle decoration for aquariums', 18.99, '/images/products/castle.svg', 15, 4),
('Premium Fish Flakes', 'Nutritionally complete daily food, 2oz container', 6.99, '/images/products/flakes.svg', 60, 5),
('Freeze Dried Bloodworms', 'High protein treat for tropical fish', 9.99, '/images/products/bloodworms.svg', 40, 5),
('Algae Wafers', 'Sinking wafers for bottom feeders', 7.99, '/images/products/wafers.svg', 45, 5);

-- Insert vulnerability flags for OWASP Top 10 2021
INSERT INTO vulnerability_flags (vulnerability_name, owasp_category, flag_uuid, points, description, remediation, difficulty)
VALUES
('Admin Access', 'A01:2021-Broken Access Control', '38f1e2a7-9c24-4b29-b2e0-585f4b9f754a', 100,
 'This vulnerability allows unauthorized users to access admin functionality.',
 'Implement proper authorization checks and follow the principle of least privilege.',
 'EASY'),

('IDOR Vulnerability', 'A01:2021-Broken Access Control', 'f8b3e58d-c438-4b9f-a7c5-c2d3f0e3c4f1', 150,
 'Insecure Direct Object References allow attackers to access unauthorized resources by manipulating identifiers.',
 'Implement proper access control checks for each resource access.',
 'MEDIUM'),

('Plaintext Storage', 'A02:2021-Cryptographic Failures', 'a1c2e3b4-5d6e-7f8a-9b0c-1d2e3f4a5b6c', 100,
 'Sensitive data is stored in plaintext, exposing it to unauthorized access.',
 'Use strong encryption for sensitive data and secure key management.',
 'EASY'),

('Weak Encryption', 'A02:2021-Cryptographic Failures', 'c4d5e6f7-8a9b-0c1d-2e3f-4a5b6c7d8e9f', 150,
 'Data is encrypted using weak or outdated algorithms.',
 'Use modern, strong encryption algorithms and proper key management.',
 'MEDIUM'),

('SQL Injection', 'A03:2021-Injection', '1a2b3c4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d', 200,
 'SQL injection allows attackers to execute arbitrary SQL commands on the database.',
 'Use parameterized queries or prepared statements to prevent SQL injection.',
 'MEDIUM'),

('Command Injection', 'A03:2021-Injection', 'd1e2f3a4-b5c6-d7e8-f9a0-b1c2d3e4f5a6', 250,
 'Command injection allows attackers to execute arbitrary system commands.',
 'Avoid using system commands with user input, or properly sanitize and validate all inputs.',
 'HARD'),

('Price Manipulation', 'A04:2021-Insecure Design', 'b1c2d3e4-f5a6-b7c8-d9e0-f1a2b3c4d5e6', 150,
 'Business logic flaws allow manipulating product prices during checkout.',
 'Implement server-side validation of prices and use signed or encrypted price data.',
 'MEDIUM'),

('No Rate Limiting', 'A04:2021-Insecure Design', 'e1f2a3b4-c5d6-e7f8-a9b0-c1d2e3f4a5b6', 100,
 'Lack of rate limiting allows brute force attacks and resource exhaustion.',
 'Implement proper rate limiting for sensitive operations.',
 'EASY'),

('Verbose Errors', 'A05:2021-Security Misconfiguration', 'f1a2b3c4-d5e6-f7a8-b9c0-d1e2f3a4b5c6', 100,
 'Detailed error messages reveal sensitive information about the application.',
 'Use generic error messages for users and log detailed errors server-side.',
 'EASY'),

('Default Credentials', 'A05:2021-Security Misconfiguration', 'a1b2c3d4-e5f6-a7b8-c9d0-e1f2a3b4c5d6', 150,
 'Default or weak credentials are used for administrative access.',
 'Change default credentials and implement strong password policies.',
 'MEDIUM'),

('Vulnerable Library', 'A06:2021-Vulnerable and Outdated Components', 'b5c6d7e8-f9a0-b1c2-d3e4-f5a6b7c8d9e0', 200,
 'The application uses a library with known security vulnerabilities.',
 'Regularly update dependencies and use tools to scan for vulnerable components.',
 'MEDIUM'),

('Weak Password Policy', 'A07:2021-Identification and Authentication Failures', 'c6d7e8f9-a0b1-c2d3-e4f5-a6b7c8d9e0f1', 100,
 'The application allows weak passwords that can be easily guessed or brute-forced.',
 'Implement strong password policies and consider multi-factor authentication.',
 'EASY'),

('Improper Session Management', 'A07:2021-Identification and Authentication Failures', 'd7e8f9a0-b1c2-d3e4-f5a6-b7c8d9e0f1a2', 150,
 'Session tokens are not properly managed, allowing session hijacking or fixation.',
 'Use secure session management with proper timeout, rotation, and invalidation.',
 'MEDIUM'),

('Insecure Deserialization', 'A08:2021-Software and Data Integrity Failures', 'e8f9a0b1-c2d3-e4f5-a6b7-c8d9e0f1a2b3', 250,
 'The application deserializes untrusted data without proper validation.',
 'Validate and sanitize data before deserialization or use safer data formats.',
 'HARD'),

('Insufficient Logging', 'A09:2021-Security Logging and Monitoring Failures', 'f9a0b1c2-d3e4-f5a6-b7c8-d9e0f1a2b3c4', 100,
 'The application does not log security-relevant events properly.',
 'Implement comprehensive logging for security events and regularly review logs.',
 'EASY'),

('SSRF Vulnerability', 'A10:2021-Server-Side Request Forgery', 'a0b1c2d3-e4f5-a6b7-c8d9-e0f1a2b3c4d5', 200,
 'The application makes requests to arbitrary URLs controlled by an attacker.',
 'Validate and sanitize URLs, use allowlists, and restrict network access.',
 'MEDIUM');
