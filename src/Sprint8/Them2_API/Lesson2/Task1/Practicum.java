package Sprint8.Them2_API.Lesson2.Task1;

/**
 * Добавьте в код сервера новый обработчик для эндпоинта /day. Сервер должен отвечать случайно выбранным днём недели,
 * то есть одной из строк — "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN".
 */

import com.sun.net.httpserver.HttpExchange;
        import com.sun.net.httpserver.HttpHandler;
        import com.sun.net.httpserver.HttpServer;

        import java.io.IOException;
        import java.io.OutputStream;
        import java.net.InetSocketAddress;
import java.util.Random;

public class Practicum {
    private static final int PORT = 8080;

    // IOException могут сгенерировать методы create() и bind(...)
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/hello", new HelloHandler());
        // добавьте новый обработчик для /day тут
        httpServer.createContext("/day", new DayHandler());
        httpServer.start(); // запускаем сервер

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        httpServer.stop(1);
    }

    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Началась обработка /hello запроса от клиента.");

            String response = "Hey! Glad to see you on our server.";
            httpExchange.sendResponseHeaders(200, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    // объявите класс-обработчик тут
    static class DayHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Началась обработка /day запроса от клиента.");
            String[] day = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

            Random rand = new Random();
            int rand1 = rand.nextInt(6);
            String response = day[rand1];
            httpExchange.sendResponseHeaders(200, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}