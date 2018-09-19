package todo.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Optional;

public enum TaskStatus {
    NEW,
    DONE;

    @JsonCreator
    public static TaskStatus forValue(String value) {
        Optional<TaskStatus> status = Arrays.stream(values())
                .filter(item -> value.equalsIgnoreCase(item.name())).findFirst();
        return status.orElse(null);
    }
}
