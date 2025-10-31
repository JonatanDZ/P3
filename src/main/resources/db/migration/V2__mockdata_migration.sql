
-- --- DEPARTMENTS ---
INSERT INTO department (department_id, department_name, is_dev)
VALUES
    (1, 'DevOps', TRUE),
    (2, 'Frontend', TRUE),
    (3, 'Games', TRUE),
    (4, 'Players', TRUE),
    (5, 'Promotions', TRUE),
    (6, 'HR', FALSE),
    (7, 'Legal', FALSE);

-- --- USERS ---
INSERT INTO employees (employee_id, name, initials, department_id, is_admin)
VALUES
    (1, 'John Admin Devops', 'JADE', 1, TRUE),
    (2, 'Bob Admin Frontend', 'BAFR', 2, TRUE),
    (3, 'Mikkel Admin Games', 'MAGA', 3, TRUE),
    (4, 'Jane Admin Players', 'JAPL', 4, TRUE),
    (5, 'John Admin Promotions', 'JAPR', 5, TRUE),
    (6, 'Mikael Admin Hr', 'MAHR', 6, TRUE),
    (7, 'Lone Admin Legal', 'LALE', 7, TRUE),
    (8, 'John Doe', 'JODO', 1, FALSE),
    (9, 'Peter Donaldsen', 'PEDO', 2, FALSE),
    (10, 'Mikkel Yrstad Gnom', 'MYGN', 3, FALSE),
    (11, 'Søren-Preben Cromwell', 'SPCR', 4, FALSE),
    (12, 'Dennis Pelvis', 'DEPE', 5, FALSE),
    (13, 'Morten Voldumgaard', 'MOVO', 6, FALSE),
    (14, 'Beelzebub Komseda', 'BEKO', 7, FALSE);


-- --- JURISDICTIONS ---
INSERT INTO jurisdiction (jurisdiction_id, jurisdiction_name)
VALUES
    (1, 'DK'),
    (2, 'UK');

-- --- TOOLS ---
INSERT INTO tool (tool_id, name, url, is_dynamic)
VALUES
    (1, 'Outlook', 'https://outlook.office365.com/mail/', FALSE),
    (2, 'Slack', 'https://slack.com', FALSE),
    (3, 'Happy Tiger Dev', 'initials.greathippydev.co.uk', TRUE),
    (4, 'Happy Tiger Stage', 'initials.stage.happytiger.co.uk', TRUE),
    (5, 'Happy Tiger Production', 'https://happytiger.co.uk', TRUE),
    (6, 'Spil Nu Dev', 'initials.lupinsdev.dk', TRUE),
    (7, 'Spil Nu Stage', 'initials.stage.spilnu.dk', TRUE),
    (8, 'Spil Nu Production', 'https://spilnu.dk', TRUE);



-- --- DEPARTMENT ↔ TOOL ---
INSERT INTO department_tool (department, tool_id)
VALUES
-- given that a department_tool is global, i.e every department has access, it has to be uploaded department.length amount of times
(1, 1), -- DevOps - Outlook
(2, 1), -- Frontend - Outlook
(3, 1), -- Games - Outlook
(4, 1), -- Players - Outlook
(5, 1), -- Promotions - Outlook
(6, 1), -- HR - Outlook
(7, 1), -- Legal - Outlook

-- Slack to every department
(1, 2), -- DevOps - Slack
(2, 2), -- Frontend - Slack
(3, 2), -- Games - Slack
(4, 2), -- Players - Slack
(5, 2), -- Promotions - Slack
(6, 2), -- HR - Slack
(7, 2), -- Legal - Slack

-- Happy Tiger
-- Happy Tiger Dev to all dev departments
(1, 3), -- DevOps - Happy Tiger Dev
(2, 3), -- Frontend - Happy Tiger Dev
(3, 3), -- Games - Happy Tiger Dev
(4, 3), -- Players - Happy Tiger Dev
(5, 3), -- Promotions - Happy Tiger Dev

-- Happy Tiger Stage to all dev departments
(1, 4), -- DevOps - Happy Tiger Stage
(2, 4), -- Frontend - Happy Tiger Stage
(3, 4), -- Games - Happy Tiger Stage
(4, 4), -- Players - Happy Tiger Stage
(5, 4), -- Promotions - Happy Tiger Stage

-- Happy Tiger Production to every department
(1, 5), -- DevOps - Happy Tiger Production
(2, 5), -- Frontend - Happy Tiger Production
(3, 5), -- Games - Happy Tiger Production
(4, 5), -- Players - Happy Tiger Production
(5, 5), -- Promotions - Happy Tiger Production
(6, 5), -- HR - Happy Tiger Production
(7, 5), -- Legal - Happy Tiger Production

-- Spil Nu
-- Spil Nu Dev to all dev departments
(1, 6), -- DevOps - Spil Nu Dev
(2, 6), -- Frontend - Spil Nu Dev
(3, 6), -- Games - Spil Nu Dev
(4, 6), -- Players - Spil Nu Dev
(5, 6), -- Promotions - Spil Nu Dev

-- Spil Nu Stage to all dev departments
(1, 7), -- DevOps - Spil Nu Stage
(2, 7), -- Frontend - Spil Nu Stage
(3, 7), -- Games - Spil NuStage
(4, 7), -- Players - Spil Nu Stage
(5, 7), -- Promotions - Spil Nu Stage

-- Spil Nu Production to every department
(1, 8), -- DevOps - Spil Nu Production
(2, 8), -- Frontend - Spil Nu Production
(3, 8), -- Games - Spil Nu Production
(4, 8), -- Players - Spil Nu Production
(5, 8), -- Promotions - Spil Nu Production
(6, 8), -- HR - Spil Nu Production
(7, 8); -- Legal - Spil Nu Production

-- --- STAGES ---
INSERT INTO stage (stage_id, name)
VALUES
    (1, 'Development'),
    (2, 'Staging'),
    (3, 'Production');

-- --- STAGE ↔ TOOL ---
INSERT INTO stage_tools (tool_id, stage_id)
VALUES
-- Global tools
-- Outlook → all stages (Dev, Staging, Prod)
(1, 1),
(1, 2),
(1, 3),
-- Slack → all stages
(2, 1),
(2, 2),
(2, 3),

-- Happy Tiger Dev → Development
(3, 1),
-- Happy Tiger Stage → Staging
(4, 2),
-- Happy Tiger Production → Production
(5, 3),

-- Spil Nu Dev → Development
(6, 1),
-- Spil Nu Stage → Staging
(7, 2),
-- Spil Nu Production → Production
(8, 3);

-- --- JURISDICTION ↔ TOOL ---
INSERT INTO jurisdiction_tools (tool_id, jurisdiction_id)
VALUES
-- Global tools → both DK & UK
(1, 1), -- Outlook DK
(1, 2), -- Outlook UK
(2, 1), -- Slack DK
(2, 2), -- Slack UK

-- Spil Nu Dev → DK
(6, 1),
-- Spil Nu Stage → DK
(7, 1),
-- Spil Nu Production → DK
(8, 1),

-- Happy Tiger Dev → UK
(3, 2),
-- Happy Tiger Stage → UK
(4, 2),
-- Happy Tiger Production → UK
(5, 2);


-- --- FAVORITE TOOLS (expanded for non-admins) ---
INSERT INTO favorite_tools (employee_id, tool_id)
VALUES
-- User 8 John Doe
(8, 1), -- Outlook
(8, 2), -- Slack
(8, 3), -- Happy Tiger Dev
(8, 4), -- Happy Tiger Stage
(8, 6), -- Spil Nu Dev

-- User 9 Peter Donaldsen
(9, 1), -- Outlook
(9, 2), -- Slack
(9, 4), -- Happy Tiger Stage
(9, 5), -- Happy Tiger Production
(9, 7), -- Spil Nu Stage

-- User 10 Mikkel Yrstad Gnom
(10, 1), -- Outlook
(10, 2), -- Slack
(10, 3), -- Happy Tiger Dev
(10, 5), -- Happy Tiger Production
(10, 6), -- Spil Nu Dev


-- User 11 Søren-Preben Cromwell
(11, 1), -- Outlook
(11, 2), -- Slack
(11, 4), -- Happy Tiger Stage
(11, 5), -- Happy Tiger Production
(11, 8), -- Spil Nu Production



-- User 12 Dennis Pelvis
(12, 1), -- Outlook
(12, 2), -- Slack
(12, 4), -- Happy Tiger Stage
(12, 5), -- Happy Tiger Production
(12, 7), -- Spil Nu Stage


-- User 13 Morten Voldumgaard
(13, 1), -- Outlook
(13, 2), -- Slack
(13, 5), -- Happy Tiger Production
(13, 8), -- Spil Nu Production

-- User 14 Beelzebub
(14, 1), -- Outlook
(14, 2), -- Slack
(14, 5), -- Happy Tiger Production
(14, 8); -- Spil Nu Production