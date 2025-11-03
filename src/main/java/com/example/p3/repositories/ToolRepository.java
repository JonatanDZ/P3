package com.example.p3.repositories;

import com.example.p3.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Integer> {

    @Query("SELECT t FROM Tool t JOIN t.departments d WHERE d.departmentName = :departmentName")
    List<Tool> findByDepartmentName(@Param("departmentName") String departmentName);

    @Query("SELECT t FROM Tool t JOIN t.jurisdictions j WHERE j.jurisdictionName = :jurisdictionName")
    List<Tool> findByJurisdictionName(@Param("jurisdictionName") String jurisdictionName);

    @Query("SELECT t FROM Tool t JOIN t.stages s WHERE s.name = :stageName")
    List<Tool> findByStageName(@Param("stageName") String stageName);


}