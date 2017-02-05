package nut.cc;

import javax.persistence.*;
import java.util.ArrayList;
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
     * Редактирует контакт с переданным id, если какое-то поле передано как null, то его не меняем
     */
    public Contact editContact(int id, String name, String number, String description) {
        return null;
    }

    /**
     * Удаление контакта, получает ввод пользователя и, в зависимоти от него, запускает один из методов:
     * deleteContactById или deleteContactByName
     */
    public void deleteContact(String usersString) {
        List<Contact> contactsToDelete = new ArrayList<>();

        //проверяем введен ли ID
        if (usersString.startsWith("id=") && usersString.length() > 3) {
            String idPart = usersString.substring(3).trim();

            //проверяем что idPart - это число, если не число, то:
            if (idPart.replaceAll("[0-9]", "").length() != 0) {
                view.printErrorMessage("Bab ID - " + idPart);
            } else {
                int id = Integer.parseInt(idPart);
                Contact contactToDelete = deleteContactById(id);

                if (contactToDelete == null) view.printErrorMessage("Client with ID " + id + " didn't found.");
                else {
                    contactsToDelete.add(contactToDelete);
                    view.printDeletingResult(contactsToDelete);
                }
            }
        }
        //если нет, считаем, что введено имя
        else {
            contactsToDelete = deleteContactByName(usersString);

            if (contactsToDelete == null) view.printErrorMessage("Client with name " + usersString + " didn't found.");
            else view.printDeletingResult(contactsToDelete);
        }
    }

    /**
     * Удаление контакта из БД по его id
     */
    public Contact deleteContactById(int id) {
        /*TypedQuery<Contact> findById = em.createQuery("SELECT c FROM Contact c WHERE c.id='2'", Contact.class);
        Contact contact = findById.getSingleResult();*/
        Contact contact = em.find(Contact.class, id);

        if (contact != null) deleteContactFromDatabaseAndContext(contact);

        return contact;
    }

    /**
     * Удаление контакта(всех контактов) у которых имя совпадает с переданным значением
     */
    public List<Contact> deleteContactByName(String name) {
        TypedQuery<Contact> findByName = em.createQuery("SELECT c FROM Contact c WHERE c.name=:contactname", Contact.class);
        findByName.setParameter("contactname", name);

        //выполняем именнованый запрос
        List<Contact> contacts = findByName.getResultList();

        //если не нашли ниодного контакта с переданным именем
        if (contacts == null || contacts.size() == 0) return null;
        else {
            for(Contact contact: contacts) {
                deleteContactFromDatabaseAndContext(contact);
            }
        }
        return contacts;
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
