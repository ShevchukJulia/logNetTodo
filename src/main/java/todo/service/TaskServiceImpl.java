package todo.service;

import exeption.TaskNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import todo.model.Task;
import todo.repository.TaskRepository;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task) {
        Task createdTask = taskRepository.save(task);
        log.info("New task was created, id {}", createdTask.getId());

        return createdTask;
    }

    @Override
    public Task updateTask(Task task) {
        Task taskToUpdate = taskRepository.findById(task.getId())
                .orElseThrow(() -> new TaskNotExistException(getNotExistMessage(task.getId())));

        if (!StringUtils.isEmpty(task.getDescription())) {
            taskToUpdate.setDescription(task.getDescription());
        }

        taskToUpdate.setChecked(task.isChecked());

        Task savedTask = taskRepository.save(taskToUpdate);
        log.info("Task with id {} was updated", savedTask.getId());

        return savedTask;
    }

    @Override
    public void deleteTask(Long id) {
        Task taskToDelete = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotExistException(getNotExistMessage(id)));
        taskRepository.delete(taskToDelete);
        log.info("Task with id {} was deleted", id);
    }

    @Override
    public List<Task> retrieveAll() {
        return (List<Task>) taskRepository.findAll();
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotExistException(getNotExistMessage(id)));
    }

    private String getNotExistMessage(Long id) {
        String message = MessageFormat.format("Task with id {0} does not exist", id);
        log.error(message);

        return message;
    }

}
