package br.com.ada.t1322.tecnicasprogramacao.projeto.repository;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskRepositoryImp implements TaskRepository {
    List<Task>tasks = new ArrayList<>();

    Long nextId = 1L;
    @Override
    public Task save(Task task) {
        task.setId(nextId++);
        tasks.add(task);
        return task;
    }


    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public List<Task> findByStatus(String status) {
        return tasks.stream().filter(task -> task.getStatus().equals(status)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Task> findByStatus(Task.Status status) {
        return tasks.stream().filter((task -> task.getStatus().equals(status))).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Task> findBy(Predicate<Task> predicate) {
        return tasks.stream().distinct().filter(task -> predicate.test(task)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
       return  tasks.stream().filter(task -> task.getId().equals(id)).findFirst().isPresent();
    }

    @Override
    public void deleteAll() {
  tasks.clear();
    }
}
