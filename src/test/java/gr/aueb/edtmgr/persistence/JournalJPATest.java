package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.Editor;
import gr.aueb.edtmgr.domain.Journal;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.RollbackException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JournalJPATest extends JPATest {

    @Test
    public void listJournals(){

        List<Journal> result = em.createQuery("select j from Journal j").getResultList();
        assertEquals(1, result.size());

        Journal j = result.get(0);
        assertEquals("Journal of Systems and Software", j.getTitle());
        assertEquals("0164-1212", j.getIssn());

        Editor editor = j.getEditor();
        assertEquals("avgeriou@gmail.com", editor.getEmail());
    }

    @Test()
    public void denySavingJournalWithSameName(){
        Journal j2 = new Journal("Journal of Systems and Software", "12345");
        Editor editor = new Editor("John", "Doe", "AUEB", "doe@aueb.gr");
        j2.setEditor(editor);

        Assertions.assertThrows(RollbackException.class, () -> {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(editor);
            em.persist(j2);
            tx.commit();
        });
    }
}
