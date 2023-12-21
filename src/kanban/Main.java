package kanban;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kanban.http.HttpTaskManager;
import kanban.http.HttpTaskServer;
import kanban.http.KVServer;
import kanban.model.Epic;
import kanban.model.LocalDateAdapter;
import kanban.model.Subtask;
import kanban.model.Task;


import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;



public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        KVServer kvServer = new KVServer();
        kvServer.start();

        HttpTaskManager taskManager = new HttpTaskManager();

        HttpTaskServer server = new HttpTaskServer(taskManager);
        server.start();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();

        Task task1 = new Task("Задача", "description1",
                LocalDateTime.of(2023, 1, 1, 0, 0), 1000);
        Epic epic2 = new Epic("Эпик", "description2");
        Subtask subtask3 = new Subtask("Подзадача", "description3", 2,
                LocalDateTime.of(2023, 1, 2, 0, 0), 1000);
        Subtask subtask4 = new Subtask("Подзадача", "description4", 2,
                LocalDateTime.of(2023, 1, 3, 0, 0), 1000);

        taskManager.addTask(task1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);

        taskManager.getEpicById(2);
        taskManager.getTaskById(1);
        taskManager.getSubtaskById(4);

//        HttpClient client = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/tasks/task/");
//        Task task = new Task("Задача", "description5",
//                LocalDateTime.of(2023, 1, 4, 0, 0), 1000);
//        String json = gson.toJson(task);
//        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
//        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println(body);
//        System.out.println(json);

//        kvServer.stop();
//        server.stop();

    }
}