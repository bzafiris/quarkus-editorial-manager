package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.Author;
import gr.aueb.edtmgr.domain.Researcher;
import gr.aueb.edtmgr.domain.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class UserJPATest extends JPATest {

    @Test
    @Transactional
    public void listResearchers(){
        List<Researcher> result = em.createQuery("select r from Researcher r").getResultList();
        assertEquals(3, result.size());
    }

    @Test
    @Transactional
    public void listAllUsers(){
        List<User> result = em.createQuery("select u from User u").getResultList();
        assertEquals(4, result.size());
    }


}
