package Sprint8.Them2_API.Lesson4;

/**
 * Включать prettyPrinting рекомендуется, например, при выводе данных в формате JSON в консоль приложения или при
 * ручной обработке ответов от API — когда важна читаемость данных.
 * Запустите этот код. JSON будет отображаться в удобном для чтения виде.
 */

import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;

        import java.time.LocalDate;

class Les1 {
    public static void main(String[] args) {
        UserPost post = new UserPost();
        post.setPhotoUrl("https://new-social-network.site/images/928476864.jpg");
        post.setUserId(97_748);
        post.setDescription("Классное фото!");
        post.setLikesQuantity(753);
        LocalDate publicationDate = LocalDate.of(2020, 12, 25);
        post.setPublicationDate(publicationDate);

        GsonBuilder gsonBuilder = new GsonBuilder();
         gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        String postSerialized = gson.toJson(post);
        System.out.println(postSerialized);
    }
}