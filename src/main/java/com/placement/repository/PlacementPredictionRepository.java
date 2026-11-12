
package com.placement.repository;

import com.placement.entity.PlacementPrediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlacementPredictionRepository extends JpaRepository<PlacementPrediction, Long> {

    List<PlacementPrediction> findByStudentId(Long studentId);

    Optional<PlacementPrediction> findTopByStudentIdOrderByPredictionDateDesc(Long studentId);

}

