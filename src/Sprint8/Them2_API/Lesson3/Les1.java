package Sprint8.Them2_API.Lesson3;

/**
 * С помощью экземпляра класса Gson можно сериализовать и десериализовать объекты. Например, чтобы конвертировать POJO
 * в его JSON-представление, то есть сериализовать объект, необходимо вызвать метод toJson(Object src).
 * Создайте свой объект и запустите код.
 */

import com.google.gson.Gson;

public class Les1 {
    public static void main(String[] args) {
        // создайте экземпляр класса Owner (владелец)
        Owner owner = new Owner("Ben", "Adams");

        // создайте экземпляр класса Dog (собака)
        Dog dog = new Dog("Bad", owner, 3);

        Gson gson = new Gson();
        // сериализуйте объект класса Dog в JSON
        String jsonString = gson.toJson(dog);

        System.out.println(jsonString);
    }
}