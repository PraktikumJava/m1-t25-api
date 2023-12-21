package Sprint8.Them2_API.Lesson3.Task1;

/**
 * Перед вами структура класса социальной сети, описывающего сведения о посте. В объекте этого класса содержится
 * информация о том, лайкнул ли пост сам автор, массив лайков, количество репостов, а также информация о последнем
 * лайке. В каждой ячейке массива хранятся данные об одном лайке — имя и ссылка на фотографию профиля.
 * В заготовке есть объект класса. Дозаполните его данными, десериализовав информацию о последнем лайке — она
 * представлена в виде текста String. Сериализуйте полученный объект информации о лайках в текст и выведите на экран.
 */

import com.google.gson.Gson;

import java.io.IOException;

public class Practicum {
    public static void main(String[] args) throws IOException {
        String lastLikeInfoStr = "{ \"user\": \"Алексей\", \"hours\": 12, \"minutes\": 30}";
        Gson gson = new Gson();
        LastLikeInfo lastLikeInfo = gson.fromJson(lastLikeInfoStr, LastLikeInfo.class);// код десериализации

        LikesInfo likesInfo = new LikesInfo();
        likesInfo.setRepostsCount(10);
        likesInfo.setHasOwnerLiked(true);
        likesInfo.setLastLikeInfo(lastLikeInfo);
        likesInfo.setLikes(new Like[]{
                new Like("Алексей", "http://example.com/avatars/aleksey.jpg"),
                new Like("Елена", "http://example.com/avatars/elena.jpg"),
                new Like("Света", "http://example.com/avatars/sveta.jpg"),
        });

        // код сериализации
        String jsonString = gson.toJson(likesInfo);

        System.out.println(jsonString);
    }
}
