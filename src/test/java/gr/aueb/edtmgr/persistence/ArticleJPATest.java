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
        assertNotNull(article.getCorrespondentAuthor());

        Set<Author> authors = article.getAuthors();
        assertEquals(2, authors.size());
    }

    @Test
    public void listArticlesByCorrespondentAuthor(){
        Query query = em.createQuery("select a from Article a where a.correspondentAuthor.personalInfo.email=:email");
        query.setParameter("email", "ndia@aueb.gr");
        List<Article> result = query.getResultList();
        assertEquals(1, result.size());
    }

    @Test
    public void fetchArticleWithAuthorsAndCorrespondentAuthor(){
        Query query = em.createQuery("select a from Article a " +
                "join fetch a.authors " +
                "join fetch a.correspondentAuthor r " +
                "where r.personalInfo.email=:email");
        query.setParameter("email", "mgia@aueb.gr");
        List<Article> result = query.getResultList();
        assertEquals(1, result.size());

        em.close();

        List<Author> authors = new ArrayList<>(result.get(0).getAuthors());
        Author a = authors.get(0);
        assertEquals("University of Zurich", a.getAffiliation());

    }


}
