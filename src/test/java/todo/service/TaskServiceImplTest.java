package todo.service;

import exeption.TaskNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import todo.model.Task;
import todo.repository.TaskRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository repository;

    private Task task;

    @Before
    public void setup() {
        task = new Task();
        task.setId(1L);
        task.setDescription("Description");
    }

    @Test
    public void createTask() {
        when(repository.save(task)).thenReturn(task);

        Task createdTask = taskService.createTask(task);
        assertEquals(task.getId(), createdTask.getId());
        assertEquals(task.getDescription(), createdTask.getDescription());
        assertEquals(task.isChecked(), createdTask.isChecked());
    }

    @Test
    public void updateTask() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        task.setChecked(true);
        when(repository.save(task)).thenReturn(task);

        Task createdTask = taskService.updateTask(task);

        assertEquals(task.getId(), createdTask.getId());
        assertTrue(createdTask.isChecked());
    }

    @Test
    public void deleteTask() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        taskService.deleteTask(1L);
    }

    @Test(expected = TaskNotExistException.class)
    public void findByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        taskService.findById(1L);
    }

}
