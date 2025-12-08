-- Schema of the SQL design.
CREATE TABLE department (
                            id        INT           NOT NULL AUTO_INCREMENT,
                            name      VARCHAR(255)  NOT NULL,
                            is_dev    BOOL          NOT NULL DEFAULT FALSE,
                            PRIMARY KEY (id)
);

CREATE TABLE tool (
                      id          INT           NOT NULL AUTO_INCREMENT,
                      name        VARCHAR(255)  NOT NULL,
                      url         VARCHAR(255)  NOT NULL,
                      is_personal BOOL          NOT NULL DEFAULT FALSE,
                      is_dynamic  BOOL          NOT NULL DEFAULT FALSE,
                      PRIMARY KEY (id)
);

CREATE TABLE employee (
                          initials      VARCHAR(4)   NOT NULL,
                          name          VARCHAR(255) NOT NULL,
                          department_id INT          NOT NULL,
                          email         VARCHAR(255) NOT NULL,
                          PRIMARY KEY (initials),
                          CONSTRAINT fk_employee_department
                              FOREIGN KEY (department_id) REFERENCES department(id)
                                  ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE jurisdiction (
                              id    INT           NOT NULL AUTO_INCREMENT,
                              name  VARCHAR(255)  NOT NULL,
                              PRIMARY KEY (id)
);

CREATE TABLE stage (
                       id    INT           NOT NULL AUTO_INCREMENT,
                       name  VARCHAR(255)  NOT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE tag (
                     id     INT           NOT NULL AUTO_INCREMENT,
                     value  VARCHAR(255)  NOT NULL,
                     PRIMARY KEY (id)
);

CREATE TABLE department_tool (
                                 department_id INT NOT NULL,
                                 tool_id       INT NOT NULL,
                                 PRIMARY KEY (department_id, tool_id),
                                 CONSTRAINT fk_dept_tool_dept
                                     FOREIGN KEY (department_id) REFERENCES department(id)
                                         ON DELETE CASCADE ON UPDATE CASCADE,
                                 CONSTRAINT fk_dept_tool_tool
                                     FOREIGN KEY (tool_id) REFERENCES tool(id)
                                         ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tool_jurisdiction (
                                   jurisdiction_id INT NOT NULL,
                                   tool_id         INT NOT NULL,
                                   PRIMARY KEY (jurisdiction_id, tool_id),
                                   CONSTRAINT fk_tool_jur_jur
                                       FOREIGN KEY (jurisdiction_id) REFERENCES jurisdiction(id)
                                           ON DELETE CASCADE ON UPDATE CASCADE,
                                   CONSTRAINT fk_tool_jur_tool
                                       FOREIGN KEY (tool_id) REFERENCES tool(id)
                                           ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tool_stage (
                            stage_id INT NOT NULL,
                            tool_id  INT NOT NULL,
                            PRIMARY KEY (stage_id, tool_id),
                            CONSTRAINT fk_tool_stage_stage
                                FOREIGN KEY (stage_id) REFERENCES stage(id)
                                    ON DELETE CASCADE ON UPDATE CASCADE,
                            CONSTRAINT fk_tool_stage_tool
                                FOREIGN KEY (tool_id) REFERENCES tool(id)
                                    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tool_tag (
                          tool_id INT NOT NULL,
                          tag_id  INT NOT NULL,
                          PRIMARY KEY (tool_id, tag_id),
                          CONSTRAINT fk_tool_tag_tool
                              FOREIGN KEY (tool_id) REFERENCES tool(id)
                                  ON DELETE CASCADE ON UPDATE CASCADE,
                          CONSTRAINT fk_tool_tag_tag
                              FOREIGN KEY (tag_id) REFERENCES tag(id)
                                  ON DELETE CASCADE ON UPDATE CASCADE
);

-- Favorites
CREATE TABLE favorite_tool (
                               employee_initials VARCHAR(4) NOT NULL,
                               tool_id           INT        NOT NULL,
                               PRIMARY KEY (employee_initials, tool_id),
                               CONSTRAINT fk_fav_emp
                                   FOREIGN KEY (employee_initials) REFERENCES employee(initials)
                                       ON DELETE CASCADE ON UPDATE CASCADE,
                               CONSTRAINT fk_fav_tool
                                   FOREIGN KEY (tool_id) REFERENCES tool(id)
                                       ON DELETE CASCADE ON UPDATE CASCADE
);