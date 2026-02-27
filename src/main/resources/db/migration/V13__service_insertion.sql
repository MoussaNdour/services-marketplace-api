-- Services pour IT & Software
INSERT INTO service (name, description , category)
SELECT 'Custom Web Feature Dev', 'Development of specific, responsive web components.', id
FROM category WHERE name = 'IT & Software';

INSERT INTO service (name, description , category)
SELECT 'Database Optimization', 'SQL query tuning and schema refactoring for performance.',  id
FROM category WHERE name = 'IT & Software';

-- Services pour Design
INSERT INTO service (name, description , category)
SELECT 'Brand Identity Pack', 'Logo creation, color palette, and social media templates.',  id
FROM category WHERE name = 'Design & Multimedia';

-- Services pour Engineering
INSERT INTO service (name, description , category)
SELECT '3D CAD Modeling', 'Technical drafting and 3D modeling for mechanical parts.',  id
FROM category WHERE name = 'Engineering';

-- Services pour Healthcare
INSERT INTO service (name, description , category)
SELECT 'Workplace Ergonomics Audit', 'Personalized assessment of your office setup.',  id
FROM category WHERE name = 'Healthcare & Wellness';

-- Services pour Writing
INSERT INTO service (name, description , category)
SELECT 'Content Proofreading', 'Professional editing for blog posts and whitepapers.',  id
FROM category WHERE name = 'Writing & Translation';