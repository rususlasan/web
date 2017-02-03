package nut.cc;

import javax.persistence.*;
import java.util.List;

public class ConsoleController {

    private ConsoleView view;
    private EntityManagerFactory emf;
    private EntityManager em;

    public void setView(ConsoleView view) {
        this.view = view;
    }

    public ConsoleView getView() {
        return view;
    }

    /**
     * Получаем EntityManagerFactory для еденицы сохраняемости contact-book-db
     */
    public void initPersistContext() {
        emf = Persistence.createEntityManagerFactory("contact-book-db");
        em = emf.createEntityManager();
    }

    /**
     * Добавление контакта: его создание и запись в БД
     */
    public Contact addContact(String name, String number, String description) {

        if (name.equals("") || number.equals("")) {
            return null;
        }

        Contact contact = new Contact();
        contact.setData(name, number ,description);
        persistDataInDatabase(contact);
        return contact;
    }

    /**
     * Редактирует контакт с переданным id, если какоето поле передано как null, то его не меняем
     */
    public Contact editContact(int id, String name, String number, String description) {
        return null;
    }

    /**
     * Удаление контакта из БД по его id
     */
    public Contact deleteContactById(int id) {
        return null;
    }

    /**
     * Удаление контакта(всех контактов) у которых имя совпадает с переданным значением
     */
    public Contact[] deleteContactByName(String name) {
        return null;
    }

    public List<Contact> findAllContacts() {
        TypedQuery<Contact> findAll = em.createQuery("SELECT c FROM Contact c", Contact.class);
        List<Contact> contacts = findAll.getResultList();
        return contacts;
    }

    /**
     * Записывает переданный контакт в БД
     */
    public void persistDataInDatabase(Contact contact) {
        EntityTransaction tx = em.getTransaction();

        //обеспечиваем постоянство(записываем в БД)
        tx.begin();
        em.persist(contact);
        tx.commit();
    }

    /**
     * Закрываем ресурсы подключения к БД
     */
    public void closeResources() {
        em.close();
        emf.close();
    }

    public static void main(String[] args) {
        ConsoleController controller = new ConsoleController();
        ConsoleView view = new ConsoleView();
        //назначаем виду контроллер
        view.setController(controller);
        //назначаем вид контроллеру
        controller.setView(view);
        controller.initPersistContext();
        view.init();
    }

}
