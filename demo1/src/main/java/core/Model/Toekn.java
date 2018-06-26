package core.Model;
import org.springframework.security.core.userdetails.User;


public class Toekn {
    String  token;
    User  User;

    public Toekn(String token, User user) {
        this.token = token;
        User = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public org.springframework.security.core.userdetails.User getUser() {
        return User;
    }

    public void setUser(org.springframework.security.core.userdetails.User user) {
        User = user;
    }
}
