package system.java.models;

public class Person {
    private String name;
    private String lastname;
    private int id;
    private long phone;
    private String email;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person() {};

    public Person(String name, String lastname, int id) {

        this.name = name;
        this.lastname = lastname;
        this.id = id;

    }

    public Person(String name, String lastname, int id, long phone, String email) {

        this.name = name;
        this.lastname = lastname;
        this.id = id;
        this.phone = phone;
        this.email = email;

    }






}
