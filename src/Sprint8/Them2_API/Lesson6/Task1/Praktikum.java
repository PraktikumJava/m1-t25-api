package Sprint8.Them2_API.Lesson6.Task1;

/**
 * Вам нужно создать HTTP-сервер для небольшой социальной сети. Сервер сможет обрабатывать запросы к трём эндпоинтам:
 * -GET /posts — для получения списка всех постов;
 * -GET /posts/{postId}/comments — для получения комментариев к посту;
 * -POST /posts/{postId}/comments — для добавления нового комментария к посту.
 * На первом этапе реализуйте метод getEndpoint — он будет анализировать, к какому из трёх перечисленных эндпоинтов был запрос.
 * Также реализуйте метод writeResponse, который возвращает HTTP-ответ с указанным в коде текстом.
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Praktikum {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static void main(String[] args) throws IOException {

        // добавьте код для конфигурирования и запуска сервера
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/posts", new PostsHandler());
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    static class PostsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // получите информацию об эндпоинте, к которому был запрос
            Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

            switch (endpoint) {
                case GET_POSTS: {
                    writeResponse(exchange, "Получен запрос на получение постов", 200);
                    break;
                }
                case GET_COMMENTS: {
                    writeResponse(exchange, "Получен запрос на получение комментариев", 200);
                    break;
                }
                case POST_COMMENT: {
                    writeResponse(exchange, "Получен запрос на добавление комментария", 200);
                    break;
                }
                default:
                    writeResponse(exchange, "Такого эндпоинта не существует", 404);
            }
        }

        private Endpoint getEndpoint(String requestPath, String requestMethod) {
            // реализуйте этот метод
            //
            String[] paths = requestPath.split("/");
            String path = "";
            if (paths.length == 2){
                path += paths[1];
            } else if (paths.length == 4) {
                path += paths[3];
            }
            String endpoint = requestMethod.toUpperCase() + "_" + path.toUpperCase();
            switch (endpoint) {
                case "GET_POSTS":
                    return Endpoint.GET_POSTS;
                case "GET_COMMENTS":
                    return Endpoint.GET_COMMENTS;
                case "POST_COMMENTS":
                    return Endpoint.POST_COMMENT;
                default:
                    return Endpoint.UNKNOWN;
            }
        }

        private void writeResponse(HttpExchange exchange,
                                   String responseString,
                                   int responseCode) throws IOException {
                /* Реализуйте отправку ответа, который содержит responseString в качестве тела ответа
                и responseCode в качестве кода ответа.
                Учтите, что если responseString — пустая строка, то её не нужно передавать в ответе. */
            //
            exchange.sendResponseHeaders(responseCode, 0);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseString.getBytes());
            }
        }

        enum Endpoint {GET_POSTS, GET_COMMENTS, POST_COMMENT, UNKNOWN}
    }
}