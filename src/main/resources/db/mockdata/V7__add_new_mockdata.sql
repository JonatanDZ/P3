-- V7__refresh_tool_mock_data.sql
-- Refresh mock data for tools, tags, and all tool relations.
-- Assumes MySQL.

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE favorite_tool;
TRUNCATE TABLE tool_tag;
TRUNCATE TABLE tool_stage;
TRUNCATE TABLE tool_jurisdiction;
TRUNCATE TABLE department_tool;
TRUNCATE TABLE tool;
TRUNCATE TABLE tag;

SET FOREIGN_KEY_CHECKS = 1;

-- ---------------------------------------------------------------------
-- TOOLS
-- ---------------------------------------------------------------------
-- Note: created_by references employee.initials from V2 (JADE, BAFR, MAGA, BEKO, PEDO, etc.)

INSERT INTO tool (name, url, is_personal, is_dynamic, pending, created_by)
VALUES
    -- Core communication & productivity
    ('Outlook',                    'https://outlook.office365.com/mail/', FALSE, FALSE, FALSE, 'JADE'),
    ('Slack',                      'https://slack.com',                   FALSE, FALSE, FALSE, 'JADE'),

    -- Dev / project tools
    ('Jira',                       'https://jira.company',                FALSE, FALSE, FALSE, 'MAGA'),
    ('Confluence',                 'https://confluence.company',          FALSE, FALSE, FALSE, 'MAGA'),
    ('GitHub',                     'https://github.com/our-org',          FALSE, TRUE,  FALSE, 'JADE'),
    ('Grafana',                    'https://grafana.company',             FALSE, TRUE,  FALSE, 'MAGA'),

    -- Business / HR / design
    ('HR Portal',                  'https://hr.company',                  FALSE, FALSE, FALSE, 'BEKO'),
    ('Design System Storybook',    'https://storybook.company',           FALSE, TRUE,  FALSE, 'BAFR'),

    -- Pending / experimental tools (pending = TRUE)
    ('Feature Flag Dashboard',     'https://flags.company',               FALSE, TRUE,  TRUE,  'JADE'),
    ('SQL Runner',                 'https://sqlrunner.company',           FALSE, FALSE, TRUE,  'PEDO'),
    ('Legal Review Portal',        'https://legal.company/review',        FALSE, FALSE, TRUE,  'LALE');

-- ---------------------------------------------------------------------
-- TAGS
-- ---------------------------------------------------------------------
INSERT INTO tag (value)
VALUES
    ('Communication'),
    ('Office'),
    ('Project Management'),
    ('Documentation'),
    ('Monitoring'),
    ('HR'),
    ('Dev Tools'),
    ('Design');

-- ---------------------------------------------------------------------
-- TOOL ↔ TAG
-- Use names/values instead of hard-coded IDs
-- ---------------------------------------------------------------------

-- Outlook → Office, Communication
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg ON t.name = 'Outlook' AND tg.value IN ('Office', 'Communication');

-- Slack → Communication
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg ON t.name = 'Slack' AND tg.value = 'Communication';

-- Jira → Project Management, Dev Tools
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg ON t.name = 'Jira' AND tg.value IN ('Project Management', 'Dev Tools');

-- Confluence → Documentation
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg ON t.name = 'Confluence' AND tg.value = 'Documentation';

-- GitHub → Dev Tools
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg ON t.name = 'GitHub' AND tg.value = 'Dev Tools';

-- Grafana → Monitoring, Dev Tools
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg ON t.name = 'Grafana' AND tg.value IN ('Monitoring', 'Dev Tools');

-- HR Portal → HR
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg ON t.name = 'HR Portal' AND tg.value = 'HR';

-- Storybook → Design, Dev Tools
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg ON t.name = 'Design System Storybook' AND tg.value IN ('Design', 'Dev Tools');

-- Feature Flag Dashboard → Dev Tools
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg ON t.name = 'Feature Flag Dashboard' AND tg.value = 'Dev Tools';

-- SQL Runner → Dev Tools
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg ON t.name = 'SQL Runner' AND tg.value = 'Dev Tools';

-- Legal Review Portal → Documentation
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg ON t.name = 'Legal Review Portal' AND tg.value = 'Documentation';

-- ---------------------------------------------------------------------
-- DEPARTMENT ↔ TOOL
-- Uses department.is_dev + department.name and tool.name
-- ---------------------------------------------------------------------

-- Outlook & Slack for every department
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t ON t.name IN ('Outlook', 'Slack');

-- Jira, Confluence, GitHub, Grafana & Storybook only for dev departments (is_dev = TRUE)
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t ON t.name IN ('Jira', 'Confluence', 'GitHub', 'Grafana', 'Design System Storybook')
WHERE d.is_dev = TRUE;

-- HR Portal only for HR department
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t ON t.name = 'HR Portal'
WHERE d.name = 'HR';

-- Legal Review Portal only for Legal department
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t ON t.name = 'Legal Review Portal'
WHERE d.name = 'Legal';

-- Feature Flag Dashboard & SQL Runner only for DevOps department
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t ON t.name IN ('Feature Flag Dashboard', 'SQL Runner')
WHERE d.name = 'DevOps';

-- ---------------------------------------------------------------------
-- STAGE ↔ TOOL
-- Uses stage.name + tool.name
-- ---------------------------------------------------------------------

-- Outlook & Slack → all stages
INSERT INTO tool_stage (stage_id, tool_id)
SELECT s.id, t.id
FROM stage s
         JOIN tool t ON t.name IN ('Outlook', 'Slack');

-- Jira, Confluence, GitHub, Storybook → Development & Staging
INSERT INTO tool_stage (stage_id, tool_id)
SELECT s.id, t.id
FROM stage s
         JOIN tool t ON t.name IN ('Jira', 'Confluence', 'GitHub', 'Design System Storybook')
WHERE s.name IN ('Development', 'Staging');

-- Grafana → all stages (monitoring everywhere)
INSERT INTO tool_stage (stage_id, tool_id)
SELECT s.id, t.id
FROM stage s
         JOIN tool t ON t.name = 'Grafana';

-- HR Portal → all stages
INSERT INTO tool_stage (stage_id, tool_id)
SELECT s.id, t.id
FROM stage s
         JOIN tool t ON t.name = 'HR Portal';

-- Pending tools only on Development
INSERT INTO tool_stage (stage_id, tool_id)
SELECT s.id, t.id
FROM stage s
         JOIN tool t ON s.name = 'Development'
WHERE t.pending = TRUE;

-- ---------------------------------------------------------------------
-- JURISDICTION ↔ TOOL
-- Uses jurisdiction.name + tool.name
-- ---------------------------------------------------------------------

-- Outlook & Slack → DK & UK
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t ON t.name IN ('Outlook', 'Slack')
WHERE j.name IN ('DK', 'UK');

-- Jira, Confluence, GitHub, Grafana, Storybook → DK & UK (global dev tools)
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t ON t.name IN ('Jira', 'Confluence', 'GitHub', 'Grafana', 'Design System Storybook')
WHERE j.name IN ('DK', 'UK');

-- HR Portal → DK & UK
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t ON t.name = 'HR Portal'
WHERE j.name IN ('DK', 'UK');

-- Feature Flag Dashboard → DK only (example of DK-only tool)
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t ON t.name = 'Feature Flag Dashboard'
WHERE j.name = 'DK';

-- SQL Runner → DK only
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t ON t.name = 'SQL Runner'
WHERE j.name = 'DK';

-- Legal Review Portal → UK only
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t ON t.name = 'Legal Review Portal'
WHERE j.name = 'UK';

-- ---------------------------------------------------------------------
-- FAVORITE TOOLS
-- Uses employee.initials + tool.name
-- ---------------------------------------------------------------------

-- John Doe (JODO)
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'JODO', t.id FROM tool t WHERE t.name = 'Slack';
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'JODO', t.id FROM tool t WHERE t.name = 'Jira';
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'JODO', t.id FROM tool t WHERE t.name = 'GitHub';

-- Peter Donaldsen (PEDO)
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'PEDO', t.id FROM tool t WHERE t.name = 'Jira';
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'PEDO', t.id FROM tool t WHERE t.name = 'Confluence';
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'PEDO', t.id FROM tool t WHERE t.name = 'SQL Runner';

-- Mikkel Yrstad Gnom (MYGN)
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'MYGN', t.id FROM tool t WHERE t.name = 'GitHub';
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'MYGN', t.id FROM tool t WHERE t.name = 'Grafana';

-- Søren-Preben Cromwell (SPCR)
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'SPCR', t.id FROM tool t WHERE t.name = 'Design System Storybook';
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'SPCR', t.id FROM tool t WHERE t.name = 'Slack';

-- Dennis Pelvis (DEPE)
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'DEPE', t.id FROM tool t WHERE t.name = 'Outlook';
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'DEPE', t.id FROM tool t WHERE t.name = 'HR Portal';

-- Beelzebub Komseda (BEKO)
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'BEKO', t.id FROM tool t WHERE t.name = 'HR Portal';
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'BEKO', t.id FROM tool t WHERE t.name = 'Legal Review Portal';
