-- adding pending attribute as it is needed for the bell notification system
ALTER TABLE tool
    ADD COLUMN pending BOOL NOT NULL DEFAULT FALSE;