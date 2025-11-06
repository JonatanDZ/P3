package com.example.p3.repositories;

import com.example.p3.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, Integer> {
    // favorites endpoint
    // it gets the favorites based on user, current jurisdiction and current stage
    @Query("""
    SELECT DISTINCT t
    FROM Tool t
    JOIN t.employeesWhoFavorited e
    JOIN t.stages s
    JOIN t.jurisdictions j
    WHERE e.initials = :employeeInitials
      AND j.jurisdictionName = :jurisdictionName
      AND s.name = :stageName
    """)
    List<Tool> findFavoritesByEmployeeAndJurisdictionAndStage(
            @Param("employeeInitials") String employeeInitials,
            @Param("jurisdictionName") String jurisdictionName,
            @Param("stageName") String stageName
    );


    /*
    SELECT * FROM tool t
    JOIN department_tool dt ON t.id = dt.tool_id
    JOIN department d ON dt.department_id = d.id
    WHERE d.name = departmentName;
     */
    List<Tool> findByDepartments_DepartmentName(String departmentName);

    /*
    SELECT * FROM tool t
    JOIN tool_jurisdiction tj ON t.id = tj.tool_id
    JOIN jurisdiction j ON tj.jurisdiction_id = j.id
    WHERE j.name = jurisdictionName;
     */
    List<Tool> findByJurisdictions_JurisdictionName(String jurisdictionName);

    /*
    SELECT * FROM tool t
    JOIN tool_stage ts ON t.id = ts.tool_id
    JOIN stage s ON ts.stage_id = s.id
    WHERE s.name = stageName;
     */
    List<Tool> findByStages_Name(String stageName);

    /*
    SELECT * FROM tool t
    JOIN department_tool dt ON t.id = dt.tool_id
    JOIN department d ON dt.department_id = d.id
    JOIN tool_jurisdiction tj ON t.id = tj.tool_id
    JOIN jurisdiction j ON tj.jurisdiction_id = j.id
    JOIN tool_stage ts ON t.id = ts.tool_id
    JOIN stage s ON ts.stage_id = s.id
    WHERE d.name = departmentName
    AND j.name = jurisdictionName
    AND s.name = stageName;
    */
    List<Tool> findByDepartments_DepartmentNameAndJurisdictions_JurisdictionNameAndStages_Name(String DepartmentName,
                                                                                               String JurisdictionName,
                                                                                               String stageName);


}



