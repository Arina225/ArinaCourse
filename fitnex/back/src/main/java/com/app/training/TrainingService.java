package com.app.training;

import com.app.appUser.UserService;
import com.app.system.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository repository;
    private final UserService userService;

    public List<Training> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Training> findAllMy() {
        List<Training> trainings = userService.getCurrentUser().getTrainings();
        trainings.sort(Comparator.comparing(Training::getId));
        Collections.reverse(trainings);
        return trainings;
    }

    public List<Training> findAllByTrainerId(String id) {
        return repository.findAllByTrainer_Id(userService.findById(id).getId(), Sort.by(Sort.Direction.DESC, "id"));
    }

    public Training find(String id) {
        return repository.findById(Long.parseLong(id)).orElseThrow(() -> new ObjectNotFoundException("Не найдена тренировка по ИД: " + id));
    }

    public Training save(Training save) {
        save.setTrainer(userService.getCurrentUser());
        return repository.save(save);
    }

    public Training update(String id, Training update) {
        Training old = find(id);
        old.update(update);
        return repository.save(old);
    }

    public void delete(String id) {
        repository.deleteById(find(id).getId());
    }

}
