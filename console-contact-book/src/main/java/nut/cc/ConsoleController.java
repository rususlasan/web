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
    // TODO: подумать, где лучше это сделать: в методе main или во время создания контроллера?
    private void initPersistContext() {
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
     * проверяет, есть ли контакт с переданным именем
     */
    public boolean isExist(String name) {
        return findContactByName(name.trim()) != null;
    }

    /**
     * Записывает переданный контакт в БД
     */
    private void persistDataInDatabase(Contact contact) {
        EntityTransaction tx = em.getTransaction();

        //обеспечиваем постоянство(записываем в БД)
        tx.begin();
        em.persist(contact);
        tx.commit();
    }

    /**
     * Удаление контакта у которого имя совпадает с переданным значением
     */
    public Contact deleteContactByName(String name) {
        name = name.trim();

        //ищем контакт с указанным именем в БД
        Contact contactToDelete = findContactByName(name);

        //если нашли контакт, то удаляем его
        if (contactToDelete != null) deleteContactFromDatabaseAndContext(contactToDelete);
        else view.printMessage("Client with name \"" + name + "\" didn't found.");

        return contactToDelete;
    }

    /**
     * Удаляет переданный контакт
     */
    private void deleteContactFromDatabaseAndContext(Contact contact) {
        EntityTransaction tx = em.getTransaction();

        //удаляем из БД
        tx.begin();
        em.remove(contact);
        tx.commit();
    }

    private Contact findContactByName(String name) {
        //формируем запрос к БД о поиске контакта
        TypedQuery<Contact> findByName = em.createQuery("SELECT c FROM Contact c WHERE c.name=:contactname", Contact.class);
        findByName.setParameter("contactname", name);

        Contact contact;
        //выполняем запрос
        try {
            contact = findByName.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return contact;
    }

    /**
     * Редактирует контакт с переданным id, если какое-то поле передано как null, то его не меняем
     * возвращает массив из 2-х эллементов(либо null), где первый эллемент - старая версия контакта, 2-ой - отредактированная
     */
    public Contact[] editContact(String currName, String newName, String newNumber, String newDescription) {

        Contact curr = findContactByName(currName);
        if (curr == null) return null;

        Contact[] oldAndEditedContact = new Contact[2];

        //сохраняем текущее состояние аккаунта
        oldAndEditedContact[0] = new Contact();
        oldAndEditedContact[0].setData(currName, curr.getNumber(), curr.getDescrition());

        if (newName.length() == 0) newName = curr.getName();
        if (newNumber.length() == 0) newNumber = curr.getNumber();
        if (newDescription.length() == 0) newDescription = curr.getDescrition();

        oldAndEditedContact[1] = editContact(curr, newName, newNumber, newDescription);

        return oldAndEditedContact;
    }

    /**
     * Редактирует контакт и заносит изминения в БД
     */
    private Contact editContact(Contact curr, String newName, String newNumber, String newDescription) {
        curr.setData(newName, newNumber, newDescription);
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        em.merge(curr);
        tx.commit();

        return curr;
    }

    /**
     * Очищает БД
     */
    public void removeAllContacts() {
        EntityTransaction tx = em.getTransaction();

        TypedQuery<Contact> removeQuery = em.createQuery("DELETE FROM Contact c", Contact.class);
        tx.begin();
        removeQuery.executeUpdate();
        tx.commit();
    }

    /**
     * Поиск всех записей
     */
    public List<Contact> findAllContacts() {
        //TypedQuery<Contact> findAll = em.createQuery("SELECT c FROM Contact c", Contact.class);
        // может бросить IllegalArgumentException
        TypedQuery<Contact> findAll = em.createNamedQuery("findAllContacts", Contact.class);
        List<Contact> contacts = findAll.getResultList();
        return contacts;
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
