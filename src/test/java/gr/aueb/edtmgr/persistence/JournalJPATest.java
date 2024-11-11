package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.Journal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.RollbackException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JournalJPATest {

    EntityManager em;

    @BeforeEach
    public void setup() {

        Initializer initializer = new Initializer();
        initializer.prepareData();

        em = JPAUtil.getCurrentEntityManager();

    }

    @AfterEach
    public void tearDown() {
        em.close();
    }
    @Test
    public void listJournals(){

        List<Journal> result = em.createQuery("select j from Journal j").getResultList();
        assertEquals(1, result.size());

        Journal j = result.get(0);
        assertEquals("Journal of Systems and Software", j.getTitle());
        assertEquals("0164-1212", j.getIssn());

    }

    @Test()
    public void denySavingJournalWithSameName(){
        Journal j2 = new Journal("Journal of Systems and Software", "12345");

        Assertions.assertThrows(RollbackException.class, () -> {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(j2);
            tx.commit();
        });
    }
}
