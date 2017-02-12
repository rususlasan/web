package nut.cc;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.NamedQuery;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ruslan Shmelev on 02.02.17.
 */
@Entity
@Table(name = "contacts")
@NamedQuery(name = "findAllContacts", query = "SELECT c FROM Contact c ORDER BY c.name DESC")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Size(min = 1, max = 25)
    @Column(nullable = false)
    private String name;

    private String number;

    @Column(length = 256)
    private String description;

    private Boolean isIllustration;

    public Contact() {}

    public Contact(String name, String number, String description, Boolean isIllustration) {
        this.name = name;
        this.number = number;
        this.description = description;
        this.isIllustration = isIllustration;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIllustration() {
        return isIllustration;
    }

    public void setIllustration(Boolean illustration) {
        isIllustration = illustration;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(name)
                .append("\t\t")
                .append(number)
                .append("\t\t")
                .append(description)
                .append("\t\t")
                .append(isIllustration)
                .toString();
    }

}
