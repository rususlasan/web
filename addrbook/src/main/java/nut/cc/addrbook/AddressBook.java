package nut.cc.addrbook;

//import java.io.IOException;
import java.io.*;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class AddressBook {
    
    private SortedMap<String, Contact> contacts = new TreeMap<>();
    
    public SortedMap<String, Contact> getContacts() {
        return contacts;
    }
    
    public Contact getContactByName(String name) {
        for (Map.Entry<String, Contact> entry: contacts.entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(name)) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    public void addContact(String name, String number, String description) {
        try {
            Contact contact = new Contact();
            contact.setData(name, number, description);
            contacts.put(name, contact);
            saveContactsInformation();
        } catch (IOException e) {}
    }
    
    public void removeContactByName(String name) {
        try {
                Contact contact = getContactByName(name);
                if (contact != null) {
                contacts.remove(contact.getName());
                }
                saveContactsInformation();
        } catch(IOException e) {}
    }
    
    public void editContactByName(String editContactName, 
                        String aName, String aNumber, String aDescription) {
        Contact contact = getContactByName(editContactName);
        if (contact == null) return;
        try {
            contact.setData(aName, aNumber, aDescription);
            saveContactsInformation();
        } catch(IOException e) {}
    }
    
    private void saveContactsInformation() throws IOException {
        FileWriter fw = new FileWriter("/Users/ruslansh/Documents/java/lxf99/contacts.txt");
        for (Contact contact: contacts.values()) {
            fw.write(contact.toString());
            fw.write(System.getProperty("line.separator"));
            fw.flush();
        }
        //throw new IOException();
    }
    
    public void cleanContactsBook() {
        contacts = new TreeMap<>();
    }
    
}
