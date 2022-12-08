package system.java.models;

import java.time.LocalDateTime;

public class Shift {

    public Shift(LocalDateTime dateTime, Person person) {

        this.person = person;
        this.dateTime = dateTime;
    }

    public LocalDateTime getDate() {
        return dateTime;
    }

    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    private Person person;
    private LocalDateTime dateTime;


}
