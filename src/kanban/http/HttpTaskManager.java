package kanban.http;

import com.google.gson.*;
import kanban.model.Epic;
import kanban.model.LocalDateAdapter;
import kanban.model.Subtask;
import kanban.model.Task;
import kanban.service.FileBackedTasksManager;


import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {

    private static final String TASKS = "/tasks";
    private static final String EPICS = "/epics";
    private static final String SUBTASKS = "/subtasks";
    private static final String HISTORY = "/history";

    private final KVTaskClient client = new KVTaskClient();

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
            .create();

    public HttpTaskManager(boolean toLoad) {
        if (toLoad) {
            load();
        }
    }

    public HttpTaskManager() {
        this(false);
    }


    @Override
    public void save() {
        client.put(TASKS, gson.toJson(getListOfTasks()));
        client.put(EPICS, gson.toJson(getListOfEpics()));
        client.put(SUBTASKS, gson.toJson(getListOfSubtasks()));
        client.put(HISTORY, gson.toJson(getHistory().stream().map(Task::getId).collect(Collectors.toList())));
    }

    private void load() {
        JsonElement jsonTasks = JsonParser.parseString(client.load(TASKS));
        JsonArray tasksArray = jsonTasks.getAsJsonArray();
        for (JsonElement jsonTask : tasksArray) {
            Task task = gson.fromJson(jsonTask, Task.class);
            int id = task.getId();
            tasks.put(id, task);
            prioritizedTasks.add(task);
            if (id > generatorId) {
                generatorId = id;
            }
        }

        JsonElement jsonEpics = JsonParser.parseString(client.load(EPICS));
        JsonArray epicsArray = jsonEpics.getAsJsonArray();
        for (JsonElement jsonEpic : epicsArray) {
            Epic epic = gson.fromJson(jsonEpic, Epic.class);
            int id = epic.getId();
            epics.put(id, epic);
            if (id > generatorId) {
                generatorId = id;
            }
        }

        JsonElement jsonSubtasks = JsonParser.parseString(client.load(SUBTASKS));
        JsonArray subtasksArray = jsonSubtasks.getAsJsonArray();
        for (JsonElement jsonSubtask : subtasksArray) {
            Subtask subtask = gson.fromJson(jsonSubtask, Subtask.class);
            int id = subtask.getId();
            subtasks.put(id, subtask);
            prioritizedTasks.add(subtask);
            if (id > generatorId) {
                generatorId = id;
            }
        }

        JsonElement historyList = JsonParser.parseString(client.load(HISTORY));
        JsonArray historyArray = historyList.getAsJsonArray();
        for (JsonElement jsonId : historyArray) {
            int id = jsonId.getAsInt();
            if (tasks.containsKey(id)) {
                historyManager.add(tasks.get(id));
            } else if (epics.containsKey(id)) {
                historyManager.add(epics.get(id));
            } else if (subtasks.containsKey(id)) {
                historyManager.add(subtasks.get(id));
            }
        }
    }
}