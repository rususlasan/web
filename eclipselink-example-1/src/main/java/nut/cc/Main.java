package nut.cc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by RuslaDasha on 19.01.2017.
 */
public class Main {

    public static void main(String[] args) {
        Contact c1 = new Contact("Ruslan", "111111111");
        Contact c2 = new Contact("Dasha", "1212121212222");

        //получаем EntityManager и транзакцию для еденицы сохроняемости, описанной в файле persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("eclipselink-test1");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        //обеспечиваем постоянство(записываем в БД) c1 и c2
        tx.begin();
        em.persist(c1);
        em.persist(c2);
        tx.commit();

        //получим контакт с id=2
        Contact fromDB = em.find(Contact.class, 2);
        System.out.println(fromDB);

        em.close();
        emf.close();
    }

}
