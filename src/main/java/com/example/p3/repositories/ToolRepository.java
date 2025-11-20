package com.example.p3.repositories;

import com.example.p3.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Integer> {
    // revert state of pending attribute for a tool
    @Modifying
    @Query(value = """
    UPDATE tool
    SET pending = FALSE
    WHERE id = :toolId
    """, nativeQuery = true)
    void revertStateOfPending(
            @Param("toolId") int toolId
    );


    @Query(value = """
    SELECT DISTINCT t.*
    FROM tool t
    JOIN department_tool dt ON t.id = dt.tool_id
    JOIN department d ON dt.department_id = d.id
    WHERE t.pending = TRUE
    AND d.name = :departmentName
    """, nativeQuery = true)
    List<Tool>  findPendingToolByUserDepartment(
            @Param("departmentName") String departmentName
    );

    // favorites endpoint
    // it gets the favorites based on user, current jurisdiction and current stage
    @Query(value = """
    SELECT DISTINCT 
    tool.id,
    tool.name,
    tool.url,
    tool.is_personal,
    tool.is_dynamic
    FROM favorite_tool
    JOIN tool
      ON favorite_tool.tool_id = tool.id
    JOIN tool_stage
      ON tool.id = tool_stage.tool_id
    JOIN stage
      ON tool_stage.stage_id = stage.id
    JOIN tool_jurisdiction
      ON tool.id = tool_jurisdiction.tool_id
    JOIN jurisdiction
      ON tool_jurisdiction.jurisdiction_id = jurisdiction.id
   WHERE favorite_tool.employee_initials = :employeeInitials
     AND jurisdiction.name = :jurisdictionName
     AND stage.name = :stageName;
""", nativeQuery = true)
    List<Tool> findFavoritesByEmployeeAndJurisdictionAndStage(
            @Param("employeeInitials") String employeeInitials,
            @Param("jurisdictionName") String jurisdictionName,
            @Param("stageName") String stageName
    );

    //  toggle existing tool as favorite
    //  the WHERE NOT EXISTS guard handles retoggling a favorite. It ensures that it does not crash the program
    //  but fails silently instead.
    @Modifying
    @Query(value = """
    INSERT INTO favorite_tool (employee_initials, tool_id)
    SELECT :employeeInitials, :toolId
    WHERE NOT EXISTS (
        SELECT 1 FROM favorite_tool
        WHERE employee_initials = :employeeInitials
          AND tool_id = :toolId
    )
    """, nativeQuery = true)
    void toggleAsFavorite(
            @Param("employeeInitials") String employeeInitials,
            @Param("toolId") int toolId
    );

    // untoggle existing tool in favorites
    @Modifying
    @Query(value = """
    DELETE FROM favorite_tool
    WHERE employee_initials = :employeeInitials
      AND tool_id = :toolId
    """, nativeQuery = true)
    void untoggleAsFavorite(
            @Param("employeeInitials") String employeeInitials,
            @Param("toolId") int toolId
    );

    //Returns only name and url from a tool (for search by tag)
    @Modifying
    @Query(value = """
    
    
""")


    /*
    SELECT * FROM tool t
    JOIN tool_jurisdiction tj ON t.id = tj.tool_id
    JOIN jurisdiction j ON tj.jurisdiction_id = j.id
    WHERE j.name = jurisdictionName;
     */
    //List<Tool> findByJurisdictions_JurisdictionName(String jurisdictionName);

    /*
    SELECT * FROM tool t
    JOIN tool_stage ts ON t.id = ts.tool_id
    JOIN stage s ON ts.stage_id = s.id
    WHERE s.name = stageName;
     */
    //List<Tool> findByStages_Name(String stageName);

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


    List<Tool> findByDepartments_NameAndJurisdictions_NameAndStages_Name(String DepartmentName,
                                                                         String JurisdictionName,
                                                                         String stageName);
    List<Tool> findByDepartments_Name(String departmentName);

}