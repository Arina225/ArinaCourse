package com.app.training.ordering;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingOrderingRepository extends JpaRepository<TrainingOrdering, Long> {
    List<TrainingOrdering> findAllByTraining_Trainer_Id(Long trainerId, Sort sort);
}
