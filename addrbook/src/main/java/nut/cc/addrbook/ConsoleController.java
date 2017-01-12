package nut.cc.addrbook;

import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ConsoleController {
    
    public static AddressBook ab = new AddressBook();
    
    public static void printAllContacts() {
        System.out.println("************ ALL CONTACTS ************");
        for (Map.Entry<String, Contact> entry: ab.getContacts().entrySet()) {
            System.out.println(entry.getValue());
        } 
        System.out.println("++++++++++++++++++++++++++++++++++++++\n\n\n");
    }
    
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(
                                        System.in));
            String command = "";
            while (true) {
                System.out.println("Select command: add, get, remove, edit, print all");
                command = br.readLine();
                
                if (command.equalsIgnoreCase("get")) 
                {
                    System.out.print("Enter search name: ");
                    command = br.readLine();
                    Contact contact = ab.getContactByName(command);
                    if (contact != null) System.out.println(contact);
                    else System.out.print("Contact " + command + " not excist!\n");
                }
                
                else if (command.equalsIgnoreCase("remove")) 
                {
                    System.out.print("Enter contact name to remove: ");
                    command = br.readLine();
                    Contact contact = ab.getContactByName(command);
                    if (contact != null) {
                        ab.removeContactByName(command);
                        System.out.print("Contact by name " + command + " was successfull remove.\n");
                    }
                    else System.out.print("Contact " + command + " not excist!\n");
                }
                
                else if (command.equalsIgnoreCase("edit"))
                {
                    System.out.print("Enter contact name and new information\n");
                    System.out.print("Name: ");
                    String name = br.readLine();
                    Contact contact = ab.getContactByName(name);
                    //если контакт не найден - возвращаемся в начало цикла
                    if (contact == null) {
                        System.out.print("Contact " + contact + " not found!!!\n");
                        continue;
                    }
                    
                    System.out.print("New name: ");
                    String newName = br.readLine();
                    System.out.print("New number: ");
                    String newNumber = br.readLine();
                    System.out.print("New description: ");
                    String newDescription = br.readLine();
                    
                    if (newName.length() == 0) newName = contact.getName();
                    if (newNumber.length() == 0) newNumber = contact.getNumber();
                    if (newDescription.length() == 0) newDescription = contact.getDescrition();
                    
                    ab.editContactByName(name, newName, newNumber, newDescription);
                    
                    System.out.print("Contact information from contact name - " + name +
                                " chnage to\n");
                    System.out.println(ab.getContactByName(newName));
                }
                
                else if (command.equalsIgnoreCase("add")) {
                    System.out.print("Name: ");
                    String name = br.readLine();
                    System.out.print("Number: ");
                    String number = br.readLine();
                    System.out.print("Description: ");
                    String description = br.readLine();
                    
                    ab.addContact(name, number, description);
                    System.out.print("Added contact:\n");
                    System.out.println(ab.getContactByName(name));
                }
                
                else if (command.equalsIgnoreCase("exit")) 
                {
                    System.out.print("GOODBY\n");
                    break;
                }
                
                else if (command.equalsIgnoreCase("print all")) {
                    printAllContacts();
                }
        }//end while loop
          
            System.out.print("exit programm...\n");
            
        } catch (IOException e) {
            System.out.print("BufferedReader ERROR!!!!!\n");
            System.exit(-111);
        }
    }
    
}
