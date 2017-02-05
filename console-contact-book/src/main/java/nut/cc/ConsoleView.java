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
    private String consoleReader() {
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
    private void consoleWriter(String message) {
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
        if (contact != null) consoleWriter("#### Contact: " + contact + " ####\n#### was successfully ADDED! ####\n");
        else consoleWriter("Contact didn't added.");
    }

    public void printAllContact() {
        List<Contact> contacts = controller.findAllContacts();

        //если БД пуста
        if(contacts.size() == 0) {
            consoleWriter("There is now contacts in current database\n");
            return;
        }

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
    // TODO: перенести всю логику по валидации ввода в ConsoleController
    public void deleteContact() {
        consoleWriter("Enter ID (like id=3) or name of contact to deleting: ");
        String input = consoleReader();

        controller.deleteContact(input);
    }

    /**
     * печатает результаты удаления (вызывается контроллером)
     */
    public void printDeletingResult(List<Contact> deletedContacts) {
        for (Contact del: deletedContacts) {
            consoleWriter("#### Contact: " + del + " ####\n#### was successfully DELETED! ####\n");
        }
    }

    /**
     * печатает сообщение об ошибке(вызывается контроллером)
     */
    public void printErrorMessage(String s) {
        consoleWriter(s + "\n");
    }

    /**
     * Закрываем ресурсы контроллера и reader, пишем что программа завершена
     */
    private void exit() {
        controller.closeResources();
        try {
            reader.close();
        } catch (IOException e) {
            consoleWriter("Can't close reader....\n");
        }
        consoleWriter("Ending program!\n");
    }
}
