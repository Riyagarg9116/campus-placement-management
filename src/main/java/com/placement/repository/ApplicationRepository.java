

package com.placement.repository;

import com.placement.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStudentId(Long studentId);

    List<Application> findByPlacementDriveId(Long driveId);

    boolean existsByStudentIdAndPlacementDriveId(Long studentId, Long driveId);

    List<Application> findByStatusIgnoreCase(String status);

    boolean existsByPlacementDriveId(Long driveId);
}
