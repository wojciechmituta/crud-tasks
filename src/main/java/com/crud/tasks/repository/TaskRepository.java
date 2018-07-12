package com.crud.tasks.repository;

import java.util.List;

import com.crud.tasks.domain.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
    @Override
    List<Task> findAll();

    @Override
    Task findOne(Long taskId);

    @Override
    Task save(Task task);

    @Override
    void delete(Long taskId);

    @Override
    long count();
}
