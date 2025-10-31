CREATE TABLE `department`(
    `department_id`   INT,
    `department_name` VARCHAR(255),
    `is_dev`          BOOL,
    PRIMARY KEY (`department_id`)
);

CREATE TABLE `employees`(
    employee_id       INT,
    `name`          VARCHAR(255),
    `initials`      VARCHAR(255),
    `department_id` INT,
    `is_admin`      BOOL,
    PRIMARY KEY (employee_id),
    FOREIGN KEY (`department_id`)
        REFERENCES `department` (`department_id`)
);

CREATE TABLE `jurisdiction`(
    `jurisdiction_id`   INT,
    `jurisdiction_name` VARCHAR(255),
    PRIMARY KEY (`jurisdiction_id`)
);

CREATE TABLE `tool`(
    `tool_id`    INT,
    `name`       VARCHAR(255),
    `url`        VARCHAR(255),
    `is_dynamic` BOOL,
    PRIMARY KEY (`tool_id`)
);

CREATE TABLE `department_tool`(
    `department` INT,
    `tool_id`    INT,
    FOREIGN KEY (`department`)
        REFERENCES `department` (`department_id`),
    FOREIGN KEY (`tool_id`)
        REFERENCES `tool` (`tool_id`)
);

CREATE TABLE `stage`(
    `stage_id` INT,
    `name`     VARCHAR(255),
    PRIMARY KEY (`stage_id`)
);

CREATE TABLE `stage_tools`(
    `tool_id`  INT,
    `stage_id` INT,
    FOREIGN KEY (`tool_id`)
        REFERENCES `tool` (`tool_id`),
    FOREIGN KEY (`stage_id`)
        REFERENCES `stage` (`stage_id`)
);

CREATE TABLE `jurisdiction_tools`(
    `tool_id`         INT,
    `jurisdiction_id` INT,
    FOREIGN KEY (`tool_id`)
        REFERENCES `tool` (`tool_id`),
    FOREIGN KEY (`jurisdiction_id`)
        REFERENCES `jurisdiction` (`jurisdiction_id`)
);

CREATE TABLE `favorite_tools`(
    employee_id INT,
    `tool_id` INT,
    PRIMARY KEY (employee_id, `tool_id`),
    FOREIGN KEY (`tool_id`) REFERENCES `tool` (`tool_id`),
    FOREIGN KEY (employee_id) REFERENCES `employees` (employee_id)
);