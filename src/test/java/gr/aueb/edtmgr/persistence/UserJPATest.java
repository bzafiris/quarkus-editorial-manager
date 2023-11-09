package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.Author;
import gr.aueb.edtmgr.domain.Researcher;
import gr.aueb.edtmgr.domain.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserJPATest extends JPATest {

    @Test
    public void listResearchers(){
        List<Researcher> result = em.createQuery("select r from Researcher r").getResultList();
        assertEquals(2, result.size());
    }

    @Test
    public void listAllUsers(){
        List<User> result = em.createQuery("select u from User u").getResultList();
        assertEquals(3, result.size());
    }


}
