package com.example.p3.repositories;

import com.example.p3.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Integer> {
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
    int toggleToolAsFavorite(
            @Param("employeeInitials") String employeeInitials,
            @Param("toolId") int toolId
    );

    @Query("SELECT t FROM Tool t JOIN t.departments d WHERE d.departmentName = :departmentName")
    List<Tool> findByDepartmentName(@Param("departmentName") String departmentName);

    @Query("SELECT t FROM Tool t JOIN t.jurisdictions j WHERE j.jurisdictionName = :jurisdictionName")
    List<Tool> findByJurisdictionName(@Param("jurisdictionName") String jurisdictionName);

    @Query("SELECT t FROM Tool t JOIN t.stages s WHERE s.name = :stageName")
    List<Tool> findByStageName(@Param("stageName") String stageName);

    @Query("SELECT t FROM Tool t " +
            "JOIN t.departments d " +
            "JOIN t.jurisdictions j " +
            "JOIN t.stages s " +
            "WHERE d.departmentName = :departmentName " +
            "AND j.jurisdictionName = :jurisdictionName " +
            "AND s.name = :stageName")
    List<Tool> findByDepartmentJurisdictonStage(@Param("departmentName") String departmentName,
                                                @Param("jurisdictionName") String jurisdictionName,
                                                @Param("stageName") String stageName);


}

