package kanban.service;

import kanban.http.HttpTaskManager;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault(){
        return new HttpTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}