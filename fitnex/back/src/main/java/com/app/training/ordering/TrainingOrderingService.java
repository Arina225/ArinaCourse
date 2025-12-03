package com.app.training.ordering;

import com.app.appUser.UserService;
import com.app.enums.OrderingStatus;
import com.app.system.exception.ObjectNotFoundException;
import com.app.training.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingOrderingService {

    private final TrainingOrderingRepository repository;
    private final TrainingService trainingService;
    private final UserService userService;

    public List<TrainingOrdering> findAll() {
        return repository.findAllByTraining_Trainer_Id(userService.getCurrentUser().getId(), Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<TrainingOrdering> findAllMy() {
        List<TrainingOrdering> orderings = userService.getCurrentUser().getTrainingOrderings();
        orderings.sort(Comparator.comparing(TrainingOrdering::getId));
        Collections.reverse(orderings);
        return orderings;
    }

    public TrainingOrdering find(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Не найдена заявка по ИД: " + id));
    }

    public TrainingOrdering save(String trainingId) {
        return repository.save(new TrainingOrdering(trainingService.find(trainingId), userService.getCurrentUser()));
    }

    public TrainingOrdering confirmed(String id) {
        TrainingOrdering ordering = find(id);
        ordering.setStatus(OrderingStatus.CONFIRMED);
        return repository.save(ordering);
    }

    public TrainingOrdering rejected(String id) {
        TrainingOrdering ordering = find(id);
        ordering.setStatus(OrderingStatus.REJECTED);
        return repository.save(ordering);
    }

}
