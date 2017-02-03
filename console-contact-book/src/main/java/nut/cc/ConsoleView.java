package nut.cc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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
        reader = new BufferedReader(new InputStreamReader(System.in));
        begin();
    }

    /**
     * Запускает цикл в котором слушает пользовательский ввод и, в зависимости от ввода,
     * запускает нужный метод или переходит на следующий шаг
     */
    public void begin() {
        String curr = "";
        while (true) {
            consoleWriter("Change command: \nc - create contact,\np - print all contact,\nd - delete contact,\nq - quit program\n");
            curr = consoleReader(null);
            if (curr.equals("q")) {
                exit();
                return;
            }
            else if (curr.equals("c")) addContact();
            else if (curr.equals("d")) deleteContact();
            else if (curr.equals("p")) printAllContact();
            else consoleWriter("Command " + curr + " not found, try again.\n");
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
            consoleWriter("IOException in consoleReader(): " + e.getMessage() + "\n");
        } finally {
            return res;
        }

    }

    /**
     * Выводит в консоль переданную строку
     */
    public void consoleWriter(String message) {
        if (message != null && !message.equals("")) {
            System.out.print(message);
        }
    }

    /**
     * Считывает данные контакта и вызывает метод котроллера для создания контакта
     * и записи его в БД
     */
    public void addContact() {
        //считываем данные с консоли, для создания контакта
        String name = consoleReader("Enter name: ");
        String number = consoleReader("Enter number: ");
        String description = consoleReader("Enter description: ");

        Contact contact = controller.addContact(name, number, description);
        if (contact != null) consoleWriter("#### Contact: " + contact + " ####\n#### was successfully added! ####\n");
        else consoleWriter("Contact didn't added.");
    }

    public void printAllContact() {
        List<Contact> contacts = controller.findAllContacts();
        consoleWriter("############### ALL CONTACTS ###############\n");
        for (Contact contact: contacts) {
            consoleWriter(contact.toString() + "\n");
        }
        consoleWriter("############################################\n");
    }

    public Contact deleteContact() {
        return null;
    }

    /**
     * Закрываем ресурсы контроллера и пишем что программа завершена
     */
    private void exit() {
        controller.closeResources();
        consoleWriter("Ending program!\n");
    }
}
