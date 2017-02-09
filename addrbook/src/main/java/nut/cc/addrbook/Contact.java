package nut.cc.addrbook;

/*@Entity
@Table(name = "console_contact")
@NamedQuery(name = "findAllContacts", query = "SELECT c FROM Contact c")*/
public class Contact {

  /*  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private int id;
    private String name;
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
        return new StringBuilder()
                .append(name)
                .append("\t\t")
                .append(number)
                .append("\t\t")
                .append(description).toString();
    }


}