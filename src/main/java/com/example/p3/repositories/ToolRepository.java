package com.example.p3.repositories;

import com.example.p3.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Integer> {
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
}

