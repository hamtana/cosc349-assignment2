-- Insert data into Manager table
INSERT INTO Manager (id, firstName, lastName, phoneNumber, username, password) VALUES
('M001', 'Alice', 'Johnson', '1234567890', 'alicej', 'password123'),
('M002', 'Bob', 'Smith', '0987654321', 'bobsmith', 'password456');

-- Insert data into Tenant table
INSERT INTO Tenant (id, firstName, lastName, phoneNumber, username, password) VALUES
('T001', 'Charlie', 'Brown', '2345678901', 'charlieb', 'password789'),
('T002', 'Daisy', 'Duke', '3456789012', 'daisyduke', 'password012');

-- Insert data into Property table
INSERT INTO Property (id, name, address, tenant_username) VALUES
(1, 'Green Villa', '123 Elm St', 'bob'),
(1, 'Sunny Apartments', '456 Oak St', 'bob');

-- Insert data into Request table
INSERT INTO Request (id, name, description, urgent, tenant_username) VALUES
('R001', 'Leaky Faucet', 'The kitchen faucet is leaking and needs urgent repair.', TRUE, 'Bob'),
('R002', 'Broken Window', 'The living room window is broken and needs replacement.', FALSE, 'Bob');
