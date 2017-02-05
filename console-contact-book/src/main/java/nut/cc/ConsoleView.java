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

    /**
     * Инициализируем reader, запускаем диалог с пользователем
     */
    public void init() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        begin();
    }

    /**
     * Запускает цикл в котором слушает пользовательский ввод и, в зависимости от ввода,
     * запускает нужный метод или переходит на следующий шаг
     */
    private void begin() {
        String curr = "";
        while (true) {
            writeInConsole("Change command: \nc - create contact,\ne - edit contact,\np - print all contact,\nd - delete contact,\nclear - clear all contacts,\nq - quit program\n");
            curr = readFromConsole();
            if (curr.equals("q")) {
                exit();
                return;
            }
            else if (curr.equals("c")) addContact();
            else if (curr.equals("d")) deleteContact();
            else if (curr.equals("e")) editContact();
            else if (curr.equals("p")) printAllContact();
            else if (curr.equals("clear")) clearAllContacts();
            else writeInConsole("Command \"" + curr + "\" not found, try again.\n");
        }
    }

    /**
     * Считывает строку с консоли.
     * Перед считывание выводит сообщение в консоль, если оно передано
     */
    private String readFromConsole() {
        String res = "";
        try {
            res = reader.readLine();
        } catch (IOException e) {
            writeInConsole("IOException in readFromConsole(): " + e.getMessage() + "\n");
        } finally {
            return res;
        }

    }

    /**
     * Выводит в консоль переданную строку
     */
    private void writeInConsole(String message) {
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
        writeInConsole("Enter name: ");
        String name = readFromConsole();

        //проверим, что контакта с таким именем нет
        if (controller.isExist(name)) {
            writeInConsole("Contact with name \"" + name.trim() + "\" existing. Break!\n");
            return;
        }

        writeInConsole("Enter number: ");
        String number = readFromConsole();

        writeInConsole("Enter description: ");
        String description = readFromConsole();

        Contact contact = controller.addContact(name, number, description);
        if (contact != null) writeInConsole("#### Contact: " + contact + " ####\n#### was successfully ADDED! ####\n");
        else writeInConsole("Contact DIDN'T ADDED.");
    }

    public void printAllContact() {
        List<Contact> contacts = controller.findAllContacts();

        //если БД пуста
        if(contacts.size() == 0) {
            writeInConsole("There is now contacts in current database\n");
            return;
        }

        writeInConsole("############### ALL CONTACTS ###############\n");
        int i = 1;          //порядковый номер (текущий, при удалении, может изменятся)
        for (Contact contact: contacts) {
            writeInConsole(i + ". " + contact.toString() + "\n");
            i++;
        }
        writeInConsole("############################################\n");
    }

    /**
     * Запускает диалог по удалению контакта, удаление возможно только по имени
     */
    public void deleteContact() {
        writeInConsole("Enter name of contact to deleting: ");
        String input = readFromConsole();

        Contact deletedContact = controller.deleteContactByName(input);

        if (deletedContact != null) writeInConsole("#### Contact: " + deletedContact + " ####\n#### was successfully DELETED! ####\n");
    }

    /**
     * запускает диалог редактирования контакта
     * редактирование возможно только по id
     */
    public void editContact() {
        //считываем необходимые данные
        writeInConsole("Enter a name of contact , that you will edit: \n");
        String currName = readFromConsole();

        //если такого контакта не существует, то прерываем диалог
        if (!controller.isExist(currName)) {
            writeInConsole("Contact with name \"" + currName.trim() + "\" isn't existing. Break!");
            return;
        }

        writeInConsole("Enter new data, to skip press \"Enter\"\n");

        writeInConsole("Enter new name: ");
        String newName = readFromConsole();

        writeInConsole("\nEnter new number: ");
        String newNumber = readFromConsole();

        writeInConsole("\nEnter new description: ");
        String newDescription = readFromConsole();

        Contact[] oldAndEditedContacts = controller.editContact(currName, newName, newNumber, newDescription);

        if (oldAndEditedContacts != null) {
            writeInConsole("\n#### Contact \"" + currName.trim() + "\" was successfully edited ####\n");
            writeInConsole("Old data: " + oldAndEditedContacts[0]);
            writeInConsole("\nNew data: " + oldAndEditedContacts[1]);
            writeInConsole("\n###########################\n");
        } else {
            writeInConsole("\nContact didn't edited!\n");
        }

    }

    /**
     * Удаляет все контакты из БД
     */
    public void clearAllContacts() {
        
    }

    /**
     * печатает сообщение пользователю(вызывается контроллером)
     */
    public void printMessage(String s) {
        writeInConsole(s + "\n");
    }

    /**
     * Закрываем ресурсы контроллера и reader, пишем что программа завершена
     */
    private void exit() {
        controller.closeResources();
        try {
            reader.close();
        } catch (IOException e) {
            writeInConsole("Can't close reader....\n");
        }
        writeInConsole("Ending program!\n");
    }
}
