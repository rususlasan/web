package nut.cc;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;

import javax.inject.Inject;
import javax.inject.Named;

import javax.persistence.EntityManager;

import java.util.List;

/**
 * Created by ruslansh on 12.02.17.
 * EJB будет использоваться для работы с БД
 * Источник данных /jdbc/addrbookDS, необходимый для блока хранения, должен быть
 * создан внутри контейнера EJB.
 * для этого аннотируем с помощью @DataSourceDefinition управляемый компонент ContactEJB.
 * Контейнер развернет компонент и создаст источник данных.
 */

@Named
@Stateless
@DataSourceDefinition(name = "java:global/jdbc/lab11DS",
        className = "org.postgresql.Driver")/*,
        url = "jdbc:derby:memory:lab11DB;create=true;user=app;password=app")*/
/*@DataSourceDefinition(
        name = "java:global/jdbc/addrbookDS",
        className = "org.postgresql.Driver",
        url = "jdbc:postgresql://127.0.0.1:5432/addrbook_web",
        user = "contact_web",
        password = "contact")*/
public class ContactEJB {

    @Inject
    private EntityManager em;

    public Contact createContact(Contact contact) {
        em.persist(contact);
        return contact;
    }

    public List<Contact> findAllContatc() {
        return em.createNamedQuery("findAllContacts", Contact.class).getResultList();
    }

    public Contact findContactById(int id) {
        return em.find(Contact.class, id);
    }

}
