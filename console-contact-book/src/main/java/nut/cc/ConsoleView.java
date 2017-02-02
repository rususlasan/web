package nut.cc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ruslansh on 02.02.17.
 */
public class ConsoleView {

    private ConsoleController controller;

    private BufferedReader reader;

    public void setController(ConsoleController controller) {
        this.controller = controller;
    }

    public ConsoleController getController() {
        return controller;
    }

    public void init() {
        System.out.println("Change command: \nc - create contact,\na - print all contact,\nd - delete contact,\n q - quit program\n");
        reader = new BufferedReader(new InputStreamReader(System.in));
        begin();
    }

    public void begin() {
        String curr = "";
        while (true) {
            curr = consoleReader(null);
            if (curr.equals("q")) {
                controller.closeRecources();
                consoleWriter("Ending program!");
                return;
            }
            else if (curr.equals("c")) addContact();
            else if (curr.equals("d")) deleteContact();
            else if (curr.equals("p")) printAllContact();
            else consoleWriter("Command " + curr + " not found, try again.");
        }
    }

    /**
     * Считывает строку с консоли.
     * Перед считывание выводит сообщение в консоль, если оно передано
     */
    public String consoleReader(String message) {
        consoleWriter(message);
        String res = "";
        try {
            res = reader.readLine();
        } catch (IOException e) {
            consoleWriter("IOException in consoleReader(): " + e.getMessage());
        } finally {
            return res;
        }

    }

    /**
     * Выводит в консоль переданную строку
     */
    public void consoleWriter(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
        }
    }

    public void addContact() {
        Contact contact = controller.addContact();
        if (contact != null) consoleWriter("Contact: " + contact + "\nwas successfully added!");
        else consoleWriter("Contact didn't added.");
    }

    public void printAllContact() {

    }

    public Contact deleteContact() {
        return null;
    }
}
