-- --- PENDING TOOLS (not in any favorites) ---
INSERT INTO tool (name, url, is_personal, is_dynamic, pending)
VALUES
    ('Youtube',       'https://youtube.com', FALSE, TRUE,  TRUE),
    ('Dangerous Link',           'https://google.com',        FALSE, TRUE,  TRUE),
    ('SQLRunner', 'https://google.com',    FALSE, FALSE, TRUE),
    ('Legal Department Link',       'https://google.com',    FALSE, FALSE, TRUE);

-- Assuming previous 8 tools got IDs 1–8, these will be IDs 9–12.

-- --- DEPARTMENT ↔ TOOL (for pending tools) ---
INSERT INTO department_tool (department_id, tool_id)
VALUES
    -- Sandboxes to all dev departments (1–5)
    (1, 9),(2, 9),(3, 9),(4, 9),(5, 9),
    (1,10),(2,10),(3,10),(4,10),(5,10),

    -- DevOps Dashboard only for DevOps (1)
    (1,11),

    -- Legal Review only for Legal (7)
    (7,12);

-- --- STAGE ↔ TOOL (pending tools only on Development) ---
INSERT INTO tool_stage (stage_id, tool_id)
VALUES
    (1, 9),
    (1,10),
    (1,11),
    (1,12);

-- --- JURISDICTION ↔ TOOL (for pending tools) ---
INSERT INTO tool_jurisdiction (jurisdiction_id, tool_id)
VALUES
    -- Happy Tiger Sandbox → UK
    (2, 9),

    -- Spil Nu Sandbox → DK
    (1,10),

    -- DevOps Internal Dashboard → DK
    (1,11),

    -- Legal Review Portal → UK
    (2,12);