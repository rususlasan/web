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
            curr = consoleReader();
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
    public String consoleReader() {
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
        consoleWriter("Enter name: ");
        String name = consoleReader();

        consoleWriter("Enter number: ");
        String number = consoleReader();

        consoleWriter("Enter description: ");
        String description = consoleReader();

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

    /**
     * Запускает диалог по удалению контакта, если передана строка вида id=2 - запускаем метод контроллера deleteContactById,
     * если передано что-то другое, запускаем deleteContactByName
     */
    public void deleteContact() {
        consoleWriter("Enter ID (like id=3) or name of contact to deleting: ");
        String line = consoleReader();

        //проверяем введен ли ID
        if (line.startsWith("id=") && line.length() > 3) {
            String idPart = line.substring(3).trim();
            try {
                int id = Integer.parseInt(idPart);
                Contact contact = controller.deleteContactById(id);

                if (contact == null) consoleWriter("Client with ID = " + id + " didn't found.\n");
                else consoleWriter("#### Contact: " + contact + " ####\n#### was successfully deleted! ####\n");
            }
            //если не удалось распарсить в число, сообщаем об этом и перезапускаем метод
            catch (IllegalArgumentException e) {
                consoleWriter("Dab ID - " + idPart + ", again\n");
                deleteContact();
            }
        }
        //если нет, считаем, что введено имя
        else {
            Contact[] contacts = controller.deleteContactByName(line);

            if (contacts == null) consoleWriter("Client with name " + line + " didn't found.\n");
            else {
                for (int i = 0; i < contacts.length; i++)
                consoleWriter("#### Contact: " + contacts[i] + " ####\n#### was successfully deleted! ####\n");
            }
        }
    }

    /**
     * Закрываем ресурсы контроллера и пишем что программа завершена
     */
    private void exit() {
        controller.closeResources();
        consoleWriter("Ending program!\n");
    }
}
