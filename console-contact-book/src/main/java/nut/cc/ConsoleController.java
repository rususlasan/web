package nut.cc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

    public void initPersistContext() {
        emf = Persistence.createEntityManagerFactory("contact-book-db");
        em = emf.createEntityManager();
    }

    public Contact addContact(String name, String number, String description) {

        if (name.equals("") || number.equals("")) {
            return null;
        }

        Contact contact = new Contact();
        contact.setData(name, number ,description);
        persistDataInDatabase(contact);
        return contact;
    }

    public List<Contact> findAllContacts() {
        return null;
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
