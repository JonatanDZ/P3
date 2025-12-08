-- MOCK DATA

-- --- DEPARTMENTS ---
INSERT INTO department (name, is_dev)
VALUES
    ('DevOps', TRUE),
    ('Frontend', TRUE),
    ('Games', TRUE),
    ('Players', TRUE),
    ('Promotions', TRUE),
    ('HR', FALSE),
    ('Legal', FALSE);

-- --- EMPLOYEES ---
INSERT INTO employee (initials, name, department_id, email)
VALUES
    ('JADE', 'John Admin Devops',       1, 'jade@example.com'),
    ('BAFR', 'Bob Admin Frontend',      2, 'bafr@example.com'),
    ('MAGA', 'Mikkel Admin Games',      3, 'maga@example.com'),
    ('JAPL', 'Jane Admin Players',      4, 'japl@example.com'),
    ('JAPR', 'John Admin Promotions',   5, 'japr@example.com'),
    ('MAHR', 'Mikael Admin Hr',         6, 'mahr@example.com'),
    ('LALE', 'Lone Admin Legal',        7, 'lale@example.com'),
    ('JODO', 'John Doe',                1, 'jodo@example.com'),
    ('PEDO', 'Peter Donaldsen',         2, 'pedo@example.com'),
    ('MYGN', 'Mikkel Yrstad Gnom',      3, 'mygn@example.com'),
    ('SPCR', 'Søren-Preben Cromwell',   4, 'spcr@example.com'),
    ('DEPE', 'Dennis Pelvis',           5, 'depe@example.com'),
    ('MOVO', 'Morten Voldumgaard',      6, 'movo@example.com'),
    ('BEKO', 'Beelzebub Komseda',       7, 'beko@example.com');

-- --- JURISDICTIONS ---
INSERT INTO jurisdiction (name)
VALUES
    ('DK'),
    ('UK');

-- --- TOOLS ---
INSERT INTO tool (name, url, is_personal, is_dynamic)
VALUES
    ('Outlook',               'https://outlook.office365.com/mail/', FALSE, FALSE),
    ('Slack',                 'https://slack.com',                   FALSE, FALSE),
    ('Happy Tiger Dev',       '$USER$.greathippydev.co.uk',        FALSE, TRUE),
    ('Happy Tiger Stage',     '$USER$.stage.happytiger.co.uk',     FALSE, TRUE),
    ('Happy Tiger Production','https://happytiger.co.uk',            FALSE, TRUE),
    ('Spil Nu Dev',           '$USER$.lupinsdev.dk',               FALSE, TRUE),
    ('Spil Nu Stage',         '$USER$.stage.spilnu.dk',            FALSE, TRUE),
    ('Spil Nu Production',    'https://spilnu.dk',                   FALSE, TRUE);

-- --- DEPARTMENT ↔ TOOL ---
INSERT INTO department_tool (department_id, tool_id)
VALUES
-- Outlook to every department
(1, 1),(2, 1),(3, 1),(4, 1),(5, 1),(6, 1),(7, 1),

-- Slack to every department
(1, 2),(2, 2),(3, 2),(4, 2),(5, 2),(6, 2),(7, 2),

-- Happy Tiger Dev to all dev departments
(1, 3),(2, 3),(3, 3),(4, 3),(5, 3),

-- Happy Tiger Stage to all dev departments
(1, 4),(2,  4),(3, 4),(4, 4),(5, 4),

-- Happy Tiger Production to every department
(1, 5),(2, 5),(3, 5),(4, 5),(5, 5),(6, 5),(7, 5),

-- Spil Nu Dev to all dev departments
(1, 6),(2, 6),(3, 6),(4, 6),(5, 6),

-- Spil Nu Stage to all dev departments
(1, 7),(2, 7),(3, 7),(4, 7),(5, 7),

-- Spil Nu Production to every department
(1, 8),(2, 8),(3, 8),(4, 8),(5, 8),(6, 8),(7, 8);

-- --- STAGES ---
INSERT INTO stage (id, name)
VALUES
    (1, 'Development'),
    (2, 'Staging'),
    (3, 'Production');

-- --- STAGE ↔ TOOL ---
INSERT INTO tool_stage (stage_id, tool_id)
VALUES
-- Outlook → all stages
(1, 1),(2, 1),(3, 1),
-- Slack → all stages
(1, 2),(2, 2),(3, 2),
-- Happy Tiger
(1, 3),    -- Dev
(2, 4),    -- Stage
(3, 5),    -- Prod
-- Spil Nu
(1, 6),    -- Dev
(2, 7),    -- Stage
(3, 8);    -- Prod

-- --- JURISDICTION ↔ TOOL ---
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
VALUES
-- Global tools → DK & UK
(1, 1),(2, 1),
(1, 2),(2, 2),

-- Spil Nu (DK)
(1, 6),(1, 7),(1, 8),

-- Happy Tiger (UK)
(2, 3),(2, 4),(2, 5);

-- --- FAVORITE TOOLS (non-admins; using initials PK) ---
INSERT INTO favorite_tool (employee_initials, tool_id)
VALUES
-- John Doe (JODO)
('JODO', 1),('JODO', 2),('JODO', 3),('JODO', 4),('JODO', 6),

-- Peter Donaldsen (PEDO)
('PEDO', 1),('PEDO', 2),('PEDO', 4),('PEDO', 5),('PEDO', 7),

-- Mikkel Yrstad Gnom (MYGN)
('MYGN', 1),('MYGN', 2),('MYGN', 3),('MYGN', 5),('MYGN', 6),

-- Søren-Preben Cromwell (SPCR)
('SPCR', 1),('SPCR', 2),('SPCR', 4),('SPCR', 5),('SPCR', 8),

-- Dennis Pelvis (DEPE)
('DEPE', 1),('DEPE', 2),('DEPE', 4),('DEPE', 5),('DEPE', 7),

-- Morten Voldumgaard (MOVO)
('MOVO', 1),('MOVO', 2),('MOVO', 5),('MOVO', 8),

-- Beelzebub Komseda (BEKO)
('BEKO', 1),('BEKO', 2),('BEKO', 5),('BEKO', 8);