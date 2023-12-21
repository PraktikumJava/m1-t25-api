package Sprint8.Them2_API.Lesson6.Task2;

/**
 * Продолжаем разрабатывать HTTP-сервер для социальной сети. На этом этапе нужно реализовать обработку двух эндпоинтов:
 * GET /posts — для получения списка всех постов;
 * GET /posts/{postId}/comments — для получения комментариев к посту.
 * Для этого напишите следующие методы:
 * handleGetPosts — формирует HTTP-ответ, содержащий список постов в формате JSON.
 * getPostId — извлекает идентификатор поста из пути, указанного в запросе. Если идентификатор указан некорректно —
 * например, не является числом, то нужно вернуть пустой объект Optional.
 * handleGetComments — формирует HTTP-ответ, содержащий список комментариев указанного поста в формате JSON. В этом
 * методе нужно также предусмотреть обработку двух ситуаций:
 * Если был передан некорректный идентификатор поста, то ответ должен содержать текст Некорректный идентификатор поста,
 * а его код должен быть равен 400.
 * Если пост с указанным идентификатором не найден, то ответ должен содержать текст Пост с идентификатором postId не
 * найден, где вместо postId будет переданный идентификатор. Код ответа должен быть равен 404.
 */

/** Подсказка
 *Для отправки ответов используйте метод writeResponse, реализованный в предыдущем задании.
 * При обработке эндпоинтов, связанных с комментариями, помните: второй элемент пути запроса —
 * это всегда идентификатор поста. Его можно преобразовать в число с помощью метода Integer.parseInt.
 * Если идентификатор не является целым числом, то при его преобразовании возникнет исключение NumberFormatException.
 * Чтобы преобразовать объект в текст в формате JSON, необходимо вызвать метод toJson объекта Gson.
 */

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



class Post {
    private int id;
    private String text;
    private List<Comment> commentaries = new ArrayList<>();

    private Post() {}

    public Post(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public void addComment(Comment comment) {
        commentaries.add(comment);
    }

    public List<Comment> getCommentaries() {
        return commentaries;
    }

    public int getId() {
        return id;
    }
}

class Comment {
    private String user;
    private String text;

    private Comment() {}

    public Comment(String user, String text) {
        this.user = user;
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }
}

public class Praktikum {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static Gson gson = new Gson();
    private static List<Post> posts = new ArrayList<>();

    static {
        Post post1 = new Post(1, "Это первый пост, который я здесь написал.");
        post1.addComment(new Comment("Пётр Первый", "Я успел откомментировать первым!"));
        posts.add(post1);

        Post post2 = new Post(22, "Это будет второй пост. Тоже короткий.");
        posts.add(post2);

        Post post3 = new Post(333, "Это пока последний пост.");
        posts.add(post3);
    }


    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/posts", new PostsHandler());
        httpServer.start(); // запускаем сервер

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    static class PostsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

            switch (endpoint) {
                case GET_POSTS: {
                    handleGetPosts(exchange);
                    break;
                }
                case GET_COMMENTS: {
                    handleGetComments(exchange);
                    break;
                }
                case POST_COMMENT: {
                    handlePostComments(exchange);
                    break;
                }
                default:
                    writeResponse(exchange, "Такого эндпоинта не существует", 404);
            }
        }

        private Endpoint getEndpoint(String requestPath, String requestMethod) {
            String[] pathParts = requestPath.split("/");

            if (pathParts.length == 2 && pathParts[1].equals("posts")) {
                return Endpoint.GET_POSTS;
            }
            if (pathParts.length == 4 && pathParts[1].equals("posts") && pathParts[3].equals("comments")) {
                if (requestMethod.equals("GET")) {
                    return Endpoint.GET_COMMENTS;
                }
                if (requestMethod.equals("POST")) {
                    return Endpoint.POST_COMMENT;
                }
            }
            return Endpoint.UNKNOWN;
        }

        private void handleGetPosts(HttpExchange exchange) throws IOException {
            // верните JSON, представляющий список постов. Код ответа должен быть 200.
            writeResponse(exchange, gson.toJson(posts), 200);
        }

        private void handleGetComments(HttpExchange exchange) throws IOException {
            Optional<Integer> postIdOpt = getPostId(exchange);
            /* Верните комментарии к указанному посту. Код ответа должен быть 200.
            Если запрос был составлен неверно, верните сообщение об ошибке. */
            if(postIdOpt.isEmpty()) {
                writeResponse(exchange, "Некорректный идентификатор поста", 400);
                return;
            }
            int postId = postIdOpt.get();

            for (Post post : posts) {
                if (post.getId() == postId) {
                    String commentsJson = gson.toJson(post.getCommentaries());
                    writeResponse(exchange, commentsJson, 200);
                    return;
                }
            }

            writeResponse(exchange, "Пост с идентификатором " + postId + " не найден", 404);
        }

        private Optional<Integer> getPostId(HttpExchange exchange) {
            /* Реализуйте метод получения идентификатора поста.
            Если идентификатор не является числом, верните Optional.empty(). */
            //
            String[] pathParts = exchange.getRequestURI().getPath().split("/");
            try {
                return Optional.of(Integer.parseInt(pathParts[2]));
            } catch (NumberFormatException exception) {
                return Optional.empty();
            }
        }

        private void handlePostComments(HttpExchange exchange) throws IOException {
            writeResponse(exchange, "Этот эндпоинт пока не реализован", 200);
        }

        private void writeResponse(HttpExchange exchange,
                                   String responseString,
                                   int responseCode) throws IOException {
            if(responseString.isBlank()) {
                exchange.sendResponseHeaders(responseCode, 0);
            } else {
                byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
                exchange.sendResponseHeaders(responseCode, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            }
            exchange.close();
        }

        enum Endpoint {GET_POSTS, GET_COMMENTS, POST_COMMENT, UNKNOWN}
    }
}