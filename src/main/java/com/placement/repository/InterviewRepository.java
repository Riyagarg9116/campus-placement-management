
package com.placement.repository;

import com.placement.entity.Interview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    // Find interviews for a particular application
    List<Interview> findByApplicationId(Long applicationId);

    // Find interviews by status
    List<Interview> findByStatusIgnoreCase(String status);

    // Find interviews belonging to a particular student
    List<Interview> findByApplicationStudentId(Long studentId);
}


