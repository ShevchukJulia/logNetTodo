package todo.service;

import todo.model.Task;

import java.util.List;

public interface TaskService {

    Task createTask(Task task);

    Task updateTask(Task task);

    void deleteTask(Long id);

    List<Task> retrieveAll();

    Task findById(Long id);

}
