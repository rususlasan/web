package nut.cc;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

/**
 * Created by Ruslan Shmelev on 02.02.17.
 */
@Entity
@Table(name = "console_contact")
public class Contact {

    @Id
    @GeneratedValue
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String number;
    private String description;

    public Contact() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getDescrition() {
        return description;
    }

    public void setData(String name, String number, String description) {
        if (name.length() == 0 || name == null) name = "NONE";
        if (number.length() == 0 || number == null) number = "NONE";
        if (description.length() == 0 || description == null) description = "NONE";
        this.name = name;
        this.number = number;
        this.description = description;
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(name)
                .append("\t\t")
                .append(number)
                .append("\t\t")
                .append(description).toString();
    }


}
