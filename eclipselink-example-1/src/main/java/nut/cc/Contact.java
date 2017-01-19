package nut.cc;

import javax.persistence.*;

/**
 * Created by Ruslan Shmelev on 19.01.2017.
 */
@Entity
@Table(name = "contact_test")
public class Contact {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "full_name")
    private String name;
    @Column(name = "full_number")
    private String number;

    public Contact() {}

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
