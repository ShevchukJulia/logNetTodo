package todo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import todo.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

}
