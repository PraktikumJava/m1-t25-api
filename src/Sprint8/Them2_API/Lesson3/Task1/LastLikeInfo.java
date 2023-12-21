package Sprint8.Them2_API.Lesson3.Task1;

public class LastLikeInfo {
    // ваш код
    String user;
    Integer hours;
    Integer minutes;

    public LastLikeInfo(String user, Integer hours, Integer minutes) {
        this.user = user;
        this.hours = hours;
        this.minutes = minutes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}
