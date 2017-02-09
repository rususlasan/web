package nut.cc.addrbook;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {
    
    private List<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public boolean addContact(Contact contact) {
        return contacts.add(contact);
    }

    public List<Contact> getContacts() {
        return contacts;
    }

}
