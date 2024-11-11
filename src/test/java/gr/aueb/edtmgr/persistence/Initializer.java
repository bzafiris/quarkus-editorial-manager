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

        em.createNativeQuery("delete from journals").executeUpdate();

        tx.commit();
    }

    public void prepareData() {

        eraseData();

        Journal j1 = new Journal("Journal of Systems and Software", "0164-1212");

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(j1);

        tx.commit();

        em.close();

    }

}

