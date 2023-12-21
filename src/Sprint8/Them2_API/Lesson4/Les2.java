package Sprint8.Them2_API.Lesson4;

/**
 * Как и предыдущую, эту настройку обычно оставляют выключенной, чтобы не передавать по сети лишние данные.
 * Если в объекте много полей и они часто бывают равны null, то выключенная настройка уберёт из JSON строки
 * вида "название_поля": null.
 */

import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;

        import java.time.LocalDate;

class Les2 {
    public static void main(String[] args) {
        UserPost post = new UserPost();
        post.setPhotoUrl("https://new-social-network.site/images/928476864.jpg");
        post.setUserId(97_748);
        post.setLikesQuantity(753);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        String postSerialized = gson.toJson(post);
        System.out.println(postSerialized);
    }
}