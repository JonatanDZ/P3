-- USING CREATED_BY IN NEW MOCK TOOLS
INSERT INTO tool (name, url, is_personal, is_dynamic, pending, created_by)
VALUES
    ('Dev Guidelines Wiki',      'https://confluence.company/dev-guidelines', FALSE, FALSE, TRUE, 'PEDO'),
    ('Release Checklist',        'https://confluence.company/release-check',  FALSE, FALSE, TRUE, 'PEDO'),
    ('Monitoring Dashboard',     'https://grafana.company',                   FALSE, TRUE,  TRUE, 'PEDO'),
    ('HR Self-Service Portal',   'https://hr.company',                        FALSE, FALSE, TRUE, 'BEKO');

INSERT INTO department_tool (department_id, tool_id)
VALUES
    (1,13),(2,13),(3,13),(4,13),(5,13),
    (1,14),(2,14),(3,14),(4,14),(5,14),

    (1,15),

    (7,16);

-- --- STAGE ↔ TOOL (new pending tools only on Development) ---
INSERT INTO tool_stage (stage_id, tool_id)
VALUES
    (1,13),
    (1,14),
    (1,15),
    (1,16);

-- --- JURISDICTION ↔ TOOL (for new pending tools) ---
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
VALUES
    -- Dev Guidelines → DK
    (1,13),

    -- Release Checklist → UK
    (2,14),

    -- Monitoring Dashboard → DK
    (1,15),

    -- HR Self-Service Portal → UK
    (2,16);