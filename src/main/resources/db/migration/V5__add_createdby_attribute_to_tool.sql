ALTER TABLE tool
    ADD COLUMN created_by VARCHAR(4) NULL;

ALTER TABLE tool
    ADD CONSTRAINT fk_tool_created_by
        FOREIGN KEY (created_by)
            REFERENCES employee(initials)
            -- if an employee is deleted the tool attribute is set to null.
            ON DELETE SET NULL
            ON UPDATE CASCADE;