package model;

/**
 *
 * @author lenovo
 * @date 2018/6/27
 */
public class User {

    private int id;
    private String name;
    private String password;
    private String email;
    private String photo;
    private int gender;
    private String login_time;
    private String register_time;
    private int is_banned;

    //用于密码更新
    private String new_password;

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public int getIs_banned() {
        return is_banned;
    }

    public void setIs_banned(int is_banned) {
        this.is_banned = is_banned;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(int id, String name, String password, String email, String photo, int gender, String login_time, String register_time, int is_banned) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.photo = photo;
        this.gender = gender;
        this.login_time = login_time;
        this.register_time = register_time;
        this.is_banned = is_banned;
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(int id, String login_time) {
        this.id = id;
        this.login_time = login_time;
    }

    public User(int id, String name, String photo, int gender) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.gender = gender;
    }

    public User(int id, String password, String new_password) {
        this.id = id;
        this.password = password;
        this.new_password = new_password;
    }

    public User(int id, int is_banned) {
        this.id = id;
        this.is_banned = is_banned;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", gender=" + gender +
                ", login_time='" + login_time + '\'' +
                ", register_time='" + register_time + '\'' +
                ", is_banned=" + is_banned +
                '}';
    }
}

