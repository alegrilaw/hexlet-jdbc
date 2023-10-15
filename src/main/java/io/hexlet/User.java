package io.hexlet;

public class User {

    private Long id;
    private String username;
    private String phone;

    public User(Long id, String username, String phone) {
        this.id = id;
        this.username = username;
        this.phone = phone;
    }

    public User(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", phone='" + phone + '\'' +
            '}';
    }
}
