package co.il.kishkushim;

public class User
{
    private String username;
    private String password;
    private int age;
    private String mail;
    private int picture;

    public User (String username, String password, int age, String mail, int picture)
    {
        this.username = username;
        this.password = password;
        this.age = age;
        this.mail = mail;
        this.picture = picture;
    }

    public int getAge() {
        return age;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }
}
