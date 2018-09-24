package todo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import todo.model.Task;
import todo.service.TaskService;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

    private static final String BASE_URL = "/task";

    @MockBean
    private TaskService service;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private Task task;

    @Before
    public void setup() {
        task = new Task();
        task.setId(1L);
        task.setDescription("Description");
    }

    @Test
    public void createTask() {
        when(service.createTask(task)).thenReturn(task);

        ResponseEntity<Task> response = testRestTemplate.postForEntity(BASE_URL, task, Task.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task.getId(), response.getBody().getId());
    }

    @Test
    public void updateTask() {
        when(service.updateTask(task)).thenReturn(task);

        ResponseEntity<Task> response = testRestTemplate
                .exchange(BASE_URL, HttpMethod.PUT, new HttpEntity<Object>(task), Task.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task.getId(), response.getBody().getId());
    }

    @Test
    public void deleteTask() {
        when(service.findById(1L)).thenReturn(task);

        ResponseEntity<Void> response = testRestTemplate
                .exchange(BASE_URL + "/1", HttpMethod.DELETE, new HttpEntity<Object>(task), Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getTaskById() {
        when(service.findById(1L)).thenReturn(task);

        ResponseEntity<Task> response = testRestTemplate.getForEntity(BASE_URL + "/1", Task.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task.getId(), response.getBody().getId());
    }

    @Test
    public void findAll() {
        when(service.retrieveAll()).thenReturn(Collections.singletonList(task));

        ResponseEntity<List<Task>> response = testRestTemplate.exchange(BASE_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Task>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

}