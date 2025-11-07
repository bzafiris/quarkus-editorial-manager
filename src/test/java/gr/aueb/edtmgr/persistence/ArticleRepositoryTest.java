package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.Article;
import gr.aueb.edtmgr.domain.ReviewInvitation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ArticleRepositoryTest extends JPATest {

    @Inject
    ArticleRepository articleRepository;

    @Inject
    EntityManager em;


    @Test
    @Transactional
    void testFetchAll(){
        Article article = articleRepository.fetchWithAllDependencies("pooja.rani@unibe.ch");
        assertNotNull(article);
        assertEquals(2, article.getAuthors().size());
    }

    @Test
    @Transactional
    void delete() {
        articleRepository.delete(4001);
        List<ReviewInvitation> invitationList = em.createQuery("select r from ReviewInvitation r where r.article.id=4001")
                .getResultList();
        assertEquals(0, invitationList.size());
    }

    @Test
    @Transactional
    void testFetchArticleWithReviewInvitations(){

        Article article = articleRepository.fetchWithReviewInvitations(4000);
        em.detach(article);

        assertEquals(2, article.getReviewInvitations().size());

        Set<ReviewInvitation> reviewInvitations = article.getReviewInvitations();
        for(ReviewInvitation r: reviewInvitations){
            em.detach(r);
            assertNotNull(r.getReviewer().getFirstName());
        }
    }
}