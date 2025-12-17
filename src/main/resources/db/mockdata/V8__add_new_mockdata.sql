-- V8__add_new_mockdata.sql
-- Fresh mock data for tools + relations
-- DB: MySQL

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
-- Employees from V2:
-- JADE, BAFR, MAGA, JAPL, JAPR, MAHR, LALE, JODO, PEDO, MYGN, SPCR, DEPE, MOVO, BEKO

INSERT INTO tool (name, url, is_personal, is_dynamic, pending, created_by)
VALUES
    -- Common tools (same URL for all stages)
    ('Outlook',                         'https://outlook.office365.com/mail/', FALSE, FALSE, FALSE, 'JADE'),
    ('Slack',                           'https://slack.com',                   FALSE, FALSE, FALSE, 'JADE'),
    ('Notion Workspace',                'https://notion.company',              FALSE, FALSE, FALSE, 'JODO'),
    ('HR Portal',                       'https://hr.company',                  FALSE, FALSE, FALSE, 'BEKO'),
    ('Legal Contract Archive',          'https://legal.company/archive',       FALSE, FALSE, FALSE, 'LALE'),

    -- Happy Tiger – stage name in URL, must be UK-only in jurisdictions
    ('Happy Tiger Player Portal (Development)', 'https://development.$USER$.player.happytiger.internal', FALSE, TRUE, FALSE, 'MAGA'),
    ('Happy Tiger Player Portal (Staging)',     'https://staging.$USER$.player.happytiger.internal',     FALSE, TRUE, FALSE, 'MAGA'),
    ('Happy Tiger Player Portal (Production)',  'https://production.$USER$.player.happytiger.internal',  FALSE, TRUE, FALSE, 'MAGA'),

    -- SpilNu Casino – stage name in URL
    ('SpilNu Casino (Development)',    'https://development.$USER$.casino.spilnu.internal',    FALSE, TRUE, FALSE, 'MAGA'),
    ('SpilNu Casino (Staging)',        'https://staging.$USER$.casino.spilnu.internal',        FALSE, TRUE, FALSE, 'MAGA'),
    ('SpilNu Casino (Production)',     'https://production.$USER$.casino.spilnu.internal',     FALSE, TRUE, FALSE, 'MAGA'),

    -- DevOps / infra tools – stage name in URL
    ('DevOps Kubernetes Dashboard (Development)', 'https://development.$USER$.k8s.devops.internal', FALSE, TRUE, FALSE, 'JADE'),
    ('DevOps Kubernetes Dashboard (Production)',  'https://production.$USER$.k8s.devops.internal',  FALSE, TRUE, FALSE, 'JADE'),

    -- Analytics / BI – stage name in URL
    ('Players Analytics (Staging)',    'https://staging.$USER$.analytics.players.internal',    FALSE, TRUE, FALSE, 'SPCR'),
    ('Players Analytics (Production)', 'https://production.$USER$.analytics.players.internal', FALSE, TRUE, FALSE, 'SPCR'),

    -- Promotions / marketing – stage name in URL
    ('Promotions Campaign Manager (Development)', 'https://development.$USER$.campaigns.promotions.internal', FALSE, TRUE, FALSE, 'JAPR'),
    ('Promotions Campaign Manager (Staging)',     'https://staging.$USER$.campaigns.promotions.internal',     FALSE, TRUE, FALSE, 'JAPR'),
    ('Promotions Campaign Manager (Production)',  'https://production.$USER$.campaigns.promotions.internal',  FALSE, TRUE, FALSE, 'JAPR'),

    -- Experimentation / feature tools – stage name in URL
    ('Feature Flag Dashboard (Development)', 'https://development.$USER$.flags.internal',       FALSE, TRUE, FALSE,  'JADE'),
    ('A/B Test Console (Staging)',          'https://staging.$USER$.abtest.internal',          FALSE, TRUE, TRUE,  'BAFR'),
    ('Canary Release Monitor (Production)', 'https://production.$USER$.canary.internal',       FALSE, TRUE, TRUE,  'JADE'),

    -- Swagger / API docs tools – stage name in URL
    ('Happy Tiger API Docs (Development)', 'https://development.$USER$.api.happytiger.internal/swagger', FALSE, TRUE, FALSE, 'MAGA'),
    ('SpilNu API Docs (Staging)',         'https://staging.$USER$.api.spilnu.internal/swagger',         FALSE, TRUE, FALSE, 'MAGA'),
    ('Platform API Docs (Development)',   'https://development.$USER$.api.platform.internal/swagger',   FALSE, TRUE, FALSE, 'JADE');

-- ---------------------------------------------------------------------
-- TAGS
-- ---------------------------------------------------------------------
INSERT INTO tag (value)
VALUES
    ('Communication'),
    ('Office'),
    ('Player Facing'),
    ('Casino'),
    ('DevOps'),
    ('Monitoring'),
    ('Analytics'),
    ('Promotions'),
    ('HR'),
    ('Legal'),
    ('Experimentation'),
    ('Shared'),
    ('Swagger');

-- ---------------------------------------------------------------------
-- TOOL ↔ TAG
-- ---------------------------------------------------------------------

-- Outlook → Office, Communication, Shared
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON t.name = 'Outlook'
                  AND tg.value IN ('Office', 'Communication', 'Shared');

-- Slack → Communication, Shared
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON t.name = 'Slack'
                  AND tg.value IN ('Communication', 'Shared');

-- Notion → Shared
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON t.name = 'Notion Workspace'
                  AND tg.value = 'Shared';

-- HR Portal → HR
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON t.name = 'HR Portal'
                  AND tg.value = 'HR';

-- Legal Contract Archive → Legal
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON t.name = 'Legal Contract Archive'
                  AND tg.value = 'Legal';

-- Happy Tiger Player Portal → Player Facing, Analytics
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON t.name LIKE 'Happy Tiger Player Portal (%)'
                  AND tg.value IN ('Player Facing', 'Analytics');

-- SpilNu Casino → Casino, Player Facing
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON t.name LIKE 'SpilNu Casino (%)'
                  AND tg.value IN ('Casino', 'Player Facing');

-- DevOps Kubernetes → DevOps, Monitoring
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON t.name LIKE 'DevOps Kubernetes Dashboard (%)'
                  AND tg.value IN ('DevOps', 'Monitoring');

-- Players Analytics → Analytics
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON t.name LIKE 'Players Analytics (%)'
                  AND tg.value = 'Analytics';

-- Promotions Campaign Manager → Promotions, Analytics
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON t.name LIKE 'Promotions Campaign Manager (%)'
                  AND tg.value IN ('Promotions', 'Analytics');

-- Experimentation tools → Experimentation, DevOps
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON t.name IN ('Feature Flag Dashboard (Development)',
                            'A/B Test Console (Staging)',
                            'Canary Release Monitor (Production)')
                  AND tg.value IN ('Experimentation', 'DevOps');

-- Swagger tools → Swagger (+ some extra semantics)
-- All three API docs get the Swagger tag
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON tg.value = 'Swagger'
                  AND t.name IN (
                                 'Happy Tiger API Docs (Development)',
                                 'SpilNu API Docs (Staging)',
                                 'Platform API Docs (Development)'
                      );

-- Happy Tiger / SpilNu API Docs also Player Facing
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON tg.value = 'Player Facing'
                  AND t.name IN (
                                 'Happy Tiger API Docs (Development)',
                                 'SpilNu API Docs (Staging)'
                      );

-- Platform API Docs is DevOps-flavoured
INSERT INTO tool_tag (tool_id, tag_id)
SELECT t.id, tg.id
FROM tool t
         JOIN tag tg
              ON tg.value = 'DevOps'
                  AND t.name = 'Platform API Docs (Development)';

-- ---------------------------------------------------------------------
-- DEPARTMENT ↔ TOOL
-- Different amount of tools per department
-- Departments from V2: DevOps, Frontend, Games, Players, Promotions, HR, Legal
-- ---------------------------------------------------------------------

-- All departments get core shared tools: Outlook, Slack, Notion
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t ON t.name IN ('Outlook', 'Slack', 'Notion Workspace');

-- DevOps extras
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t
WHERE d.name = 'DevOps'
  AND t.name IN (
                 'DevOps Kubernetes Dashboard (Development)',
                 'DevOps Kubernetes Dashboard (Production)',
                 'Feature Flag Dashboard (Development)',
                 'Canary Release Monitor (Production)',
                 'SpilNu Casino (Development)',
                 'SpilNu Casino (Staging)',
                 'Platform API Docs (Development)',
                 'SpilNu API Docs (Staging)',
                 'Happy Tiger API Docs (Development)'
    );

-- Frontend extras
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t
WHERE d.name = 'Frontend'
  AND t.name IN (
                 'Happy Tiger Player Portal (Development)',
                 'Happy Tiger Player Portal (Staging)',
                 'SpilNu Casino (Development)',
                 'SpilNu Casino (Staging)',
                 'A/B Test Console (Staging)',
                 'Happy Tiger API Docs (Development)',
                 'SpilNu API Docs (Staging)'
    );

-- Games extras
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t
WHERE d.name = 'Games'
  AND t.name IN (
                 'SpilNu Casino (Development)',
                 'SpilNu Casino (Staging)',
                 'SpilNu Casino (Production)',
                 'Platform API Docs (Development)',
                 'SpilNu API Docs (Staging)',
                 'Canary Release Monitor (Production)'
    );

-- Players extras
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t
WHERE d.name = 'Players'
  AND t.name IN (
                 'Happy Tiger Player Portal (Development)',
                 'Happy Tiger Player Portal (Staging)',
                 'Happy Tiger Player Portal (Production)',
                 'SpilNu Casino (Staging)',
                 'SpilNu Casino (Production)',
                 'Players Analytics (Staging)',
                 'Players Analytics (Production)',
                 'Happy Tiger API Docs (Development)'
    );

-- Promotions extras
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t
WHERE d.name = 'Promotions'
  AND t.name IN (
                 'Promotions Campaign Manager (Development)',
                 'Promotions Campaign Manager (Staging)',
                 'Promotions Campaign Manager (Production)',
                 'SpilNu Casino (Production)',
                 'A/B Test Console (Staging)',
                 'Players Analytics (Production)'
    );

-- HR extras: HR Portal
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t
WHERE d.name = 'HR'
  AND t.name = 'HR Portal';

-- Legal extras: Legal Contract Archive
INSERT INTO department_tool (department_id, tool_id)
SELECT d.id, t.id
FROM department d
         JOIN tool t
WHERE d.name = 'Legal'
  AND t.name = 'Legal Contract Archive';

-- ---------------------------------------------------------------------
-- STAGE ↔ TOOL
-- Different tools per stage; env-specific names carry "(Development|Staging|Production)"
-- ---------------------------------------------------------------------

-- Common tools → all stages
INSERT INTO tool_stage (stage_id, tool_id)
SELECT s.id, t.id
FROM stage s
         JOIN tool t ON t.name IN (
                                   'Outlook',
                                   'Slack',
                                   'Notion Workspace',
                                   'HR Portal',
                                   'Legal Contract Archive'
    );

-- Development-only tools (name ends with "(Development)")
INSERT INTO tool_stage (stage_id, tool_id)
SELECT s.id, t.id
FROM stage s
         JOIN tool t
WHERE s.name = 'Development'
  AND t.name LIKE '%(Development)';

-- Staging-only tools (name ends with "(Staging)")
INSERT INTO tool_stage (stage_id, tool_id)
SELECT s.id, t.id
FROM stage s
         JOIN tool t
WHERE s.name = 'Staging'
  AND t.name LIKE '%(Staging)';

-- Production-only tools (name ends with "(Production)")
INSERT INTO tool_stage (stage_id, tool_id)
SELECT s.id, t.id
FROM stage s
         JOIN tool t
WHERE s.name = 'Production'
  AND t.name LIKE '%(Production)';

-- ---------------------------------------------------------------------
-- JURISDICTION ↔ TOOL
-- Important: all "Happy Tiger" tools must ONLY be in UK
-- ---------------------------------------------------------------------

-- Common tools → DK & UK
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t
WHERE t.name IN ('Outlook','Slack','Notion Workspace','HR Portal','Legal Contract Archive')
  AND j.name IN ('DK','UK');

-- Happy Tiger tools → UK ONLY
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t
WHERE t.name LIKE 'Happy Tiger%'
  AND j.name = 'UK';

-- SpilNu Casino:
--   Dev & Staging → DK only
--   Production → DK & UK
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t
WHERE t.name IN ('SpilNu Casino (Development)',
                 'SpilNu Casino (Staging)')
  AND j.name = 'DK';

INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t
WHERE t.name = 'SpilNu Casino (Production)'
  AND j.name IN ('DK','UK');

-- SpilNu API Docs (Staging) → DK only
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t
WHERE t.name = 'SpilNu API Docs (Staging)'
  AND j.name = 'DK';

-- Development tools → DK only (infra + platform docs)
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t
WHERE t.name IN ('DevOps Kubernetes Dashboard (Development)',
                 'Feature Flag Dashboard (Development)',
                 'Platform API Docs (Development)')
  AND j.name = 'DK';

-- Staging tools → UK only (staging analytics/promotions/experiments)
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t
WHERE t.name IN ('Players Analytics (Staging)',
                 'Promotions Campaign Manager (Staging)',
                 'A/B Test Console (Staging)')
  AND j.name = 'UK';

-- Production monitoring / analytics / campaigns → DK & UK
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
SELECT j.id, t.id
FROM jurisdiction j
         JOIN tool t
WHERE t.name IN ('DevOps Kubernetes Dashboard (Production)',
                 'Players Analytics (Production)',
                 'Promotions Campaign Manager (Production)',
                 'Canary Release Monitor (Production)')
  AND j.name IN ('DK','UK');

-- ---------------------------------------------------------------------
-- FAVORITE TOOLS
-- ---------------------------------------------------------------------

-- John Doe (JODO) loves Slack, Notion, Happy Tiger Prod
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'JODO', t.id
FROM tool t
WHERE t.name IN (
                 'Slack',
                 'Notion Workspace',
                 'Happy Tiger Player Portal (Production)'
    );

-- Peter Donaldsen (PEDO) loves Happy Tiger Dev/Stage + feature flags
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'PEDO', t.id
FROM tool t
WHERE t.name IN (
                 'Happy Tiger Player Portal (Development)',
                 'Happy Tiger Player Portal (Staging)',
                 'Feature Flag Dashboard (Development)'
    );

-- Mikkel Yrstad Gnom (MYGN) loves SpilNu + DevOps Prod
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'MYGN', t.id
FROM tool t
WHERE t.name IN (
                 'SpilNu Casino (Staging)',
                 'SpilNu Casino (Production)',
                 'DevOps Kubernetes Dashboard (Production)'
    );

-- Søren-Preben Cromwell (SPCR) loves analytics & campaigns
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'SPCR', t.id
FROM tool t
WHERE t.name IN (
                 'Players Analytics (Staging)',
                 'Players Analytics (Production)',
                 'Promotions Campaign Manager (Production)'
    );

-- Beelzebub Komseda (BEKO) loves HR & Legal
INSERT INTO favorite_tool (employee_initials, tool_id)
SELECT 'BEKO', t.id
FROM tool t
WHERE t.name IN (
                 'HR Portal',
                 'Legal Contract Archive'
    );
