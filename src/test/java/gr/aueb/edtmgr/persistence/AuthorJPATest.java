package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.Author;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class AuthorJPATest extends JPATest {

    @Test
    @Transactional
    public void listAuthors(){
        List<Author> result = em.createQuery("select a from Author a").getResultList();
        assertEquals(4, result.size());
    }

}
