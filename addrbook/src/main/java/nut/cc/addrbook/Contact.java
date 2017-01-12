package nut.cc.addrbook;


public class Contact {
    
    private String name;
    private String number;
    private String description;
    
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
        if (name.length() == 0) name = "NONE";
        if (number.length() == 0) name = "NONE";
        if (description.length() == 0) name = "NONE";
        this.name = name;
        this.number = number;
        this.description = description;
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
