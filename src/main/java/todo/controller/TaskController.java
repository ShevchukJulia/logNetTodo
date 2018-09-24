package todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import todo.model.Task;
import todo.service.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskController {

    private TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody @Valid Task task) {
        return service.createTask(task);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Task updateTask(@RequestBody Task task) {
        return service.updateTask(task);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Task> findAll() {
        return service.retrieveAll();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Task findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
