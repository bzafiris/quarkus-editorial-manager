package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArticleJPATest extends JPATest {

    @Test
    public void listArticles(){
        List<Article> result = em.createQuery("select a from Article a").getResultList();
        assertEquals(2, result.size());

        Article article = result.get(0);
        assertNotNull(article.getJournal());

        Set<Author> authors = article.getAuthors();
        assertEquals(2, authors.size());
    }

}
