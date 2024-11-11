package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class Initializer {

    private EntityManager em;

    public Initializer() {
        em = JPAUtil.getCurrentEntityManager();
    }

    private void eraseData() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.createNativeQuery("delete from users").executeUpdate();
        em.createNativeQuery("delete from journals").executeUpdate();

        tx.commit();
    }

    public void prepareData() {

        eraseData();

        Researcher r1 = new Researcher("Nikos", "Diamantidis", "AUEB", "ndia@aueb.gr");
        Researcher r2 = new Researcher("Manolis", "Giakoumakis", "AUEB", "mgia@aueb.gr");

        Editor e1 = new Editor("Paris", "Avgeriou",
                "University of Groningen", "avgeriou@gmail.com");

        Journal j1 = new Journal("Journal of Systems and Software", "0164-1212");

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(r1);
        em.persist(r2);
        em.persist(e1);
        em.persist(j1);

        tx.commit();

        em.close();

    }

}

