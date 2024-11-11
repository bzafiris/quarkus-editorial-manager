package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.Author;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorJPATest extends JPATest {

    @Test
    public void listAuthors(){
        List<Author> result = em.createQuery("select a from Author a").getResultList();
        assertEquals(4, result.size());
    }

}
