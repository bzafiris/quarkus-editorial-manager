package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.Editor;
import gr.aueb.edtmgr.domain.Journal;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.RollbackException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class JournalJPATest extends JPATest {

    @Test
    @Transactional
    public void listJournals(){

        List<Journal> result = em.createQuery("select j from Journal j").getResultList();
        assertEquals(1, result.size());

        Journal j = result.get(0);
        assertEquals("Journal of Systems and Software", j.getTitle());
        assertEquals("0164-1212", j.getIssn());

        Editor editor = j.getEditor();
        assertEquals("avgeriou@gmail.com", editor.getEmail());
    }

    @Test
    public void denySavingJournalWithSameName(){

        Editor editor = new Editor("John", "Doe", "AUEB", "doe@aueb.gr");
        saveEditor(editor);

        Assertions.assertThrows(Exception.class, () -> {
            Journal j2 = new Journal("Journal of Systems and Software", "12345");
            j2.setEditor(editor);
            saveJournal(j2);
        });
    }

    @Transactional
    public void saveJournal(Journal j){
        em.persist(j);
    }

    @Transactional
    public void saveEditor(Editor e){
        em.persist(e);
    }
}
